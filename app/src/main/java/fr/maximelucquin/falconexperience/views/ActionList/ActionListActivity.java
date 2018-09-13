package fr.maximelucquin.falconexperience.views.ActionList;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.List;

import fr.maximelucquin.falconexperience.R;
import fr.maximelucquin.falconexperience.data.Actiion;
import fr.maximelucquin.falconexperience.data.Sequence;
import fr.maximelucquin.falconexperience.data.database.AppDatabase;
import fr.maximelucquin.falconexperience.views.ActiionDetails.ActiionDetailsActivity;
import fr.maximelucquin.falconexperience.views.Sequence.StepActivity;
import fr.maximelucquin.falconexperience.views.SequenceList.SequenceAdapter;
import fr.maximelucquin.falconexperience.views.SequenceList.SequenceListActivity;
import fr.maximelucquin.falconexperience.views.Tools.RecyclerItemClickListener;

public class ActionListActivity extends AppCompatActivity {

    private String stepId;
    private List<Actiion> actiionList;

    private RecyclerView recyclerView;
    private ActiionAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_action_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        stepId = getIntent().getExtras().getString("stepId");



        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Actiion actiion = new Actiion();
                actiion.setStepId(stepId);
                actiion.save(getApplicationContext());
                Intent intent = new Intent(ActionListActivity.this, ActiionDetailsActivity.class);
                intent.putExtra("actiionId", actiion.getIdActiion());
                startActivity(intent);
            }
        });

        recyclerView = (RecyclerView) findViewById(R.id.actiionRecycler);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new ActiionAdapter(actiionList, getApplicationContext());
        recyclerView.setAdapter(adapter);

        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(getApplicationContext(), recyclerView ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        Actiion actiion = actiionList.get(position);
                        Intent intent = new Intent(ActionListActivity.this, ActiionDetailsActivity.class);
                        intent.putExtra("actiionId", actiion.getIdActiion());
                        startActivity(intent);
                    }

                    @Override public void onLongItemClick(View view, int position) {
                        // do whatever
                    }
                })
        );

        getData();


    }

    @Override
    public void onResume() {
        super.onResume();

        getData();
    }

    public void getData() {
        actiionList = AppDatabase.getAppDatabase(getApplicationContext()).actiionDAO().getActiionForStep(stepId);
        adapter = new ActiionAdapter(actiionList, getApplicationContext());
        recyclerView.setAdapter(adapter);
    }

}
