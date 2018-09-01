package fr.maximelucquin.falconexperience.data.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import fr.maximelucquin.falconexperience.data.Triggeer;

@Dao
public interface TriggeerDAO {

    @Query("SELECT * FROM triggeer WHERE id=:triggeerId")
    public Triggeer getTriggeer(String triggeerId);

    @Query("SELECT * FROM triggeer")
    public List<Triggeer> getAllTriggeers();

    @Query("SELECT * FROM triggeer WHERE stepId=:stepId")
    public List<Triggeer> getTriggeersForStep(String stepId);

    @Insert
    public void insertTriggeer(Triggeer triggeer);

    @Update
    public void updateTriggeer(Triggeer triggeer);

    @Delete
    public void deleteTriggeer(Triggeer triggeer);
}
