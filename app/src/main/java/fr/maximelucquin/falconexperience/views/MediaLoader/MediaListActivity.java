package fr.maximelucquin.falconexperience.views.MediaLoader;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RadioButton;

import java.util.ArrayList;
import java.util.List;

import fr.maximelucquin.falconexperience.R;
import fr.maximelucquin.falconexperience.data.Item;
import fr.maximelucquin.falconexperience.data.database.AppDatabase;
import fr.maximelucquin.falconexperience.views.ItemDetails.ItemDetailsActivity;
import fr.maximelucquin.falconexperience.views.ItemList.ItemsActivity;
import fr.maximelucquin.falconexperience.views.MainActivity;
import fr.maximelucquin.falconexperience.views.Tools.RecyclerItemClickListener;
import fr.maximelucquin.falconexperience.views.TriggerDetails.ItemAdapter;

import static fr.maximelucquin.falconexperience.data.Item.ItemType.ALARM;
import static fr.maximelucquin.falconexperience.data.Item.ItemType.MUSIC;
import static fr.maximelucquin.falconexperience.data.Item.ItemType.SOUND;
import static fr.maximelucquin.falconexperience.data.Item.ItemType.VIDEO;

public class MediaListActivity extends AppCompatActivity {

    private RecyclerView itemsRecycler;
    private ItemAdapter adapter;

    private List<Item> items;
    private List<Item> filteredItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media_list);

        filteredItems = new ArrayList<>();
        items = AppDatabase.getAppDatabase(getApplicationContext()).itemDAO().getAllItems();
        filterItems(VIDEO);

        itemsRecycler = (RecyclerView) findViewById(R.id.mediaItemList);
        itemsRecycler.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ItemAdapter(filteredItems,getApplicationContext(), null);
        itemsRecycler.setAdapter(adapter);

        itemsRecycler.addOnItemTouchListener(
                new RecyclerItemClickListener(getApplicationContext(), itemsRecycler ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        Item item = filteredItems.get(position);
                        Intent intent = new Intent(MediaListActivity.this, MediaItemActivity.class);
                        intent.putExtra("itemId", item.getItemId());
                        startActivity(intent);
                    }

                    @Override public void onLongItemClick(View view, int position) {

                    }
                })
        );


    }

    public void filterItems(Item.ItemType type) {
        filteredItems.clear();
        Item.ItemType theType = VIDEO;
        if (type != null) theType = type;

        for (Item item: items){
            if (item.type == theType) {
                filteredItems.add(item);
            }

        }
    }

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.radioButtonVideo:
                if (checked)
                    filterItems(VIDEO);
                    break;
            case R.id.radioButtonMusic:
                if (checked)
                    filterItems(MUSIC);
                    break;
            case R.id.radioButtonSound:
                if (checked)
                    filterItems(SOUND);
                    break;
            case R.id.radioButtonAlarm:
                if (checked)
                    filterItems(ALARM);
                    break;
        }
        itemsRecycler.getAdapter().notifyDataSetChanged();
    }
}
