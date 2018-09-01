package fr.maximelucquin.falconexperience.data.database;

import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import fr.maximelucquin.falconexperience.data.Item;
import fr.maximelucquin.falconexperience.data.Sequence;
import fr.maximelucquin.falconexperience.data.Step;

public interface ItemDAO {

    @Query("SELECT * FROM item WHERE id=:itemId")
    public Item getItem(String itemId);

    @Query("SELECT * FROM item")
    public List<Item> getAllItems();

    @Query("SELECT * FROM item WHERE triggerId=:triggerId")
    public List<Item> getItemsForTrigger(String triggerId);

    @Query("SELECT * FROM item WHERE actionId=:actionId")
    public List<Item> getItemsForAction(String actionId);

    @Insert
    public void insertItem(Item item);

    @Update
    public void updateItem(Item item);

    @Delete
    public void deleteItem(Item item);
}
