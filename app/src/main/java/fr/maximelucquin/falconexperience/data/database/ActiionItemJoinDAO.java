package fr.maximelucquin.falconexperience.data.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import fr.maximelucquin.falconexperience.data.Actiion;
import fr.maximelucquin.falconexperience.data.Item;

@Dao
public interface ActiionItemJoinDAO {

    @Insert
    void insert(ActiionItemJoin actiionItemJoin);

    @Delete
    void delete(ActiionItemJoin actiionItemJoin);

    @Query("SELECT * FROM actiion INNER JOIN actiion_item_join ON actiion.id=actiion_item_join.actiionId WHERE actiion_item_join.itemId=:itemId")
    List<Actiion> getActiionForItem(final String itemId);

    @Query("SELECT * FROM item INNER JOIN actiion_item_join ON item.id=actiion_item_join.itemId WHERE actiion_item_join.actiionId=:actiionId")
    List<Item> getItemForActiion(final String actiionId);
}
