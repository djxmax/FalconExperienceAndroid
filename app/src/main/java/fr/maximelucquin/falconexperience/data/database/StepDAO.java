package fr.maximelucquin.falconexperience.data.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import fr.maximelucquin.falconexperience.data.Sequence;
import fr.maximelucquin.falconexperience.data.Step;

@Dao
public interface StepDAO {

    @Query("SELECT * FROM step WHERE id=:stepId")
    public Sequence getStep(String stepId);

    @Query("SELECT * FROM step")
    public List<Step> getAllSteps();

    @Query("SELECT * FROM step WHERE sequenceId=:sequenceId")
    public List<Step> getStepsForSequence(String sequenceId);

    @Insert
    public void insertStep(Step step);

    @Update
    public void updateStep(Step step);

    @Delete
    public void deleteStep(Step step);
}
