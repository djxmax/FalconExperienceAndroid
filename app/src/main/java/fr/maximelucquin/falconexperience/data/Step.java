package fr.maximelucquin.falconexperience.data;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.content.Context;
import android.support.annotation.NonNull;

import java.util.List;
import java.util.UUID;

import fr.maximelucquin.falconexperience.data.database.AppDatabase;

import static android.arch.persistence.room.ForeignKey.CASCADE;

@Entity(foreignKeys = @ForeignKey(entity = Sequence.class,
        parentColumns = "id",
        childColumns = "sequenceId",
        onDelete = CASCADE))
public class Step {
    @NonNull
    @PrimaryKey
    public String id;
    public String sequenceId;
    public int order;
    @Ignore
    public Trigger trigger;
    @Ignore
    public List<Action> actions;
    public int timeTrigger;

    public Step() {
        this.id = UUID.randomUUID().toString();
    }

    public Step(String sequenceId, int order, Trigger trigger, List<Action> actions, int timeTrigger) {
        this.id = UUID.randomUUID().toString();
        this.sequenceId = sequenceId;
        this.order = order;
        this.trigger = trigger;
        this.actions = actions;
        this.timeTrigger = timeTrigger;
    }

    public String getStepId() {
        return id;
    }

    public void setStepId(String id) {
        this.id = id;
    }

    public String getSequenceId() {
        return sequenceId;
    }

    public void setSequenceId(String sequenceId) {
        this.sequenceId = sequenceId;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public Trigger getTrigger() {
        return trigger;
    }

    public void setTrigger(Trigger trigger) {
        this.trigger = trigger;
    }

    public List<Action> getActions() {
        return actions;
    }

    public void setActions(List<Action> actions) {
        this.actions = actions;
    }

    public int getTimeTrigger() {
        return timeTrigger;
    }

    public void setTimeTrigger(int timeTrigger) {
        this.timeTrigger = timeTrigger;
    }

    public void save(Context context) {
        if (AppDatabase.getAppDatabase(context).stepDAO().getStep(getStepId()) != null) {
            AppDatabase.getAppDatabase(context).stepDAO().updateStep(this);
        } else {
            AppDatabase.getAppDatabase(context).stepDAO().insertStep(this);
        }
    }
}
