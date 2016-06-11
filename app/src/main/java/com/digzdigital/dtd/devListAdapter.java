package com.digzdigital.dtd;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import io.realm.RealmResults;

/**
 * Created by Digz on 12/05 /2016. f
 */
public class devListAdapter extends RecyclerView.Adapter<devListAdapter.ViewHolder> {
    private static MyClickListener myClickListener;
    RealmResults<Devotional> devotionals;

    public devListAdapter(RealmResults<Devotional> devotionals) {
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
    public void onBindViewHolder(ViewHolder holder, int position) {
        Devotional devotional = getItem(position);

        holder.viewTitle.setText(devotional.getTitle());
        holder.viewDate.setText(devotional.getDate());
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public int getItemCount() {
        return devotionals.size();
    }


    public interface MyClickListener {
        public void onItemClick(int position, View v);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        CardView cv;
        TextView viewTitle, viewDate;

        ViewHolder(View itemView) {
            super(itemView);
            cv = (CardView) itemView.findViewById(R.id.cardView);
            viewTitle = (TextView) itemView.findViewById(R.id.dev_title);
            viewDate = (TextView) itemView.findViewById(R.id.dev_date);
            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {
            myClickListener.onItemClick(getAdapterPosition(), v);

        }


    }
    public void setOnItemClickListener(MyClickListener myClickListener) {
        this.myClickListener = myClickListener;
    }
}
