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
        onDelete = ForeignKey.CASCADE))
public class Step implements Comparable<Step> {
    @NonNull
    @PrimaryKey
    public String id;
    public String sequenceId;
    public String note;
    public int order;
    @Ignore
    public Triggeer triggeer;
    @Ignore
    public List<Actiion> actiions;
    public int timeTrigger;

    public Step() {
        this.id = UUID.randomUUID().toString();
    }

    public Step(String sequenceId, int order, Triggeer triggeer, List<Actiion> actiions, int timeTrigger) {
        this.id = UUID.randomUUID().toString();
        this.sequenceId = sequenceId;
        this.order = order;
        this.triggeer = triggeer;
        this.actiions = actiions;
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

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Triggeer getTriggeer(Context context) {
            List<Triggeer> triggeers = AppDatabase.getAppDatabase(context).triggeerDAO().getTriggeersForStep(getStepId());
            if (triggeers != null) {
                if (triggeers.size() > 0) {
                    triggeer = AppDatabase.getAppDatabase(context).triggeerDAO().getTriggeersForStep(getStepId()).get(0);
                }
            }


        return triggeer;
    }

    public Triggeer getTriggeerWithoutReload(Context context) {
        if (triggeer == null) {
            List<Triggeer> triggeers = AppDatabase.getAppDatabase(context).triggeerDAO().getTriggeersForStep(getStepId());
            if (triggeers != null) {
                if (triggeers.size() > 0) {
                    triggeer = AppDatabase.getAppDatabase(context).triggeerDAO().getTriggeersForStep(getStepId()).get(0);
                }
            }
        }

        return triggeer;
    }

    public void setTrigger(Triggeer trigger) {
        this.triggeer = trigger;
    }

    public List<Actiion> getActiions(Context context) {
        actiions = AppDatabase.getAppDatabase(context).actiionDAO().getActiionForStep(getStepId());
        return actiions;
    }

    public void setActiions(List<Actiion> actions) {
        this.actiions = actiions;
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

        if (actiions != null) {
            for (Actiion actiion: actiions) {
                actiion.save(context);
            }
        }

        if (triggeer != null) {
            triggeer.save(context);
        }
    }

    @Override
    public int compareTo(Step step) {
        if (this.getOrder() < step.getOrder()){
            return -1;
        }else{
            return 1;
        }
    }

    public static List<Step> reorderSteps(Context context, List<Step> steps) {
        for(int i = 0; i < steps.size(); i++) {
            steps.get(i).setOrder(i);
        }

        if (steps != null) {
            for (Step step: steps) {
                step.save(context);
            }
        }
        return steps;
    }
}
