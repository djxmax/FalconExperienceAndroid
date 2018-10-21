package fr.maximelucquin.falconexperience.data.database;

import android.arch.persistence.room.TypeConverter;

import fr.maximelucquin.falconexperience.data.Item;

import static fr.maximelucquin.falconexperience.data.Item.ItemOutput.EXTERNAL;
import static fr.maximelucquin.falconexperience.data.Item.ItemOutput.INTERNAL;
import static fr.maximelucquin.falconexperience.data.Item.ItemPutType.INPUT;
import static fr.maximelucquin.falconexperience.data.Item.ItemPutType.OUTPUT;

public class ItemOutputConverter {

    @TypeConverter
    public static Item.ItemOutput toItemOutput(int itemOutput) {
        if (itemOutput == INTERNAL.getCode()) {
            return INTERNAL;
        } else if (itemOutput == EXTERNAL.getCode()) {
            return EXTERNAL;
        } else {
            throw new IllegalArgumentException("Could not recognize trigger type");
        }
    }

    @TypeConverter
    public static int toInteger(Item.ItemOutput itemOutput) {
        if (itemOutput != null) {
            return itemOutput.getCode();
        }
        return 0;
    }
}
