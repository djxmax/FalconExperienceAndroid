package fr.maximelucquin.falconexperience.views.TriggerDetails;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import fr.maximelucquin.falconexperience.R;
import fr.maximelucquin.falconexperience.data.Item;
import fr.maximelucquin.falconexperience.data.Sequence;
import fr.maximelucquin.falconexperience.views.SequenceList.SequenceViewHolder;

public class ItemAdapter extends RecyclerView.Adapter<ItemViewHolder> {

    List<Item> list;
    Context context;

    public ItemAdapter(List<Item> list, Context context) {
        this.list = list;
        this.context = context;
    }

    //cette fonction permet de créer les viewHolder
    //et par la même indiquer la vue à inflater (à partir des layout xml)
    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup viewGroup, int itemType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_card_view,viewGroup,false);
        return new ItemViewHolder(view);
    }

    //c'est ici que nous allons remplir notre cellule avec le texte/image de chaque MyObjects
    @Override
    public void onBindViewHolder(ItemViewHolder itemViewHolder, int position) {
        Item item = list.get(position);
        itemViewHolder.bind(item, context);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

}
