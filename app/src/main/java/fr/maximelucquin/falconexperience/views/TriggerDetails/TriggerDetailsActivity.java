package fr.maximelucquin.falconexperience.views.TriggerDetails;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import fr.maximelucquin.falconexperience.R;
import fr.maximelucquin.falconexperience.data.Step;
import fr.maximelucquin.falconexperience.data.Triggeer;
import fr.maximelucquin.falconexperience.data.database.AppDatabase;
import fr.maximelucquin.falconexperience.data.database.TriggeerItemJoin;
import fr.maximelucquin.falconexperience.views.ItemList.ItemsActivity;
import fr.maximelucquin.falconexperience.views.Sequence.StepActivity;
import fr.maximelucquin.falconexperience.views.Sequence.StepAdapter;
import fr.maximelucquin.falconexperience.views.StepDetails.StepDetailsActivity;
import fr.maximelucquin.falconexperience.views.Tools.RecyclerItemClickListener;

public class TriggerDetailsActivity extends AppCompatActivity {

    private Step step;
    private Triggeer triggeer;

    private TextView triggerType;
    private RecyclerView triggerItems;
    private ItemAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trigger_details);

        String triggeerId = getIntent().getExtras().getString("triggeerId");
        triggeer = AppDatabase.getAppDatabase(getApplicationContext()).triggeerDAO().getTriggeer(triggeerId);
        String stepId = getIntent().getExtras().getString("stepId");
        step = AppDatabase.getAppDatabase(getApplicationContext()).stepDAO().getStep(stepId);

        triggerType = (TextView) findViewById(R.id.triggerType);
        triggerItems = (RecyclerView) findViewById(R.id.triggerItemRecycler);

        triggerItems.setLayoutManager(new LinearLayoutManager(this));

        adapter = new ItemAdapter(triggeer.getItems(getApplicationContext()), this);
        triggerItems.setAdapter(adapter);

        triggerItems.addOnItemTouchListener(
                new RecyclerItemClickListener(getApplicationContext(), triggerItems ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        /*Step step = steps.get(position);
                        Intent intent = new Intent(StepActivity.this, StepDetailsActivity.class);
                        intent.putExtra("stepId",step.getStepId());
                        startActivity(intent);*/
                    }

                    @Override public void onLongItemClick(View view, int position) {
                        // do whatever
                    }
                })
        );

        if (triggeer.getType() != null) {
            switch (triggeer.getType()) {
                case SWITCH_OFF:
                    triggerType.setText("Type déclencheur : on -> off");
                    break;
                case SWITCH_ON:
                    triggerType.setText("Type déclencheur : off -> on");
                    break;
            }
        } else {
            triggerType.setText("Type déclencheur inconnu");
        }
    }

    public void saveTriggeer(View view) {
        triggeer.save(getApplicationContext());
        super.onBackPressed();
    }

    public void deleteTriggeer(View view) {
        AppDatabase.getAppDatabase(getApplicationContext()).triggeerDAO().deleteTriggeer(triggeer);
        super.onBackPressed();
    }

    public void editType(View view) {

    }

    public void addTrigger(View view) {
        Intent intent = new Intent(TriggerDetailsActivity.this, ItemsActivity.class);
        intent.putExtra("triggeerId",triggeer.getTriggeerId());
        startActivity(intent);
    }
}
