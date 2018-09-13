package fr.maximelucquin.falconexperience.data.database;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.content.Context;
import android.support.annotation.NonNull;

import fr.maximelucquin.falconexperience.data.Actiion;
import fr.maximelucquin.falconexperience.data.Item;
import fr.maximelucquin.falconexperience.data.Triggeer;

@Entity(tableName = "actiion_item_join",
        primaryKeys = { "actiionId", "itemId" },
        foreignKeys = {
                @ForeignKey(entity = Actiion.class,
                        parentColumns = "id",
                        childColumns = "actiionId"),
                @ForeignKey(entity = Item.class,
                        parentColumns = "id",
                        childColumns = "itemId")
        })
public class ActiionItemJoin {
    @NonNull
    public String actiionId;
    @NonNull
    public String itemId;

    public ActiionItemJoin(String actiionId, String itemId) {
        this.actiionId = actiionId;
        this.itemId = itemId;
    }

    public String getActiionId() {
        return actiionId;
    }

    public void setActiionId(String actionId) {
        this.actiionId = actionId;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public static void saveItems(Context context, String actionId, String itemId) {
        AppDatabase.getAppDatabase(context).actiionItemJoinDAO().insert(new ActiionItemJoin(actionId, itemId));
    }
}
