package fr.maximelucquin.falconexperience.data.database;

import android.arch.persistence.room.TypeConverter;

import fr.maximelucquin.falconexperience.data.Actiion;

import static fr.maximelucquin.falconexperience.data.Actiion.ActiionType.OFF;
import static fr.maximelucquin.falconexperience.data.Actiion.ActiionType.ON;

public class ActiionTypeConverter {

    @TypeConverter
    public static Actiion.ActiionType toActiionType(int actiionType) {
        if (actiionType == OFF.getCode()) {
            return OFF;
        } else if (actiionType == ON.getCode()) {
            return ON;
        } else {
            throw new IllegalArgumentException("Could not recognize trigger type");
        }
    }

    @TypeConverter
    public static int toInteger(Actiion.ActiionType actiionType) {
        if (actiionType != null) {
            return actiionType.getCode();
        }
        return 0;
    }
}
