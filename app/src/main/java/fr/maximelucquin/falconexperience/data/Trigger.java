package fr.maximelucquin.falconexperience.data;

import java.util.List;
import java.util.UUID;

public class Trigger {

    public String id;
    public List<Item> items;
    public TriggerType type;

    public Trigger(List<Item> items, TriggerType type) {
        this.id = UUID.randomUUID().toString();
        this.items = items;
        this.type = type;
    }

    public String getIdTrigger() {
        return id;
    }

    public void setIdTrigger(String id) {
        this.id = id;
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
