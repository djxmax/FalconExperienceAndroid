package fr.maximelucquin.falconexperience.data.database;

import android.arch.persistence.room.TypeConverter;

import fr.maximelucquin.falconexperience.data.Item;

import static fr.maximelucquin.falconexperience.data.Item.ItemPutType.INPUT;
import static fr.maximelucquin.falconexperience.data.Item.ItemPutType.OUTPUT;

public class ItemPutTypeConverter {

    @TypeConverter
    public static Item.ItemPutType toItemPutType(int itemPutType) {
        if (itemPutType == INPUT.getCode()) {
            return INPUT;
        } else if (itemPutType == OUTPUT.getCode()) {
            return OUTPUT;
        } else {
            throw new IllegalArgumentException("Could not recognize trigger type");
        }
    }

    @TypeConverter
    public static int toInteger(Item.ItemPutType itemPutType) {
        if (itemPutType != null) {
            return itemPutType.getCode();
        }
        return 0;
    }
}
