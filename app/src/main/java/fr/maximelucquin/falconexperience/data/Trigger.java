package fr.maximelucquin.falconexperience.data;

import java.util.List;

public class Trigger {
    public List<Item> items;
    public TriggerType type;

    public Trigger(List<Item> items, TriggerType type) {
        this.items = items;
        this.type = type;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public TriggerType getType() {
        return type;
    }

    public void setType(TriggerType type) {
        this.type = type;
    }
}
