package fr.maximelucquin.falconexperience.views.TriggerDetails;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import fr.maximelucquin.falconexperience.R;
import fr.maximelucquin.falconexperience.data.Item;
import fr.maximelucquin.falconexperience.data.Sequence;

import static fr.maximelucquin.falconexperience.data.Item.ItemType.LED;
import static fr.maximelucquin.falconexperience.data.Item.ItemType.LEDSTRIP;

public class ItemViewHolder extends RecyclerView.ViewHolder{

    private TextView itemName;
    private TextView itemType;
    private TextView itemPutType;



    public ItemViewHolder(View itemView) {
        super(itemView);

        itemName = (TextView) itemView.findViewById(R.id.item_name);
        itemType = (TextView) itemView.findViewById(R.id.item_type);
        itemPutType = (TextView) itemView.findViewById(R.id.item_put_type);
    }

    public void bind(Item item, Context context){
        itemName.setText(item.getName());

        if (item.getType() != null) {
            switch (item.getType()) {
                case BUTTON:
                    itemType.setText("Bouton");
                    break;
                case LED:
                    itemType.setText("Lumière");
                    break;
                case LEDSTRIP:
                    itemType.setText("Bandeau lumineux");
                    break;
                case ROUNDLED:
                    itemType.setText("Lumière ronde");
                    break;
                case SQUARELED:
                    itemType.setText("Lumière carré");
                    break;
                case VIDEO:
                    itemType.setText("Vidéo");
                    break;
                case MUSIC:
                    itemType.setText("Musique");
                    break;
                case ALARM:
                    itemType.setText("Alarme");
                    break;
                case SOUND:
                    itemType.setText("Son");
                    break;
                case OTHER:
                    itemType.setText("Autre");
                    break;
            }
        } else {
            itemType.setText("Inconnu");
        }

        if (item.getPutType() != null) {
            switch (item.getPutType()) {
                case INPUT:
                    itemPutType.setText("Entrée");
                    break;
                case OUTPUT:
                    itemPutType.setText("Sortie");
                    break;
            }
        } else {
            itemPutType.setText("Inconnu");
        }

    }
}