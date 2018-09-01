package fr.maximelucquin.falconexperience.views.Sequence;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import fr.maximelucquin.falconexperience.R;
import fr.maximelucquin.falconexperience.data.Step;

public class StepViewHolder extends RecyclerView.ViewHolder{

    private TextView stepOrder;
    private TextView stepTrigger;
    private TextView stepAction;

    public StepViewHolder(View itemView) {
        super(itemView);

        stepOrder = (TextView) itemView.findViewById(R.id.step_order);
        stepTrigger = (TextView) itemView.findViewById(R.id.step_trigger);
        stepAction = (TextView) itemView.findViewById(R.id.step_action);
    }

    public void bind(Step step, Context context, int position){
        stepOrder.setText(position + 1);
        if (step.timeTrigger != 0) {
            stepTrigger.setText("Temps de déclenchement : "+step.getTimeTrigger());
        } else {
            //stepTrigger.setText("Déclencheur : "+step.getStepId());
        }

        //stepAction.setText("Nombre d'action : "+step.getActions().size());
    }
}
