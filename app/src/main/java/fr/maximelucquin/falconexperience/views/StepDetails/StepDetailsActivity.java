package fr.maximelucquin.falconexperience.views.StepDetails;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import fr.maximelucquin.falconexperience.R;
import fr.maximelucquin.falconexperience.data.Step;
import fr.maximelucquin.falconexperience.data.Triggeer;
import fr.maximelucquin.falconexperience.data.database.AppDatabase;
import fr.maximelucquin.falconexperience.views.ActionList.ActionListActivity;
import fr.maximelucquin.falconexperience.views.Sequence.StepActivity;
import fr.maximelucquin.falconexperience.views.TriggerDetails.TriggerDetailsActivity;

public class StepDetailsActivity extends AppCompatActivity {

    private Step step;

    private TextView order;
    private EditText timeTrigger;
    private EditText note;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_details);

        order = (TextView) findViewById(R.id.stepDetailsOrder);
        timeTrigger = (EditText) findViewById(R.id.stepDetailsTimeTrigger);
        note = (EditText) findViewById(R.id.stepDetailsNote);

        String stepId = getIntent().getExtras().getString("stepId");
        step = AppDatabase.getAppDatabase(getApplicationContext()).stepDAO().getStep(stepId);

        int theOrder = step.getOrder() + 1;
        order.setText("Numéro d'étape : "+theOrder);
        timeTrigger.setText(""+step.getTimeTrigger(), TextView.BufferType.EDITABLE);
        if(step.getNote() != null){
            note.setText(step.getNote());
        }


    }

    @Override
    public void onResume(){
        super.onResume();

        step = AppDatabase.getAppDatabase(getApplicationContext()).stepDAO().getStep(step.getStepId());
    }

    public void openTriggerActivity(View view) {
        Triggeer triggeer = null;
        if (step.getTriggeer(getApplicationContext()) != null) {
            System.out.println("reuse");
            triggeer = step.getTriggeer(getApplicationContext());
        } else {
            System.out.println("new");
            triggeer = new Triggeer();
            triggeer.setStepId(step.getStepId());
            triggeer.save(getApplicationContext());
        }
        Intent intent = new Intent(StepDetailsActivity.this, TriggerDetailsActivity.class);
        intent.putExtra("triggeerId",triggeer.getTriggeerId());
        intent.putExtra("stepId",step.getStepId());
        startActivity(intent);
    }

    public void openActionsActivity(View view) {
        Intent intent = new Intent(StepDetailsActivity.this, ActionListActivity.class);
        intent.putExtra("stepId",step.getStepId());
        startActivity(intent);
    }

    public void saveStep(View view) {
        if (timeTrigger.getText().toString() != "0") {
            step.setTimeTrigger(Integer.parseInt(timeTrigger.getText().toString()));
        }

        if (!note.getText().toString().isEmpty()) {
            step.setNote(note.getText().toString());
        }
        step.save(getApplicationContext());
        super.onBackPressed();
    }

    public void deleteStep(View view) {
        AppDatabase.getAppDatabase(getApplicationContext()).stepDAO().deleteStep(step);
        super.onBackPressed();
    }
}
