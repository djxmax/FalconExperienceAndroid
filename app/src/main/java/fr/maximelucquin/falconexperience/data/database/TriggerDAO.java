package fr.maximelucquin.falconexperience.data.database;

import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import fr.maximelucquin.falconexperience.data.Trigger;

public interface TriggerDAO {

    @Query("SELECT * FROM _trigger WHERE id=:triggerId")
    public Trigger getTrigger(String triggerId);

    @Query("SELECT * FROM _trigger")
    public List<Trigger> getAllTriggers();

    @Query("SELECT * FROM _trigger WHERE stepId=:stepId")
    public List<Trigger> getTriggersForStep(String stepId);

    @Insert
    public void insertTrigger(Trigger trigger);

    @Update
    public void updateTrigger(Trigger trigger);

    @Delete
    public void deleteTrigger(Trigger trigger);
}
