package fr.maximelucquin.falconexperience.data;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.util.UUID;

@Entity
public class Item {
    @NonNull
    @PrimaryKey
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

    public String getItemId() {
        return id;
    }

    public void setItemId(String id) {
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
