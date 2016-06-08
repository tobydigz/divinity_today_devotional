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
    private DatabaseHandler db;

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
        contentLoad = i.getStringExtra("contentLoad");
        id = Integer.valueOf(i.getStringExtra("position"));
        Log.d("title", title);

        textTitle = (TextView) findViewById(R.id.readerTitle);
        textTitle.setText(title);

        textDate = (TextView) findViewById(R.id.readerDate);
        textContent = (TextView) findViewById(R.id.readerContent);
        textDate.setText(date);

        db = new DatabaseHandler(this);

        if (contentLoad == "a"){
            sendRequest();}
        else if (contentLoad == "b"){
            //TODO Check this syntax
            Devotional devotional = db.getDevotional(id);
            textContent.setText(devotional.getContent());
        }

    }

    private void sendRequest(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, JSON_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        showJSON(response);
                        Log.d(tag, response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Reader.this, "Check your internet connection", Toast.LENGTH_LONG).show();
                        String error1 = error.toString();
                        Log.d(tag, error1);
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("title", title);
                params.put("date", date);
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("Content-Type", "charset=utf-8");
                return headers;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
//        requestQueue.add(postRequest);
    }

    private void showJSON(String json){
        pj1 = new ParseJSON1(json);
        pj1.parseJSON1();

        content = Arrays.toString(ParseJSON1.postContent);
//        textContent = (TextView) findViewById(R.id.readerContent);

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
//        title = textTitle.getText().toString();
//        date = textDate.getText().toString();
//        content = textContent.getText().toString();
        db.addDevotional(new Devotional(title, date, content));
        SharedPreferences sharedPreferences = Reader.this.getSharedPreferences("divinity_devotional", Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sharedPreferences.edit();

        //Shared preferences storing goes on here
        edit.putBoolean("anySaved", true);
        edit.commit();
    }
}