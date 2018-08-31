package fr.maximelucquin.falconexperience.views.SequenceList;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

import fr.maximelucquin.falconexperience.R;
import fr.maximelucquin.falconexperience.data.Sequence;

public class SequenceListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;

    private List<Sequence> sequences = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sequence_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.sequenceAdd);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addSequence();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        getSequences();

        recyclerView = (RecyclerView) findViewById(R.id.sequenceRecyclerView);

        //définit l'agencement des cellules, ici de façon verticale, comme une ListView
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //puis créer un MyAdapter, lui fournir notre liste de villes.
        //cet adapter servira à remplir notre recyclerview
        recyclerView.setAdapter(new SequenceAdapter(sequences));
    }

    public void getSequences() {
        sequences = Sequence.listAll(Sequence.class);
    }


    public void addSequence() {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Title");
        alert.setMessage("Message");

        final EditText input = new EditText(this);
// Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        alert.setView(input);

        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                String name = input.getText().toString();
                Sequence sequence = new Sequence(name);
                sequence.save();

            }
        });

        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                // Canceled.
            }
        });
        alert.show();
    }



}
