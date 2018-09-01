package fr.maximelucquin.falconexperience.data.database;

import android.arch.persistence.room.TypeConverter;

import fr.maximelucquin.falconexperience.data.Item;
import fr.maximelucquin.falconexperience.data.Trigger;

import static fr.maximelucquin.falconexperience.data.Item.ItemType.ALARM;
import static fr.maximelucquin.falconexperience.data.Item.ItemType.BUTTON;
import static fr.maximelucquin.falconexperience.data.Item.ItemType.LED;
import static fr.maximelucquin.falconexperience.data.Item.ItemType.LEDSTRIP;
import static fr.maximelucquin.falconexperience.data.Item.ItemType.MUSIC;
import static fr.maximelucquin.falconexperience.data.Item.ItemType.OTHER;
import static fr.maximelucquin.falconexperience.data.Item.ItemType.ROUNDLED;
import static fr.maximelucquin.falconexperience.data.Item.ItemType.SOUND;
import static fr.maximelucquin.falconexperience.data.Item.ItemType.SQUARELED;
import static fr.maximelucquin.falconexperience.data.Item.ItemType.VIDEO;
import static fr.maximelucquin.falconexperience.data.Trigger.TriggerType.SWITCH_OFF;
import static fr.maximelucquin.falconexperience.data.Trigger.TriggerType.SWITCH_ON;

public class ItemTypeConverter {
    @TypeConverter
    public static Item.ItemType toItemType(int itemType) {
        if (itemType == BUTTON.getCode()) {
            return BUTTON;
        } else if (itemType == LED.getCode()) {
            return LED;
        } else if (itemType == LEDSTRIP.getCode()) {
            return LEDSTRIP;
        } else if (itemType == ROUNDLED.getCode()) {
            return ROUNDLED;
        } else if (itemType == SQUARELED.getCode()) {
            return SQUARELED;
        } else if (itemType == VIDEO.getCode()) {
            return VIDEO;
        } else if (itemType == MUSIC.getCode()) {
            return MUSIC;
        } else if (itemType == ALARM.getCode()) {
            return ALARM;
        } else if (itemType == SOUND.getCode()) {
            return SOUND;
        } else if (itemType == OTHER.getCode()) {
            return OTHER;
        } else {
            throw new IllegalArgumentException("Could not recognize trigger type");
        }
    }

    @TypeConverter
    public static Integer toInteger(Item.ItemType itemType) {
        return itemType.getCode();
    }
}
