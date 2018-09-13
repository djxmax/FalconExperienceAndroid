package fr.maximelucquin.falconexperience.views.ActionList;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import fr.maximelucquin.falconexperience.R;
import fr.maximelucquin.falconexperience.data.Actiion;

public class ActiionViewHolder extends RecyclerView.ViewHolder{

    private TextView actiionType;
    private TextView actiionItems;



    public ActiionViewHolder(View itemView) {
        super(itemView);

        actiionType = (TextView) itemView.findViewById(R.id.actiion_type);
        actiionItems = (TextView) itemView.findViewById(R.id.actiion_items);
    }

    public void bind(Actiion actiion, Context context){
        if (actiion.getType() != null) {
            switch (actiion.getType()) {
                case OFF:
                    actiionType.setText("Type d'action : éteindre");
                    break;
                case ON:
                    actiionType.setText("Type d'action : allumer");
                    break;
            }
        } else {
            actiionType.setText("Type d'action : inconnu");
        }

        if (actiion.getItems(context) != null) {
            actiionItems.setText("Nombre d'item :"+actiion.getItems(context).size());
        } else {
            actiionItems.setText("Nombre d'item : inconnu");
        }

    }
}

