package fr.maximelucquin.falconexperience.views.SequencePlay;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import fr.maximelucquin.falconexperience.R;
import fr.maximelucquin.falconexperience.data.Item;
import fr.maximelucquin.falconexperience.views.TriggerDetails.ItemViewHolder;

public class SequencePlayItemAdapter extends RecyclerView.Adapter<SequencePlayItemViewHolder> {

    List<Item> list;
    Context context;

    public SequencePlayItemAdapter(List<Item> list, Context context) {
        this.list = list;
        this.context = context;
    }

    //cette fonction permet de créer les viewHolder
    //et par la même indiquer la vue à inflater (à partir des layout xml)
    @Override
    public SequencePlayItemViewHolder onCreateViewHolder(ViewGroup viewGroup, int itemType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_small_card_view,viewGroup,false);
        return new SequencePlayItemViewHolder(view);
    }

    //c'est ici que nous allons remplir notre cellule avec le texte/image de chaque MyObjects
    @Override
    public void onBindViewHolder(SequencePlayItemViewHolder sequencePlayItemViewHolder, int position) {
        Item item = list.get(position);
        sequencePlayItemViewHolder.bind(item, context);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
