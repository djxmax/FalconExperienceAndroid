package fr.maximelucquin.falconexperience.views.SequenceList;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import fr.maximelucquin.falconexperience.R;
import fr.maximelucquin.falconexperience.data.Sequence;

public class SequenceAdapter extends RecyclerView.Adapter<SequenceViewHolder> {

    List<Sequence> list;
    Context context;
    public SequenceAdapterListener onClickListener;

    public SequenceAdapter(List<Sequence> list, Context context, SequenceAdapterListener listener) {
        this.list = list;
        this.context = context;
        this.onClickListener = listener;
    }

    //cette fonction permet de créer les viewHolder
    //et par la même indiquer la vue à inflater (à partir des layout xml)
    @Override
    public SequenceViewHolder onCreateViewHolder(ViewGroup viewGroup, int itemType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.sequence_card_view,viewGroup,false);
        return new SequenceViewHolder(view, onClickListener);
    }

    //c'est ici que nous allons remplir notre cellule avec le texte/image de chaque MyObjects
    @Override
    public void onBindViewHolder(SequenceViewHolder sequenceViewHolder, int position) {
        Sequence sequence = list.get(position);
        sequenceViewHolder.bind(sequence, context);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public interface SequenceAdapterListener {

        void editOnClick(View v, int position);
        void playOnClick(View v, int position);
    }

}



