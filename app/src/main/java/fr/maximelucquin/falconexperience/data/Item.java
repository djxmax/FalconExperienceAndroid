package fr.maximelucquin.falconexperience.data;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;
import android.support.annotation.NonNull;

import java.util.UUID;

import fr.maximelucquin.falconexperience.data.database.AppDatabase;
import fr.maximelucquin.falconexperience.data.database.ItemPutTypeConverter;
import fr.maximelucquin.falconexperience.data.database.ItemTypeConverter;

import static android.arch.persistence.room.ForeignKey.CASCADE;

@Entity(foreignKeys = {
        @ForeignKey(entity = Action.class,
                parentColumns = "id",
                childColumns = "actionId",
                onDelete = CASCADE),
        @ForeignKey(entity = Trigger.class,
                parentColumns = "id",
                childColumns = "actionId",
                onDelete = CASCADE)}
)
public class Item {
    @NonNull
    @PrimaryKey
    public String id;
    public String actionId;
    public String triggerId;
    public String name;
    @TypeConverters(ItemTypeConverter.class)
    public ItemType type;
    @TypeConverters(ItemPutTypeConverter.class)
    public ItemPutType putType;

    public enum ItemType {
        BUTTON(0),
        LED(1),
        LEDSTRIP(2),
        ROUNDLED(3),
        SQUARELED(4),
        VIDEO(5),
        MUSIC(6),
        ALARM(7),
        SOUND(8),
        OTHER(9);

        private int code;

        ItemType(int code) {
            this.code = code;
        }

        public int getCode() {
            return code;
        }
    }

    public enum ItemPutType {
        INPUT(0),
        OUTPUT(1);

        private int code;

        ItemPutType(int code) {
            this.code = code;
        }

        public int getCode() {
            return code;
        }
    }

    public Item(String name, ItemType type, ItemPutType putType) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.type = type;
        this.putType = putType;
    }

    public String getItemId() {
        return id;
    }

    public void setItemId(String id) {
        this.id = id;
    }

    public String getActionId() {
        return actionId;
    }

    public void setActionId(String actionId) {
        this.actionId = actionId;
    }

    public String getTriggerId() {
        return triggerId;
    }

    public void setTriggerId(String triggerId) {
        this.triggerId = triggerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ItemType getType() {
        return type;
    }

    public void setType(ItemType type) {
        this.type = type;
    }

    public ItemPutType getPutType() {
        return putType;
    }

    public void setPutType(ItemPutType putType) {
        this.putType = putType;
    }

    public void save(Context context) {
        if (AppDatabase.getAppDatabase(context).itemDAO().getItem(getItemId()) != null) {
            AppDatabase.getAppDatabase(context).itemDAO().updateItem(this);
        } else {
            AppDatabase.getAppDatabase(context).itemDAO().insertItem(this);
        }
    }
}
