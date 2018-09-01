package fr.maximelucquin.falconexperience.data.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import fr.maximelucquin.falconexperience.data.Sequence;

@Dao
public interface SequenceDAO {

    @Query("SELECT * FROM sequence WHERE id=:sequenceId")
    public Sequence getSequence(String sequenceId);

    @Query("SELECT * FROM sequence")
    public List<Sequence> getAllSequences();

    @Insert
    public void insertSequence(Sequence sequence);

    @Update
    public void updateSequence(Sequence sequence);

    @Delete
    public void deleteSequence(Sequence sequence);

}
