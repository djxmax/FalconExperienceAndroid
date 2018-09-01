package fr.maximelucquin.falconexperience.data.database;

import android.arch.persistence.room.TypeConverter;

import fr.maximelucquin.falconexperience.data.Triggeer;

import static fr.maximelucquin.falconexperience.data.Triggeer.TriggeerType.SWITCH_OFF;
import static fr.maximelucquin.falconexperience.data.Triggeer.TriggeerType.SWITCH_ON;

public class TriggeerTypeConverter {

    @TypeConverter
    public static Triggeer.TriggeerType toTriggeerType(int triggerType) {
        if (triggerType == SWITCH_OFF.getCode()) {
            return SWITCH_OFF;
        } else if (triggerType == SWITCH_ON.getCode()) {
            return SWITCH_ON;
        } else {
            throw new IllegalArgumentException("Could not recognize trigger type");
        }
    }

    @TypeConverter
    public static int toInteger(Triggeer.TriggeerType triggeerType) {
        return triggeerType.getCode();
    }
}
