package com.digzdigital.divinitytoday.ui.saveddevlist.adapter;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.digzdigital.divinitytoday.R;
import com.digzdigital.divinitytoday.data.model.Devotional;

import java.util.ArrayList;

public class SavedDevotionalsAdapter extends RecyclerView.Adapter<SavedDevotionalsAdapter.ViewHolder> {
    private static MyClickListener myClickListener;
    private static DeleteClickedListener deleteClickedListener;
    private ArrayList<Devotional> devotionals;

    public SavedDevotionalsAdapter(ArrayList<Devotional> devotionals) {
        this.devotionals = devotionals;
    }

    public Devotional getItem(int position) {
        return devotionals.get(position);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.saved_grid, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        Devotional devotional = getItem(position);

        holder.viewTitle.setText(devotional.getTitle());
        holder.viewDate.setText(devotional.getDate());


        // Set a click listener for item remove button
        holder.mRemoveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteClickedListener.onDeleteClicked(holder.getAdapterPosition());





            }
        });
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public int getItemCount() {
        return devotionals.size();
    }

    public void setOnItemClickListener(MyClickListener myClickListener) {
        this.myClickListener = myClickListener;
    }

    public void setOnDeleteClickedListener(DeleteClickedListener deleteClickedListener){
        this.deleteClickedListener = deleteClickedListener;
    }

    public interface MyClickListener {
        public void onItemClick(int position, View v);
    }

    public interface DeleteClickedListener {
        void onDeleteClicked(int position);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        CardView cv;
        TextView viewTitle, viewDate;
        ImageButton mRemoveButton;

        ViewHolder(View itemView) {
            super(itemView);
            cv = (CardView) itemView.findViewById(R.id.cardView);
            viewTitle = (TextView) itemView.findViewById(R.id.dev_title);
            viewDate = (TextView) itemView.findViewById(R.id.dev_date);
            mRemoveButton = (ImageButton) itemView.findViewById(R.id.dev_remove);
            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {
            myClickListener.onItemClick(getAdapterPosition(), v);

        }


    }
}
