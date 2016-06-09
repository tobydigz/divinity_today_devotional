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

public class Reader extends AppCompatActivity{

    public static String title, content, contentLoad, date;
    public static Integer id;
    private TextView textTitle, textDate, textContent;
    MenuItem mnu1;
    String tag = "Lifecycle";

    private static final String JSON_URL = "http://divinitytodaydevotional.org/divinity_android2.php";
    private ParseJSON1 pj1;

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
        id = Integer.valueOf(i.getStringExtra("position"));
        Log.d("title", title);

        textTitle = (TextView) findViewById(R.id.readerTitle);
        textTitle.setText(title);

        textDate = (TextView) findViewById(R.id.readerDate);
        textContent = (TextView) findViewById(R.id.readerContent);
        textDate.setText(date);




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
        SharedPreferences sharedPreferences = Reader.this.getSharedPreferences("divinity_devotional", Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sharedPreferences.edit();

        //Shared preferences storing goes on here
        edit.putBoolean("anySaved", true);
        edit.commit();
    }
}