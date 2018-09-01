package fr.maximelucquin.falconexperience.data;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.content.Context;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import fr.maximelucquin.falconexperience.data.database.AppDatabase;

@Entity
public class Sequence {
    @NonNull
    @PrimaryKey
    public String id;
    public String name;
    @Ignore
    public List<Step> steps;

    public Sequence(String name) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.steps = new ArrayList<>();
    }

    public Sequence(String name, List<Step> steps) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.steps = steps;
    }

    public String getSequenceId() {
        return id;
    }

    public void setSequenceId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Step> getSteps(Context context) {
        if (steps == null) {
            steps = AppDatabase.getAppDatabase(context).stepDAO().getStepsForSequence(getSequenceId());
        }
        return steps;
    }

    public void setSteps(List<Step> steps) {
        this.steps = steps;
    }

    public void save(Context context) {
        if (AppDatabase.getAppDatabase(context).sequenceDAO().getSequence(getSequenceId()) != null) {
            AppDatabase.getAppDatabase(context).sequenceDAO().updateSequence(this);
        } else {
            AppDatabase.getAppDatabase(context).sequenceDAO().insertSequence(this);
        }

        if (steps != null) {
            for (Step step: steps) {
                step.save(context);
            }
        }
    }
}
