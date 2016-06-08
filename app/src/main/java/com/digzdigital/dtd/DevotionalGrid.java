package com.digzdigital.dtd;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.Date;
import java.util.GregorianCalendar;

import io.realm.Realm;
import io.realm.RealmConfiguration;


public class DevotionalGrid extends AppCompatActivity {

    private ListView listView;
    private static final String JSON_URL = "http://divinitytodaydevotional.org/divinity_android.php";
    ParseJSON pj;
//    private String titledev, datedev;
    private SwipeRefreshLayout swipeContainer;
    private ProgressDialog progressDialog;
    private String[] Content, Id;


    @Override
    protected void onCreate(Bundle b){
        super.onCreate(b);
        setContentView(R.layout.devotionals_layout);

        //Progress bar
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Loading");
        progressDialog.setMessage("Please wait...");
        progressDialog.show();


        listView = (ListView) findViewById(R.id.devotionalsList);
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
                i.putExtra("position", position);
                i.putExtra("content", Content[position]);
                i.putExtra("id", Id[position]);

                startActivity(i);
            }
        });

        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Your code to refresh the list here.
                // Make sure you call swipeContainer.setRefreshing(false)
                // once the network request has completed successfully.
                sendRequest();
//                fetchTimelineAsync(0);
            }
        });

        // Configure the refreshing colors
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);


        sendRequest();
        Content = new String[listView.getCount()];
        Id = new String[listView.getCount()];

    }

    private void sendRequest(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, JSON_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        showJSON(response);

                        swipeContainer.setRefreshing(false);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        Toast.makeText(DevotionalGrid.this, "Check your internet connection", Toast.LENGTH_LONG).show();
                        swipeContainer.setRefreshing(false);
                    }
                });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void showJSON(String json){
        pj = new ParseJSON(json);
        pj.parseJSON();
        DevRowAdapter dv = new DevRowAdapter(this, ParseJSON.postTitle, ParseJSON.postDate, ParseJSON.postContent, ParseJSON.postId, ParseJSON.num);
        for(int i=0; i < listView.getCount(); i++)
        {

            Content[i] = ParseJSON.postContent[i];
            Id[i]=ParseJSON.postId[i];
        }
        listView.setAdapter(dv);
    }
public void updateDb(String id, String title, String content, String date){
    RealmConfiguration config = new RealmConfiguration.Builder(this)
            .name("drop.taxi")
            .schemaVersion(42)
            .build();
// Use the config
    Realm realm = Realm.getInstance(config);

    realm.beginTransaction();
    Devotional devotional = realm.createObject(Devotional.class);
    devotional.setID(id);
    devotional.setTitle(title);
    devotional.setContent(content);
    devotional.setDate(date);
    realm.commitTransaction();

}

}