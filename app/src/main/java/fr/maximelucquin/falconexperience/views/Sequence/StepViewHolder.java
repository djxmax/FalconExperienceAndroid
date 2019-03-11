package fr.maximelucquin.falconexperience.views.Sequence;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import fr.maximelucquin.falconexperience.R;
import fr.maximelucquin.falconexperience.data.Step;

public class StepViewHolder extends RecyclerView.ViewHolder{

    private TextView stepOrder;
    private TextView stepTrigger;
    private TextView stepAction;
    private TextView stepNote;
    private LinearLayout stepBackground;

    public StepViewHolder(View itemView) {
        super(itemView);

        stepOrder = (TextView) itemView.findViewById(R.id.step_order);
        stepTrigger = (TextView) itemView.findViewById(R.id.step_trigger);
        stepAction = (TextView) itemView.findViewById(R.id.step_action);
        stepNote = (TextView) itemView.findViewById(R.id.step_note);
        stepBackground = (LinearLayout) itemView.findViewById(R.id.step_background);
    }

    public void bind(Step step, Context context, int position, int currentStep){
        int order = step.getOrder() + 1;
        stepOrder.setText(""+order);
        if (step.timeTrigger != 0) {
            stepTrigger.setText("Temps de déclenchement : "+step.getTimeTrigger());
        } else {
            stepTrigger.setText("Déclencheur : items");
        }

        if (step.getActiions(context) != null) {
            stepAction.setText("Nombre d'action : "+step.getActiions(context).size());
        } else {
            stepAction.setText("Nombre d'action : inconnu");
        }

        if (currentStep != -1 && currentStep == position) {
            stepBackground.setBackgroundResource(R.color.colorAccent);
        } else {
            stepBackground.setBackgroundResource(R.color.backgroundClear);
        }

        if (step.getNote() != null) {
            stepNote.setText("Note : " + step.getNote());
        } else {
            stepNote.setText("Note : aucune");
        }

    }
}
