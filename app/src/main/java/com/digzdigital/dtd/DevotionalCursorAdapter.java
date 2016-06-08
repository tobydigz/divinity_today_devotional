package com.digzdigital.dtd;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

/**
 * Created by Digz on 15/02/2016.
 */
public class DevotionalCursorAdapter extends CursorAdapter {
    public DevotionalCursorAdapter(Context context, Cursor cursor/*, int flags*/){
        super(context, cursor, 0);
    }
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.devotionals_grid, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView devTitle = (TextView) view.findViewById(R.id.dev_title);
        TextView devDate = (TextView) view.findViewById(R.id.dev_date);

        String title = cursor.getString(cursor.getColumnIndexOrThrow("title"));
        String date = cursor.getString(cursor.getColumnIndexOrThrow("date"));

        devTitle.setText(title);
        devDate.setText(date);
    }


}
