package fr.maximelucquin.falconexperience.views.ActionList;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import fr.maximelucquin.falconexperience.R;
import fr.maximelucquin.falconexperience.data.Actiion;
import fr.maximelucquin.falconexperience.data.Sequence;
import fr.maximelucquin.falconexperience.views.SequenceList.SequenceViewHolder;

public class ActiionAdapter extends RecyclerView.Adapter<ActiionViewHolder> {

    List<Actiion> list;
    Context context;

    public ActiionAdapter(List<Actiion> list, Context context) {
        this.list = list;
        this.context = context;
    }

    //cette fonction permet de créer les viewHolder
    //et par la même indiquer la vue à inflater (à partir des layout xml)
    @Override
    public ActiionViewHolder onCreateViewHolder(ViewGroup viewGroup, int itemType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.action_card_view,viewGroup,false);
        return new ActiionViewHolder(view);
    }

    //c'est ici que nous allons remplir notre cellule avec le texte/image de chaque MyObjects
    @Override
    public void onBindViewHolder(ActiionViewHolder actiionViewHolder, int position) {
        Actiion actiion = list.get(position);
        actiionViewHolder.bind(actiion, context);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

}
