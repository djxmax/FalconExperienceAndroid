package fr.maximelucquin.falconexperience.views.ActiionDetails;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import fr.maximelucquin.falconexperience.R;
import fr.maximelucquin.falconexperience.data.Actiion;
import fr.maximelucquin.falconexperience.data.Triggeer;
import fr.maximelucquin.falconexperience.data.database.AppDatabase;
import fr.maximelucquin.falconexperience.views.ItemList.ItemsActivity;
import fr.maximelucquin.falconexperience.views.TriggerDetails.TriggerDetailsActivity;

public class ActiionDetailsActivity extends AppCompatActivity {

    private String actiionId;
    private Actiion actiion;

    private EditText actiionDelay;
    private EditText actiionDuration;
    private CheckBox actiionBlink;
    private EditText actiionBlinkFreq;
    private EditText actiionNote;
    private TextView actiionType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actiion_details);

        actiionId = getIntent().getExtras().getString("actiionId");

        actiionDelay = (EditText) findViewById(R.id.actiionDelay);
        actiionDuration = (EditText) findViewById(R.id.actiionDuration);
        actiionBlink = (CheckBox) findViewById(R.id.actiionBlink);
        actiionBlinkFreq = (EditText) findViewById(R.id.actiionBlinkFreq);
        actiionNote = (EditText) findViewById(R.id.actiionNote);
        actiionType = (TextView) findViewById(R.id.actiionType);

        getData();
    }


    public void getData() {
        actiion = AppDatabase.getAppDatabase(getApplicationContext()).actiionDAO().getActiion(actiionId);

        actiionDelay.setText(""+actiion.getDelay());
        actiionDuration.setText(""+actiion.getDuration());
        actiionBlink.setChecked(actiion.isBlink());
        actiionBlinkFreq.setText(""+actiion.getBlinkFreq());

        if (actiion.getNote() != null) {
            actiionNote.setText(actiion.getNote());
        }

        setActionType();
    }

    public void editItems(View view) {
        Intent intent = new Intent(ActiionDetailsActivity.this, ItemsActivity.class);
        intent.putExtra("actiionId",actiion.getIdActiion());
        intent.putExtra("input",false);
        startActivity(intent);
    }

    public void editActiionType(View view) {
        AlertDialog.Builder builderSingle = new AlertDialog.Builder(this);
        //builderSingle.setIcon(R.drawable.ic_launcher);
        builderSingle.setTitle("Selection du type :");

        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.select_dialog_singlechoice);
        arrayAdapter.add("éteindre");
        arrayAdapter.add("allumer");

        builderSingle.setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        AlertDialog.Builder builder = builderSingle.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0:
                        actiion.setType(Actiion.ActiionType.OFF);
                        break;
                    case 1:
                        actiion.setType(Actiion.ActiionType.ON);
                        break;
                    default:
                        break;
                }
                setActionType();
            }
        });
        builderSingle.show();
    }

    public void setActionType() {
        if (actiion.getType() != null) {
            switch (actiion.getType()) {
                case OFF:
                    actiionType.setText("Type d'action : éteindre");
                    break;
                case ON:
                    actiionType.setText("Type d'action : allumer");
                    break;
            }
        } else {
            actiionType.setText("Type d'action : inconnu");
        }
    }

    public void saveActiion(View view) {
        actiion.setDelay(Integer.parseInt(actiionDelay.getText().toString()));
        actiion.setDuration(Integer.parseInt(actiionDuration.getText().toString()));
        actiion.setBlink(actiionBlink.isChecked());
        actiion.setBlinkFreq(Integer.parseInt(actiionBlinkFreq.getText().toString()));
        actiion.setNote(actiionNote.getText().toString());

        actiion.save(getApplicationContext());
        super.onBackPressed();

    }

    public void deleteActiion(View view) {
        AppDatabase.getAppDatabase(getApplicationContext()).actiionDAO().deleteActiion(actiion);
        super.onBackPressed();

    }
}
