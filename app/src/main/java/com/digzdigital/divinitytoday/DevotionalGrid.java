package com.digzdigital.divinitytoday;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.safety.Whitelist;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
/*
TODO refine api endpoint to leave out content
TODO change to recyclerview and use new model for loaded devotionals plus objects
TODO
*/

public class DevotionalGrid extends AppCompatActivity {

    private static final String JSON_URL = "http://divinitytodaydevotional.org/index.php/wp-json/wp/v2/posts?per_page=5";//todo test this
    ArrayList<Devotional> devotionals;
    TextView dbErrorHandle;
    ImageView image;
    RecyclerView rv;
    DevRowAdapter devRowAdapter;
    private SwipeRefreshLayout swipeContainer;
    private ProgressDialog progressDialog;

    public static String html2ptesxt(String html) {
        Document document = Jsoup.parse(html);
        document.outputSettings(new Document.OutputSettings().prettyPrint(false));//makes html() preserve linebreaks and spacing
        document.select("br").append("\\n");
        document.select("p").prepend("");

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
        image = (ImageView) findViewById(R.id.dropLogo);
        rv = (RecyclerView) findViewById(R.id.devotionalsList);
        rv.setHasFixedSize(true);

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
//                        if (listView.getVisibility() == View.GONE)
//                            listView.setVisibility(View.VISIBLE);
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
//                        listView.setVisibility(View.GONE);
                    }
                });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void showJSON(String json) {
        devotionals = new ArrayList<>();
        String[] postTitle;
        String[] postDate;
        String[] postId;
        String[] postContent;
        JSONObject jsonObject = null;
        JSONArray content = null;
        try {
            content = new JSONArray(json);

            postTitle = new String[content.length()];
            postDate = new String[content.length()];
            postId = new String[content.length()];
            postContent = new String[content.length()];

            for (int i = 0; i < content.length(); i++) {
                JSONObject jo = content.getJSONObject(i);
                postId[i] = jo.getString("id");
                JSONObject title = jo.getJSONObject("title");
                postTitle[i] = Jsoup.parse((title.getString("rendered"))).text();
                postDate[i] = cleanDate(jo.getString("date"));
                JSONObject content1 = jo.getJSONObject("content");
                postContent[i] = html2ptesxt(content1.getString("rendered"));
                devotionals.add(new Devotional(postId[i], postTitle[i], postDate[i], postContent[i]));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        doRest(devotionals);
    }

    public String cleanDate(String dateString) {
        String dateString1 = dateString.replace("T", " ");
        String dateString2 = dateString1.replace("-", "/");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date convertedDate = new Date();
        try {
            convertedDate = dateFormat.parse(dateString2);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        String dayOfTheWeek = (String) android.text.format.DateFormat.format("EEEE", convertedDate);//Thursday
        String stringMonth = (String) android.text.format.DateFormat.format("MMM", convertedDate); //Jun
//        String intMonth = (String) android.text.format.DateFormat.format("MM", date); //06
        String year = (String) android.text.format.DateFormat.format("yyyy", convertedDate); //2013
        String day = (String) android.text.format.DateFormat.format("dd", convertedDate); //20
        return dayOfTheWeek + " " + day + " " + stringMonth + " " + year;
    }

    protected void doRest(ArrayList<Devotional> devotionalList) {
        LinearLayoutManager llm = new LinearLayoutManager(this);
        rv.setLayoutManager(llm);

        devotionals = devotionalList;
        if (devotionalList != null) {
            if (devotionalList.size() != 0) {
                devRowAdapter = new DevRowAdapter(devotionalList);
                rv.setAdapter(devRowAdapter);

                devRowAdapter.setOnItemClickListener(new DevRowAdapter.MyClickListener() {
                    @Override
                    public void onItemClick(int position, View v) {
                        Devotional devotional = devotionals.get(position);
                        String title = devotional.getTitle();
                        String date = devotional.getDate();
                        String content = devotional.getContent();
                        String id = devotional.getPostId();
                        Intent i = new Intent(getApplicationContext(), Reader.class);
                        i.putExtra("title", title);
                        i.putExtra("date", date);
                        i.putExtra("content", content);
                        i.putExtra("id", id);

                        startActivity(i);
                    }
                });
            }
        }

    }
}