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
import fr.maximelucquin.falconexperience.data.database.TriggerTypeConverter;

import static android.arch.persistence.room.ForeignKey.CASCADE;

@Entity(tableName = "_trigger",foreignKeys = @ForeignKey(entity = Step.class,
        parentColumns = "id",
        childColumns = "stepId",
        onDelete = CASCADE))
public class Trigger {
    @NonNull
    @PrimaryKey
    public String id;
    public String stepId;
    @Ignore
    public List<Item> items;
    @TypeConverters(TriggerTypeConverter.class)
    public TriggerType type;


    public enum TriggerType {
        SWITCH_OFF(0),
        SWITCH_ON(1);

        private int code;

        TriggerType(int code) {
            this.code = code;
        }

        public int getCode() {
            return code;
        }
    }

    public Trigger(List<Item> items, TriggerType type) {
        this.id = UUID.randomUUID().toString();
        this.items = items;
        this.type = type;
    }

    public String getTriggerId() {
        return id;
    }

    public void setTriggerId(String id) {
        this.id = id;
    }

    public String getStepId() {
        return stepId;
    }

    public void setStepId(String stepId) {
        this.stepId = stepId;
    }

    public List<Item> getItems(Context context) {
        if (items == null) {
            items = AppDatabase.getAppDatabase(context).itemDAO().getItemsForTrigger(getTriggerId());
        }
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public TriggerType getType() {
        return type;
    }

    public void setType(TriggerType type) {
        this.type = type;
    }

    public void save(Context context) {
        if (AppDatabase.getAppDatabase(context).triggerDAO().getTrigger(getTriggerId()) != null) {
            AppDatabase.getAppDatabase(context).triggerDAO().updateTrigger(this);
        } else {
            AppDatabase.getAppDatabase(context).triggerDAO().insertTrigger(this);
        }

        if (items != null) {
            for (Item item: items) {
                item.save(context);
            }
        }
    }
}
