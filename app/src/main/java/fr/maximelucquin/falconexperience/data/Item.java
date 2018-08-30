package fr.maximelucquin.falconexperience.data;

public class Item {
    public String id;
    public ItemType type;
    public ItemPutType putType;

    public Item(String id, ItemType type, ItemPutType putType) {
        this.id = id;
        this.type = type;
        this.putType = putType;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
