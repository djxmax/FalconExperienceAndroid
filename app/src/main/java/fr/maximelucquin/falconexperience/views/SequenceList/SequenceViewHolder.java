package fr.maximelucquin.falconexperience.views.SequenceList;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import fr.maximelucquin.falconexperience.R;
import fr.maximelucquin.falconexperience.data.Sequence;

public class SequenceViewHolder extends RecyclerView.ViewHolder{

    private TextView nameSequence;
    private TextView stepSequence;
    private ImageView imageSequence;



    public SequenceViewHolder(View itemView) {
        super(itemView);

        nameSequence = (TextView) itemView.findViewById(R.id.sequence_name);
        stepSequence = (TextView) itemView.findViewById(R.id.sequence_step);
        imageSequence = (ImageView) itemView.findViewById(R.id.sequence_image);
    }

    public void bind(Sequence sequence, Context context){
        nameSequence.setText(sequence.getName());
        if (sequence.getSteps(context) != null) {
            stepSequence.setText("Nombre d'étapes : " + sequence.getSteps(context).size());
        } else {
            stepSequence.setText("Nombre d'étapes : aucune");
        }
        imageSequence.setImageResource(R.drawable.falcon);
    }
}
