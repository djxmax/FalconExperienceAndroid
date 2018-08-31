package fr.maximelucquin.falconexperience.data;

import com.orm.SugarRecord;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Sequence extends SugarRecord<Sequence> {
    public String id;
    public String name;
    public List<Step> steps;

    public Sequence(String name) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.steps = new ArrayList<>();
    }

    public Sequence(String name, List<Step> steps) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.steps = steps;
    }

    public String getIdSequence() {
        return id;
    }

    public void setIdSequence(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Step> getSteps() {
        return steps;
    }

    public void setSteps(List<Step> steps) {
        this.steps = steps;
    }
}
