package fr.maximelucquin.falconexperience.data;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;
import android.support.annotation.NonNull;

import java.util.List;
import java.util.UUID;

import fr.maximelucquin.falconexperience.data.database.AppDatabase;
import fr.maximelucquin.falconexperience.data.database.TriggeerTypeConverter;

import static android.arch.persistence.room.ForeignKey.CASCADE;

@Entity(tableName = "triggeer",foreignKeys = @ForeignKey(entity = Step.class,
        parentColumns = "id",
        childColumns = "stepId",
        onDelete = CASCADE))
public class Triggeer {
    @NonNull
    @PrimaryKey
    public String id;
    public String stepId;
    @Ignore
    public List<Item> items;
    @TypeConverters(TriggeerTypeConverter.class)
    public TriggeerType type;


    public enum TriggeerType {
        SWITCH_OFF(0),
        SWITCH_ON(1);

        private int code;

        TriggeerType(int code) {
            this.code = code;
        }

        public int getCode() {
            return code;
        }
    }

    public Triggeer() {
        this.id = UUID.randomUUID().toString();
    }

    public Triggeer(List<Item> items, TriggeerType type) {
        this.id = UUID.randomUUID().toString();
        this.items = items;
        this.type = type;
    }

    public String getTriggeerId() {
        return id;
    }

    public void setTriggeerId(String id) {
        this.id = id;
    }

    public String getStepId() {
        return stepId;
    }

    public void setStepId(String stepId) {
        this.stepId = stepId;
    }

    public List<Item> getItems(Context context) {
        items = AppDatabase.getAppDatabase(context).triggeerItemJoinDAO().getItemForTriggeer(getTriggeerId());
        return items;
    }

    public List<Item> getItemsWithoutReload(Context context) {
        if (items == null) {
            items = AppDatabase.getAppDatabase(context).triggeerItemJoinDAO().getItemForTriggeer(getTriggeerId());
        }
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public TriggeerType getType() {
        return type;
    }

    public void setType(TriggeerType type) {
        this.type = type;
    }

    public void save(Context context) {
        if (AppDatabase.getAppDatabase(context).triggeerDAO().getTriggeer(getTriggeerId()) != null) {
            AppDatabase.getAppDatabase(context).triggeerDAO().updateTriggeer(this);
        } else {
            AppDatabase.getAppDatabase(context).triggeerDAO().insertTriggeer(this);
        }

        if (items != null) {
            for (Item item: items) {
                item.save(context);
            }
        }
    }
}
