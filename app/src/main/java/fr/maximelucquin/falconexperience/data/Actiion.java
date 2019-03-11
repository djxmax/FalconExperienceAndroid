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

import fr.maximelucquin.falconexperience.data.database.ActiionTypeConverter;
import fr.maximelucquin.falconexperience.data.database.AppDatabase;

import static android.arch.persistence.room.ForeignKey.CASCADE;

@Entity(foreignKeys = @ForeignKey(entity = Step.class,
        parentColumns = "id",
        childColumns = "stepId",
        onDelete = ForeignKey.CASCADE))
public class Actiion {
    @NonNull
    @PrimaryKey
    public String id;
    public String stepId;
    public String note;
    @Ignore
    public List<Item> items;
    public int delay;
    public int duration;
    public boolean blink;
    public int blinkFreq;
    @TypeConverters(ActiionTypeConverter.class)
    public ActiionType type;

    public enum ActiionType {
        OFF(0),
        ON(1);

        private int code;

        ActiionType(int code) {
            this.code = code;
        }

        public int getCode() {
            return code;
        }
    }

    public Actiion() {
        this.id = UUID.randomUUID().toString();
    }

    public Actiion(List<Item> items, int delay, int duration, boolean blink, int blinkFreq, ActiionType type) {
        this.id = UUID.randomUUID().toString();
        this.items = items;
        this.delay = delay;
        this.duration = duration;
        this.blink = blink;
        this.blinkFreq = blinkFreq;
        this.type = type;
    }

    public String getIdActiion() {
        return id;
    }

    public void setIdActiion(String id) {
        this.id = id;
    }

    public String getStepId() {
        return stepId;
    }

    public void setStepId(String stepId) {
        this.stepId = stepId;
    }

    public List<Item> getItems(Context context) {
        items = AppDatabase.getAppDatabase(context).actiionItemJoinDAO().getItemForActiion(getIdActiion());

        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public int getDelay() {
        return delay;
    }

    public void setDelay(int delay) {
        this.delay = delay;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public boolean isBlink() {
        return blink;
    }

    public void setBlink(boolean blink) {
        this.blink = blink;
    }

    public int getBlinkFreq() {
        return blinkFreq;
    }

    public void setBlinkFreq(int blinkFreq) {
        this.blinkFreq = blinkFreq;
    }

    public ActiionType getType() {
        return type;
    }

    public void setType(ActiionType type) {
        this.type = type;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public void save(Context context) {
        if (AppDatabase.getAppDatabase(context).actiionDAO().getActiion(getIdActiion()) != null) {
            AppDatabase.getAppDatabase(context).actiionDAO().updateActiion(this);
        } else {
            AppDatabase.getAppDatabase(context).actiionDAO().insertActiion(this);
        }

        /*if (items != null) {
            for (Item item: items) {
                item.save(context);
            }
        }*/
    }
}
