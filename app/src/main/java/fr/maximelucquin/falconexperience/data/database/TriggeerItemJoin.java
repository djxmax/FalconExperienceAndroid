package fr.maximelucquin.falconexperience.data.database;


import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.content.Context;
import android.support.annotation.NonNull;

import fr.maximelucquin.falconexperience.data.Item;
import fr.maximelucquin.falconexperience.data.Triggeer;

@Entity(tableName = "triggeer_item_join",
        primaryKeys = { "triggeerId", "itemId" },
        foreignKeys = {
                @ForeignKey(entity = Triggeer.class,
                        parentColumns = "id",
                        childColumns = "triggeerId"),
                @ForeignKey(entity = Item.class,
                        parentColumns = "id",
                        childColumns = "itemId")
        })
public class TriggeerItemJoin {
    @NonNull
    public String triggeerId;
    @NonNull
    public String itemId;

    public TriggeerItemJoin(String triggeerId, String itemId) {
        this.triggeerId = triggeerId;
        this.itemId = itemId;
    }

    public String getTriggeerId() {
        return triggeerId;
    }

    public void setTriggeerId(String triggeerId) {
        this.triggeerId = triggeerId;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public static void saveTriggeer(Context context, String triggerId, String itemId) {
        AppDatabase.getAppDatabase(context).triggeerItemJoinDAO().insert(new TriggeerItemJoin(triggerId, itemId));
    }
}
