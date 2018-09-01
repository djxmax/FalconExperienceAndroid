package fr.maximelucquin.falconexperience.data;

import java.util.List;
import java.util.UUID;

public class Action {
    public String id;
    public List<Item> items;
    public int delay;
    public int duration;
    public boolean blink;
    public int blinkFreq;
    public ActionType type;

    public Action(List<Item> items, int delay, int duration, boolean blink, int blinkFreq, ActionType type) {
        this.id = UUID.randomUUID().toString();
        this.items = items;
        this.delay = delay;
        this.duration = duration;
        this.blink = blink;
        this.blinkFreq = blinkFreq;
        this.type = type;
    }

    public String getIdAction() {
        return id;
    }

    public void setIdAction(String id) {
        this.id = id;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public int getDelay() {
        return delay;
    }

    public void setDelay(int delay) {
        this.delay = delay;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public boolean isBlink() {
        return blink;
    }

    public void setBlink(boolean blink) {
        this.blink = blink;
    }

    public int getBlinkFreq() {
        return blinkFreq;
    }

    public void setBlinkFreq(int blinkFreq) {
        this.blinkFreq = blinkFreq;
    }

    public ActionType getType() {
        return type;
    }

    public void setType(ActionType type) {
        this.type = type;
    }
}
