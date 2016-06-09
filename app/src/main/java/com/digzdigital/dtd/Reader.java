package com.digzdigital.dtd;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.content.Intent;
//import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Config;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;


import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class Reader extends AppCompatActivity{

    public static String id, title, content, contentLoad, date;
    private TextView textTitle, textDate, textContent;
    MenuItem mnu1;


    @Override
    public void onCreate(Bundle c){
        super.onCreate(c);
        this.setContentView(R.layout.reader_wrapper);
        Toolbar divinityBar = (Toolbar) findViewById(R.id.divinity_toolbar);
        setSupportActionBar(divinityBar);

        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);


        Intent i = getIntent();
        title = i.getStringExtra("title");
        date = i.getStringExtra("date");
        id = i.getStringExtra("id");
        content=i.getStringExtra("content");
        Log.d("title", title);

        textTitle = (TextView) findViewById(R.id.readerTitle);
        textTitle.setText(title);

        textDate = (TextView) findViewById(R.id.readerDate);
        textContent = (TextView) findViewById(R.id.readerContent);
        textDate.setText(date);
        textContent.setText(content);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return MenuChoice(item);
    }

    private boolean MenuChoice(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.saveDevotional:
                saveDevotional();
                return true;
           default:
               return super.onOptionsItemSelected(item);
        }
    }

    private void saveDevotional(){
        updateDb(id, title, content, date);
        SharedPreferences sharedPreferences = Reader.this.getSharedPreferences("divinity_devotional", Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sharedPreferences.edit();

        //Shared preferences storing goes on here
        edit.putBoolean("anySaved", true);
        edit.commit();
    }

    public void updateDb(String id, String title, String content, String date) {
        RealmConfiguration config = new RealmConfiguration.Builder(this)
                .name("drop.taxi")
                .schemaVersion(42)
                .build();
        // Use the config
        Realm realm = Realm.getInstance(config);

        realm.beginTransaction();
        Devotional devotional = realm.createObject(Devotional.class);
        devotional.setPostId(id);
        devotional.setTitle(title);
        devotional.setContent(content);
        devotional.setDate(date);
        realm.commitTransaction();
    }
}