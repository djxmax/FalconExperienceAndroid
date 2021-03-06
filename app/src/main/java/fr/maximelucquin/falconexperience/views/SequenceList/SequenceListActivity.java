package fr.maximelucquin.falconexperience.views.SequenceList;

import android.content.DialogInterface;
import android.content.Intent;
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
import fr.maximelucquin.falconexperience.data.database.AppDatabase;
import fr.maximelucquin.falconexperience.views.Sequence.StepActivity;
import fr.maximelucquin.falconexperience.views.SequencePlay.SequencePlayActivity;
import fr.maximelucquin.falconexperience.views.Tools.RecyclerItemClickListener;

public class SequenceListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private SequenceAdapter adapter;

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

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        SequenceAdapter.SequenceAdapterListener listener = new SequenceAdapter.SequenceAdapterListener() {
            @Override
            public void editOnClick(View v, int position) {
                Sequence sequence = sequences.get(position);
                Intent intent = new Intent(SequenceListActivity.this, StepActivity.class);
                intent.putExtra("sequenceId", sequence.getSequenceId());
                startActivity(intent);
            }

            @Override
            public void playOnClick(View v, int position) {
                Sequence sequence = sequences.get(position);
                Intent intent = new Intent(SequenceListActivity.this, SequencePlayActivity.class);
                intent.putExtra("sequenceId", sequence.getSequenceId());
                startActivity(intent);
            }
        };

        adapter = new SequenceAdapter(sequences, getApplicationContext(), listener);
        recyclerView.setAdapter(adapter);

    }

    @Override
    public void onResume(){
        super.onResume();

        getSequences();
        adapter.notifyDataSetChanged();

    }

    public void getSequences() {
        sequences = AppDatabase.getAppDatabase(getApplicationContext()).sequenceDAO().getAllSequences();

    }


    public void addSequence() {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Title");
        alert.setMessage("Message");

        final EditText input = new EditText(this);
// Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        alert.setView(input);

        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                String name = input.getText().toString();
                Sequence sequence = new Sequence(name);
                sequence.save(getApplicationContext());
                Intent intent = new Intent(SequenceListActivity.this, StepActivity.class);
                intent.putExtra("sequenceId", sequence.getSequenceId());
                startActivity(intent);
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
