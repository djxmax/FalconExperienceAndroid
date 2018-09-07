package fr.maximelucquin.falconexperience.views.Sequence;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import fr.maximelucquin.falconexperience.R;
import fr.maximelucquin.falconexperience.data.Sequence;
import fr.maximelucquin.falconexperience.data.Step;
import fr.maximelucquin.falconexperience.data.database.AppDatabase;
import fr.maximelucquin.falconexperience.views.SequenceList.SequenceAdapter;
import fr.maximelucquin.falconexperience.views.SequenceList.SequenceListActivity;
import fr.maximelucquin.falconexperience.views.StepDetails.StepDetailsActivity;
import fr.maximelucquin.falconexperience.views.Tools.RecyclerItemClickListener;

public class StepActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private StepAdapter adapter;
    public Sequence sequence;
    private List<Step> steps = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sequence);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.stepAdd);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createStep();
            }
        });

        String sequenceId = getIntent().getExtras().getString("sequenceId");
        sequence = AppDatabase.getAppDatabase(getApplicationContext()).sequenceDAO().getSequence(sequenceId);
        getSteps();

        recyclerView = (RecyclerView) findViewById(R.id.stepRecyclerView);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new StepAdapter(steps, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);

        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(getApplicationContext(), recyclerView ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        Step step = steps.get(position);
                        Intent intent = new Intent(StepActivity.this, StepDetailsActivity.class);
                        intent.putExtra("stepId",step.getStepId());
                        startActivity(intent);
                    }

                    @Override public void onLongItemClick(View view, int position) {
                        // do whatever
                    }
                })
        );
    }

    @Override
    public void onResume(){
        super.onResume();

        getSteps();
        adapter.notifyDataSetChanged();

    }

    public void getSteps() {
        steps = AppDatabase.getAppDatabase(getApplicationContext()).stepDAO().getStepsForSequence(sequence.getSequenceId());
        Collections.sort(steps);
        steps = Step.reorderSteps(getApplicationContext(),steps);
    }


    public void createStep() {
        Step step = new Step();
        step.setOrder(steps.size());
        step.setSequenceId(sequence.getSequenceId());
        step.setTimeTrigger(0);
        step.save(getApplicationContext());
        steps.add(step);
        adapter = new StepAdapter(steps, this);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

}
