package fr.maximelucquin.falconexperience.views.ItemList;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.List;

import fr.maximelucquin.falconexperience.R;
import fr.maximelucquin.falconexperience.data.Item;
import fr.maximelucquin.falconexperience.data.database.AppDatabase;
import fr.maximelucquin.falconexperience.views.TriggerDetails.ItemAdapter;

public class ItemsActivity extends AppCompatActivity {

    private RecyclerView itemsRecycler;
    private ItemAdapter adapter;

    private List<Item> items;
    private List<Item> selectedItems;
    private String triggeerId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_items);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        triggeerId = getIntent().getExtras().getString("triggeerId");

        getAllItems();
        if (triggeerId != null) {
            getTriggerItems();
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        itemsRecycler = (RecyclerView) findViewById(R.id.itemsRecycler);
        adapter = new ItemAdapter(items,getApplicationContext());
        itemsRecycler.setAdapter(adapter);


    }

    public void getAllItems() {
        items = AppDatabase.getAppDatabase(getApplicationContext()).itemDAO().getAllItems();
    }

    public void getTriggerItems() {
        selectedItems = AppDatabase.getAppDatabase(getApplicationContext()).triggeerItemJoinDAO().getItemForTriggeer(triggeerId);
    }

}
