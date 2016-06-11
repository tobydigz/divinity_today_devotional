package com.digzdigital.dtd;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.safety.Whitelist;

import io.realm.Realm;
import io.realm.RealmConfiguration;


public class DevotionalGrid extends AppCompatActivity {

    private static final String JSON_URL = "http://divinitytodaydevotional.org/index.php/wp-json/wp/v2/posts";
    ParseJSON pj;
    private ListView listView;
    //    private String titledev, datedev;
    private SwipeRefreshLayout swipeContainer;
    private ProgressDialog progressDialog;
    private String[] Content, Id;
    TextView dbErrorHandle;
    ImageView image;

    public static String html2text(String html) {
        return Jsoup.parse(html).text();
    }
    public static String html2ptesxt(String html){
        Document document = Jsoup.parse(html);
        document.outputSettings(new Document.OutputSettings().prettyPrint(false));//makes html() preserve linebreaks and spacing
        document.select("br").append("\\n");
        document.select("p").prepend("\\n\\n");

        String s = document.html().replaceAll("\\\\n", "\n");

        s = Jsoup.clean(s, "", Whitelist.none(), new Document.OutputSettings().prettyPrint(false));
        String st = s.replace("&nbsp;", "");
        return st;
    }

    @Override
    protected void onCreate(Bundle b) {
        super.onCreate(b);
        setContentView(R.layout.devotionals_layout);

        //Progress bar
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Loading");
        progressDialog.setMessage("Please wait...");
        progressDialog.show();

        dbErrorHandle = (TextView) findViewById(R.id.dbErrorHandle);
        image = (ImageView)findViewById(R.id.dropLogo);


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


    }

    private void sendRequest() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, JSON_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        if (listView.getVisibility() == View.GONE  )listView.setVisibility(View.VISIBLE);
                        showJSON(response);
                        Log.d("digz", response);

                        swipeContainer.setRefreshing(false);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        Toast.makeText(DevotionalGrid.this, "Check your internet connection", Toast.LENGTH_LONG).show();
                        Log.d("digz", error.toString());
                        swipeContainer.setRefreshing(false);
                        dbErrorHandle.setVisibility(View.VISIBLE);
                        image.setVisibility(View.VISIBLE);
                        listView.setVisibility(View.GONE);
                    }
                });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void showJSON(String json) {
        pj = new ParseJSON(json);
        pj.parseJSON();
        DevRowAdapter dv = new DevRowAdapter(this, ParseJSON.postTitle, ParseJSON.postDate, ParseJSON.postContent, ParseJSON.postId, ParseJSON.num);

        Content = new String[ParseJSON.num];
        Id = new String[ParseJSON.num];
        for (int i = 0; i < ParseJSON.num; i++) {
            Content[i] = html2ptesxt(ParseJSON.postContent[i]);
            Id[i] = ParseJSON.postId[i];
        }
        listView.setAdapter(dv);
    }



}