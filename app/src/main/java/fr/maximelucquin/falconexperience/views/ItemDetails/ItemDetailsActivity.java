package fr.maximelucquin.falconexperience.views.ItemDetails;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

import fr.maximelucquin.falconexperience.R;
import fr.maximelucquin.falconexperience.data.Item;
import fr.maximelucquin.falconexperience.data.database.AppDatabase;

import static fr.maximelucquin.falconexperience.data.Item.ItemType.ALARM;
import static fr.maximelucquin.falconexperience.data.Item.ItemType.BUTTON;
import static fr.maximelucquin.falconexperience.data.Item.ItemType.LED;
import static fr.maximelucquin.falconexperience.data.Item.ItemType.LEDSTRIP;
import static fr.maximelucquin.falconexperience.data.Item.ItemType.MUSIC;
import static fr.maximelucquin.falconexperience.data.Item.ItemType.OTHER;
import static fr.maximelucquin.falconexperience.data.Item.ItemType.ROUNDLED;
import static fr.maximelucquin.falconexperience.data.Item.ItemType.SOUND;
import static fr.maximelucquin.falconexperience.data.Item.ItemType.SQUARELED;
import static fr.maximelucquin.falconexperience.data.Item.ItemType.VIDEO;

public class ItemDetailsActivity extends AppCompatActivity {

    private Item item;

    private EditText itemName;
    private TextView itemType;
    private TextView itemPutType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_details);

        itemName = (EditText) findViewById(R.id.item_name);
        itemType = (TextView) findViewById(R.id.item_type);
        itemPutType = (TextView) findViewById(R.id.item_put_type);

        String itemId = getIntent().getExtras().getString("itemId");
        item = AppDatabase.getAppDatabase(getApplicationContext()).itemDAO().getItem(itemId);

        itemName.setText(item.getName());

        setType();

        setPutType();
    }

    public void setType() {
        if (item.getType() != null) {
            switch (item.getType()) {
                case BUTTON:
                    itemType.setText("Type : Boutton");
                    break;
                case LED:
                    itemType.setText("Type : Lumière");
                    break;
                case LEDSTRIP:
                    itemType.setText("Type : Bandeau LED");
                    break;
                case ROUNDLED:
                    itemType.setText("Type : Lumière ronde");
                    break;
                case SQUARELED:
                    itemType.setText("Type : Lumière carré");
                    break;
                case VIDEO:
                    itemType.setText("Type : Vidéo");
                    break;
                case MUSIC:
                    itemType.setText("Type : Musique");
                    break;
                case ALARM:
                    itemType.setText("Type : Alarme");
                    break;
                case SOUND:
                    itemType.setText("Type : Son");
                    break;
                case OTHER:
                    itemType.setText("Type : Autre");
                    break;
            }
        } else {
            itemType.setText("Type : Inconnu");
        }
    }

    public void setPutType() {
        if (item.getPutType() != null) {
            switch (item.getPutType()) {
                case INPUT:
                    itemPutType.setText("Type I/O : entrée");
                    break;
                case OUTPUT:
                    itemPutType.setText("Type I/O : sortie");
                    break;
            }
        } else {
            itemPutType.setText("Type I/O : inconnu");
        }
    }

    public void saveItem(View view) {
        item.setName(itemName.getText().toString());
        item.save(getApplicationContext());
        super.onBackPressed();
    }

    public void deleteItem(View view) {
        AppDatabase.getAppDatabase(getApplicationContext()).itemDAO().deleteItem(item);
        super.onBackPressed();
    }

    public void editType(View view) {
        AlertDialog.Builder builderSingle = new AlertDialog.Builder(this);
        //builderSingle.setIcon(R.drawable.ic_launcher);
        builderSingle.setTitle("Selection du type :");

        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.select_dialog_singlechoice);
        arrayAdapter.add("Boutton");
        arrayAdapter.add("Lumière");
        arrayAdapter.add("Bandeau LED");
        arrayAdapter.add("Lumière ronde");
        arrayAdapter.add("Lumière carré");
        arrayAdapter.add("Vidéo");
        arrayAdapter.add("Musique");
        arrayAdapter.add("Alarme");
        arrayAdapter.add("Son");
        arrayAdapter.add("Autre");

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
                        item.setType(BUTTON);
                        break;
                    case 1:
                        item.setType(LED);
                        break;
                    case 2:
                        item.setType(LEDSTRIP);
                        break;
                    case 3:
                        item.setType(ROUNDLED);
                        break;
                    case 4:
                        item.setType(SQUARELED);
                        break;
                    case 5:
                        item.setType(VIDEO);
                        break;
                    case 6:
                        item.setType(MUSIC);
                        break;
                    case 7:
                        item.setType(ALARM);
                        break;
                    case 8:
                        item.setType(SOUND);
                        break;
                    case 9:
                        item.setType(OTHER);
                        break;
                    default:
                        break;
                }
                setType();
            }
        });
        builderSingle.show();
    }

    public void editPutType(View view) {
        AlertDialog.Builder builderSingle = new AlertDialog.Builder(this);
        //builderSingle.setIcon(R.drawable.ic_launcher);
        builderSingle.setTitle("Selection du type I/O :");

        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.select_dialog_singlechoice);
        arrayAdapter.add("Entrée");
        arrayAdapter.add("Sortie");

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
                        item.setPutType(Item.ItemPutType.INPUT);
                        break;
                    case 1:
                        item.setPutType(Item.ItemPutType.OUTPUT);
                        break;
                    default:
                        break;
                }
                setPutType();
            }
        });
        builderSingle.show();
    }

    public void autoGenerate(View view) {
        super.onBackPressed();
    }
}
