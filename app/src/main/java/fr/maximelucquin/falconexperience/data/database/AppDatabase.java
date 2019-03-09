package fr.maximelucquin.falconexperience.data.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

import fr.maximelucquin.falconexperience.data.Actiion;
import fr.maximelucquin.falconexperience.data.Item;
import fr.maximelucquin.falconexperience.data.Sequence;
import fr.maximelucquin.falconexperience.data.Step;
import fr.maximelucquin.falconexperience.data.Triggeer;

@Database(entities = {Sequence.class, Step.class, Triggeer.class, Actiion.class, Item.class, TriggeerItemJoin.class, ActiionItemJoin.class}, version = 2)
@TypeConverters({ActiionTypeConverter.class,ItemTypeConverter.class,ItemPutTypeConverter.class,TriggeerTypeConverter.class})
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase INSTANCE;

    public abstract SequenceDAO sequenceDAO();
    public abstract StepDAO stepDAO();
    public abstract TriggeerDAO triggeerDAO();
    public abstract ActiionDAO actiionDAO();
    public abstract ItemDAO itemDAO();
    public abstract TriggerItemJoinDAO triggeerItemJoinDAO();
    public abstract ActiionItemJoinDAO actiionItemJoinDAO();

    public static AppDatabase getAppDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE =
                    Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "falcon_experience_db")
                            // allow queries on the main thread.
                            // Don't do this on a real app! See PersistenceBasicSample for an example.
                            .allowMainThreadQueries()
                            .build();
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }

    /*public boolean importDatabase(String dbPath) throws IOException {

        // Close the SQLiteOpenHelper so it will commit the created empty
        // database to internal storage.
        close();
        File newDb = new File(dbPath);
        File oldDb = new File(DB_FILEPATH);
        if (newDb.exists()) {
            AppDatabase.copyFile(new FileInputStream(newDb), new FileOutputStream(oldDb));
            // Access the copied database so SQLiteHelper will cache it and mark
            // it as created.
            AppDatabase.getAppDatabase().close();
            return true;
        }
        return false;
    }

    public static void copyFile(FileInputStream fromFile, FileOutputStream toFile) throws IOException {
        FileChannel fromChannel = null;
        FileChannel toChannel = null;
        try {
            fromChannel = fromFile.getChannel();
            toChannel = toFile.getChannel();
            fromChannel.transferTo(0, fromChannel.size(), toChannel);
        } finally {
            try {
                if (fromChannel != null) {
                    fromChannel.close();
                }
            } finally {
                if (toChannel != null) {
                    toChannel.close();
                }
            }
        }
    }*/

}
