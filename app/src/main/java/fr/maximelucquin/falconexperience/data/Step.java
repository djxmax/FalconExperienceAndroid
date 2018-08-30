package fr.maximelucquin.falconexperience.data;

import java.util.List;

public class Step {
    public String id;
    public int order;
    public Trigger trigger;
    public List<Action> actions;
    public int timeTrigger;

    public Step(String id, int order, Trigger trigger, List<Action> actions, int timeTrigger) {
        this.id = id;
        this.order = order;
        this.trigger = trigger;
        this.actions = actions;
        this.timeTrigger = timeTrigger;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public Trigger getTrigger() {
        return trigger;
    }

    public void setTrigger(Trigger trigger) {
        this.trigger = trigger;
    }

    public List<Action> getActions() {
        return actions;
    }

    public void setActions(List<Action> actions) {
        this.actions = actions;
    }

    public int getTimeTrigger() {
        return timeTrigger;
    }

    public void setTimeTrigger(int timeTrigger) {
        this.timeTrigger = timeTrigger;
    }
}
