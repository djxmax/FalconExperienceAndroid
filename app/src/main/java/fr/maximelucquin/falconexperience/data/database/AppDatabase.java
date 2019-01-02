package fr.maximelucquin.falconexperience.data.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;

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

}
