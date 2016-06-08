package com.digzdigital.dtd;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class DevRowAdapter extends ArrayAdapter<String> {

    private String[] postTitle;
    private String[] postDate;
    private String[] postContent;
    private String[] postId;
    private Integer num;
    private Activity context;

    public DevRowAdapter(Activity context, String[] postTitle, String[] postDate, String[] postContent, String [] postId, Integer num) {
        super(context, R.layout.devotionals_grid, postTitle);
        this.context = context;
        this.postTitle = postTitle;
        this.postDate = postDate;
        this.postContent = postContent;
        this.postId = postId;
        this.num = num;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        LayoutInflater inflater = context.getLayoutInflater();
        View devGridItem = inflater.inflate(R.layout.devotionals_grid, null, true);
        TextView textTitle = (TextView) devGridItem.findViewById(R.id.dev_title);
        TextView textDate = (TextView) devGridItem.findViewById(R.id.dev_date);

        textTitle.setText(postTitle[position]);
        textDate.setText(postDate[position]);

        return devGridItem;
    }
}
