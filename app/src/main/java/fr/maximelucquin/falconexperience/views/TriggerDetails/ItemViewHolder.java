package fr.maximelucquin.falconexperience.views.TriggerDetails;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import fr.maximelucquin.falconexperience.R;
import fr.maximelucquin.falconexperience.data.Item;

public class ItemViewHolder extends RecyclerView.ViewHolder{

    private CardView itemCard;
    private LinearLayout itemCardLayout;
    private TextView itemName;
    private TextView itemType;
    private TextView itemPutType;



    public ItemViewHolder(View itemView) {
        super(itemView);

        itemCard = (CardView) itemView.findViewById(R.id.item_card);
        itemCardLayout = (LinearLayout) itemView.findViewById(R.id.item_card_layout);
        itemName = (TextView) itemView.findViewById(R.id.item_name);
        itemType = (TextView) itemView.findViewById(R.id.item_type);
        itemPutType = (TextView) itemView.findViewById(R.id.item_put_type);
    }

    public void bind(Item item, Context context, List<Item> selectedList){
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

        if (selectedList != null) {
            if (Item.containsItem(selectedList,item.getItemId())) {
                itemCardLayout.setBackgroundResource(R.color.colorAccent);
            } else {
                itemCardLayout.setBackgroundResource(R.color.backgroundClear);

            }
        } else {
            itemCardLayout.setBackgroundResource(R.color.backgroundClear);
        }

    }
}