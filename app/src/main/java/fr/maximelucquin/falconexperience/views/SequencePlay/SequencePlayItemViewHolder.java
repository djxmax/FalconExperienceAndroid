package fr.maximelucquin.falconexperience.views.SequencePlay;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import fr.maximelucquin.falconexperience.R;
import fr.maximelucquin.falconexperience.data.Item;

public class SequencePlayItemViewHolder extends RecyclerView.ViewHolder{

    private LinearLayout itemCardLayout;
    private TextView itemName;



    public SequencePlayItemViewHolder(View itemView) {
        super(itemView);

        itemCardLayout = (LinearLayout) itemView.findViewById(R.id.item_small_card_layout);
        itemName = (TextView) itemView.findViewById(R.id.item_small_name);
    }

    public void bind(Item item, Context context){
        itemName.setText(item.getName());

        if (item.isEnabled()) {
            if (item.getPutType() != null && item.getPutType() == Item.ItemPutType.INPUT) {
                itemCardLayout.setBackgroundColor(Color.RED);
            } else {
                itemCardLayout.setBackgroundResource(R.color.colorAccent);
            }

        } else {
            itemCardLayout.setBackgroundResource(R.color.backgroundClear);
        }
    }
}
