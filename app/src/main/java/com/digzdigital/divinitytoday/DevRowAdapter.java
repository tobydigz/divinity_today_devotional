package com.digzdigital.divinitytoday;

import android.app.Activity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.jsoup.Jsoup;

import java.util.ArrayList;

public class DevRowAdapter extends RecyclerView.Adapter<DevRowAdapter.ViewHolder> {
    private static MyClickListener myClickListener;
    ArrayList<Devotional> devotionals;
    private String[] postTitle;
    private String[] postDate;
    private Activity context;
    public DevRowAdapter(ArrayList<Devotional> devotionals) {
        this.devotionals = devotionals;
    }

    public Devotional getItem(int position) {
        return devotionals.get(position);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.devotionals_grid, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        Devotional devotional = getItem(position);

        holder.devTitle.setText(devotional.getTitle());
        holder.devDate.setText(devotional.getDate());
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
    public void setOnItemClickListener(MyClickListener myClickListener) {
        this.myClickListener = myClickListener;
    }
}

