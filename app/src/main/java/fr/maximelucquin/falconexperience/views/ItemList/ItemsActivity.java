package fr.maximelucquin.falconexperience.views.ItemList;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import fr.maximelucquin.falconexperience.R;
import fr.maximelucquin.falconexperience.data.Item;
import fr.maximelucquin.falconexperience.data.database.ActiionItemJoin;
import fr.maximelucquin.falconexperience.data.database.AppDatabase;
import fr.maximelucquin.falconexperience.data.database.TriggeerItemJoin;
import fr.maximelucquin.falconexperience.views.ItemDetails.ItemDetailsActivity;
import fr.maximelucquin.falconexperience.views.Tools.RecyclerItemClickListener;
import fr.maximelucquin.falconexperience.views.TriggerDetails.ItemAdapter;

public class ItemsActivity extends AppCompatActivity {

    private Boolean input;
    private RecyclerView itemsRecycler;
    private ItemAdapter adapter;

    private List<Item> items;
    private List<Item> selectedItems;
    private String triggeerId;
    private String actiionId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_items);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        triggeerId = getIntent().getExtras().getString("triggeerId", "");
        actiionId = getIntent().getExtras().getString("actiionId", "");
        input = getIntent().getExtras().getBoolean("input", false);

        getData();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Item item = new Item();
                item.save(getApplicationContext());
                Intent intent = new Intent(ItemsActivity.this, ItemDetailsActivity.class);
                intent.putExtra("itemId",item.getItemId());
                startActivity(intent);
            }
        });

        itemsRecycler = (RecyclerView) findViewById(R.id.itemsRecycler);
        itemsRecycler.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ItemAdapter(items,getApplicationContext(), selectedItems);
        itemsRecycler.setAdapter(adapter);

        itemsRecycler.addOnItemTouchListener(
                new RecyclerItemClickListener(getApplicationContext(), itemsRecycler ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {

                        Item item = items.get(position);
                        if (Item.containsItem(selectedItems,item.getItemId())) {
                            //selectedItems.remove(item);
                            int index = -1;
                            for(int i = 0; i < selectedItems.size(); i++) {
                                if (selectedItems.get(i).getItemId().equals(item.getItemId())) {
                                    index = i;
                                    break;
                                }
                            }
                            if (index != -1) {
                                selectedItems.remove(index);
                            }

                        } else {
                            selectedItems.add(item);
                        }

                        adapter = new ItemAdapter(items,getApplicationContext(), selectedItems);
                        itemsRecycler.setAdapter(adapter);
                    }

                    @Override public void onLongItemClick(View view, int position) {
                        Item item = items.get(position);
                        Intent intent = new Intent(ItemsActivity.this, ItemDetailsActivity.class);
                        intent.putExtra("itemId",item.getItemId());
                        startActivity(intent);
                    }
                })
        );


    }

    public void getData() {
        getAllItems();
        if (triggeerId != null && !triggeerId.isEmpty()) {
            getTriggerItems();
        }

        if (actiionId != null && !actiionId.isEmpty()) {
            getActiionItems();
        }
    }

    @Override
    public void onResume(){
        super.onResume();

        getData();
        adapter = new ItemAdapter(items,getApplicationContext(), selectedItems);
        itemsRecycler.setAdapter(adapter);

    }

    public void getAllItems() {
        items = AppDatabase.getAppDatabase(getApplicationContext()).itemDAO().getAllItems();
        List<Item> tempArray = new ArrayList<Item>();
        for (Item i: items) {
            if (input == true && i.getPutType() != null && i.getPutType() == Item.ItemPutType.INPUT) {
                tempArray.add(i);
            } else if (input == false && i.getPutType() != null && i.getPutType() == Item.ItemPutType.OUTPUT) {
                tempArray.add(i);
            }
        }
        items = tempArray;
    }

    public void getTriggerItems() {
        selectedItems = AppDatabase.getAppDatabase(getApplicationContext()).triggeerItemJoinDAO().getItemForTriggeer(triggeerId);
    }

    public void getActiionItems() {
        selectedItems = AppDatabase.getAppDatabase(getApplicationContext()).actiionItemJoinDAO().getItemForActiion(actiionId);
    }

    public void saveItems(View view) {
        if (triggeerId != null && !triggeerId.isEmpty()) {
            TriggeerItemJoin.deleteAllItems(getApplicationContext(),triggeerId);
        }

        if (actiionId != null && !actiionId.isEmpty()) {
            ActiionItemJoin.deleteAllItems(getApplicationContext(), actiionId);
        }

        for (Item item: selectedItems) {
            if (triggeerId != null && !triggeerId.isEmpty()) {
                TriggeerItemJoin.saveTriggeer(getApplicationContext(), triggeerId, item.getItemId());
            }

            if (actiionId != null && !actiionId.isEmpty()) {
                ActiionItemJoin.saveItems(getApplicationContext(), actiionId, item.getItemId());
            }
        }
        super.onBackPressed();
    }

}
