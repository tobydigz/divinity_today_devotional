package com.digzdigital.divinitytoday.ui.devlist.adapter;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.digzdigital.divinitytoday.R;
import com.digzdigital.divinitytoday.data.Devotional;

import java.util.ArrayList;


public class DevotionalAdapter extends RecyclerView.Adapter<DevotionalAdapter.ViewHolder> {

    private static MyClickListener myClickListener;
    private static final int VIEW_TYPE_ITEM = 0;
    private static final int VIEW_TYPE_LOADING = 1;
    private ArrayList<Devotional> devotionals;


    public DevotionalAdapter(ArrayList<Devotional> devotionals) {
        this.devotionals = devotionals;
    }


    @Override
    public int getItemViewType(int position) {
        return devotionals.get(position) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.devotionals_grid, parent, false);
            return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
            Devotional devotional = getItem(position);
            holder.devTitle.setText(devotional.getTitle());
            holder.devDate.setText(devotional.getDate());
    }

    @Override
    public int getItemCount() {
        return devotionals == null ? 0 : devotionals.size();
    }

    public void setOnItemClickListener(MyClickListener myClickListener) {
        DevotionalAdapter.myClickListener = myClickListener;
    }

    private Devotional getItem(int position) {
        return devotionals.get(position);
    }

    public interface MyClickListener {
        void onItemClick(int position, View v);
    }


    static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        CardView cv;
        TextView devTitle, devDate;

        ViewHolder(View itemView) {
            super(itemView);
            cv = (CardView) itemView.findViewById(R.id.cv);
            devTitle = (TextView) itemView.findViewById(R.id.dev_title);
            devDate = (TextView) itemView.findViewById(R.id.dev_date);
            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {
            myClickListener.onItemClick(getAdapterPosition(), v);

        }

    }

}
