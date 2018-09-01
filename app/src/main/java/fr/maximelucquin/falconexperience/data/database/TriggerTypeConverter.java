package fr.maximelucquin.falconexperience.data.database;

import android.arch.persistence.room.TypeConverter;

import fr.maximelucquin.falconexperience.data.Trigger;

import static fr.maximelucquin.falconexperience.data.Trigger.TriggerType.SWITCH_OFF;
import static fr.maximelucquin.falconexperience.data.Trigger.TriggerType.SWITCH_ON;

public class TriggerTypeConverter {

    @TypeConverter
    public static Trigger.TriggerType toTriggerType(int triggerType) {
        if (triggerType == SWITCH_OFF.getCode()) {
            return SWITCH_OFF;
        } else if (triggerType == SWITCH_ON.getCode()) {
            return SWITCH_ON;
        } else {
            throw new IllegalArgumentException("Could not recognize trigger type");
        }
    }

    @TypeConverter
    public static Integer toInteger(Trigger.TriggerType triggerType) {
        return triggerType.getCode();
    }
}
