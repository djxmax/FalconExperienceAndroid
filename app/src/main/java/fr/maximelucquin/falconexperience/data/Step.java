package fr.maximelucquin.falconexperience.data;

import com.orm.SugarRecord;

import java.util.List;
import java.util.UUID;

public class Step extends SugarRecord<Step> {
    public String id;
    public int order;
    public Trigger trigger;
    public List<Action> actions;
    public int timeTrigger;

    public Step(int order, Trigger trigger, List<Action> actions, int timeTrigger) {
        this.id = UUID.randomUUID().toString();
        this.order = order;
        this.trigger = trigger;
        this.actions = actions;
        this.timeTrigger = timeTrigger;
    }

    public String getIdStep() {
        return id;
    }

    public void setIdStep(String id) {
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
