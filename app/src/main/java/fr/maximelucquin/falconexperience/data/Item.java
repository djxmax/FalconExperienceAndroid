package fr.maximelucquin.falconexperience.data;

import com.orm.SugarRecord;

import java.util.UUID;

public class Item extends SugarRecord<Item> {
    public String id;
    public String name;
    public ItemType type;
    public ItemPutType putType;

    public Item(String name, ItemType type, ItemPutType putType) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.type = type;
        this.putType = putType;
    }

    public String getIdItem() {
        return id;
    }

    public void setIdItem(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ItemType getType() {
        return type;
    }

    public void setType(ItemType type) {
        this.type = type;
    }

    public ItemPutType getPutType() {
        return putType;
    }

    public void setPutType(ItemPutType putType) {
        this.putType = putType;
    }
}
