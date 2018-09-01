package fr.maximelucquin.falconexperience.data.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import fr.maximelucquin.falconexperience.data.Actiion;

@Dao
public interface ActiionDAO {

    @Query("SELECT * FROM actiion WHERE id=:actiionId")
    public Actiion getActiion(String actiionId);

    @Query("SELECT * FROM actiion")
    public List<Actiion> getAllActiions();

    @Query("SELECT * FROM actiion WHERE stepId=:stepId")
    public List<Actiion> getActiionForStep(String stepId);

    @Insert
    public void insertActiion(Actiion actiion);

    @Update
    public void updateActiion(Actiion actiion);

    @Delete
    public void deleteActiion(Actiion actiion);
}
