package fr.maximelucquin.falconexperience.data.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import fr.maximelucquin.falconexperience.data.Item;
import fr.maximelucquin.falconexperience.data.Triggeer;

@Dao
public interface TriggerItemJoinDAO {

    @Insert
    void insert(TriggeerItemJoin triggerItemJoin);

    @Query("SELECT triggeer.* FROM triggeer INNER JOIN triggeer_item_join ON triggeer.id=triggeer_item_join.triggeerId WHERE triggeer_item_join.itemId=:itemId")
    List<Triggeer> getTriggerForItem(final String itemId);

    @Query("SELECT item.* FROM item INNER JOIN triggeer_item_join ON item.id=triggeer_item_join.itemId WHERE triggeer_item_join.triggeerId=:triggeerId")
    List<Item> getItemForTriggeer(final String triggeerId);
}
