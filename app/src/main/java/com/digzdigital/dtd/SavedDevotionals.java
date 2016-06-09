package com.digzdigital.dtd;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Digz on 10/02/2016.
 */
public class SavedDevotionals extends AppCompatActivity{
    private Devotional devotional;
    private ListView listView;

    @Override
    public void onCreate(Bundle a){
        super.onCreate(a);
        setContentView(R.layout.devotionals_layout);

        listView = (ListView) findViewById (R.id.devotionalsList);


//        List<Devotional> devotionals = db.getAllDevotionals();

//        ArrayAdapter<Devotional> adapter = new ArrayAdapter<Devotional>(this, R.layout.devotionals_grid, devotionals);
//        setListAdapter(adapter);



        ListView lvItems = (ListView) findViewById(R.id.devotionalsList);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                TextView textView = (TextView) view.findViewById(R.id.dev_title);
                TextView textView1 = (TextView) view.findViewById(R.id.dev_date);

                String title = textView.getText().toString();
                String date = textView1.getText().toString();

                Intent i = new Intent(getApplicationContext(), Reader.class);
                i.putExtra("title", title);
                i.putExtra("date", date);
                i.putExtra("contentLoad", "b");

                startActivity(i);
            }
        });

    }
}
