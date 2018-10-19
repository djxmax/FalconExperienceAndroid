package fr.maximelucquin.falconexperience.views.Sequence;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import fr.maximelucquin.falconexperience.R;
import fr.maximelucquin.falconexperience.data.Step;

public class StepAdapter extends RecyclerView.Adapter<StepViewHolder> {



    List<Step> list;
    Context context;
    int currentStep;

    public void setList(List<Step> list) {
        this.list = list;
    }

    public void setCurrentStep(int currentStep) {
        this.currentStep = currentStep;
    }

    public StepAdapter(List<Step> list, Context context) {
        this.list = list;
        this.context = context;
        this.currentStep = -1;
    }

    public StepAdapter(List<Step> list, Context context, int currentStep) {
        this.list = list;
        this.context = context;
        this.currentStep = currentStep;
    }

    //cette fonction permet de créer les viewHolder
    //et par la même indiquer la vue à inflater (à partir des layout xml)
    @Override
    public StepViewHolder onCreateViewHolder(ViewGroup viewGroup, int itemType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.step_card_view,viewGroup,false);
        return new StepViewHolder(view);
    }

    //c'est ici que nous allons remplir notre cellule avec le texte/image de chaque MyObjects
    @Override
    public void onBindViewHolder(StepViewHolder stepViewHolder, int position) {
        Step step = list.get(position);
        stepViewHolder.bind(step, context, position, currentStep);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

}