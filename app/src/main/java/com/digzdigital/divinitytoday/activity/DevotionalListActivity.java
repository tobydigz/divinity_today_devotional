package com.digzdigital.divinitytoday.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.digzdigital.divinitytoday.R;
import com.digzdigital.divinitytoday.adapter.DevotionalAdapter;
import com.digzdigital.divinitytoday.model.Devotional;

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

public class DevotionalListActivity extends AppCompatActivity implements View.OnClickListener {

    private ArrayList<Devotional> devotionals;
    private RecyclerView rv;
    private DevotionalAdapter devotionalAdapter;
    private Button loadMoreButton;
    private SwipeRefreshLayout swipeContainer;
    private ProgressDialog progressDialog;
    private boolean isLoaded = false;

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

        loadMoreButton = (Button) findViewById(R.id.loadMoreButton);
        loadMoreButton.setOnClickListener(this);
        rv = (RecyclerView) findViewById(R.id.devotionalsList);
//        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(this));

        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Your code to refresh the list here.
                // Make sure you call swipeContainer.setRefreshing(false)
                // once the network request has completed successfully.
                sendRequest();
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
        // TODO: 01/11/2016 Refine endpoint to load data from end
        String JSON_URL = "http://divinitytodaydevotional.org/index.php/wp-json/wp/v2/posts?per_page=7";//todo test this
        StringRequest stringRequest = new StringRequest(Request.Method.GET, JSON_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
//                        if (listView.getVisibility() == View.GONE)
//                            listView.setVisibility(View.VISIBLE);
                        showJSON(response, false);
                        isLoaded = true;
                        Log.d("digz", response);

                        loadMoreButton.setText("Load More");
                        swipeContainer.setRefreshing(false);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        Toast.makeText(DevotionalListActivity.this, "Check your internet connection", Toast.LENGTH_LONG).show();
                        Log.d("digz", error.toString());
                        swipeContainer.setRefreshing(false);
                        loadMoreButton.setText("Retry");
//                        listView.setVisibility(View.GONE);
                    }
                });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void sendRequest(int endPoint) {
        // TODO: 01/11/2016 Refine endpoint to load data from end
        String JSON_URL = "http://divinitytodaydevotional.org/index.php/wp-json/wp/v2/posts?per_page=7&offset=" + endPoint;//todo test this
        StringRequest stringRequest = new StringRequest(Request.Method.GET, JSON_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        showJSON(response, true);
                        Log.d("digz", response);

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        Toast.makeText(DevotionalListActivity.this, "Couldn't load more Devotionals", Toast.LENGTH_LONG).show();
                        Log.d("digz", error.toString());
                    }
                });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void showJSON(String json, boolean addToList) {
        devotionals = new ArrayList<>();
        String[] postTitle;
        String[] postDate;
        int[] postId;
        String[] postContent;
        JSONObject jsonObject = null;
        JSONArray content = null;
        try {
            content = new JSONArray(json);

            postTitle = new String[content.length()];
            postDate = new String[content.length()];
            postId = new int[content.length()];
            postContent = new String[content.length()];

            for (int i = 0; i < content.length(); i++) {
                JSONObject jo = content.getJSONObject(i);
                postId[i] = jo.getInt("id");
                JSONObject title = jo.getJSONObject("title");
                postTitle[i] = Jsoup.parse((title.getString("rendered"))).text();
                postDate[i] = cleanDate(jo.getString("date"));
                JSONObject content1 = jo.getJSONObject("content");
                postContent[i] = html2ptesxt(content1.getString("rendered"));
                devotionals.add(new Devotional(postId[i], postTitle[i], postDate[i], postContent[i], false));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (!addToList) doRest();
        else addToList();
    }

    private void addToList() {
        devotionals.add(null);
        devotionalAdapter.notifyItemInserted(devotionals.size() - 1);

        //Loading more data

        //Remove loading item
        devotionals.remove(devotionals.size() - 1);
        devotionalAdapter.notifyItemRemoved(devotionals.size());

        //Load data
        int startPoint = devotionals.size();
        sendRequest(startPoint);

        devotionalAdapter.notifyDataSetChanged();

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

    protected void doRest() {
        if (devotionals != null) {
            if (devotionals.size() != 0) {
                devotionalAdapter = new DevotionalAdapter(devotionals);
                rv.setAdapter(devotionalAdapter);

                devotionalAdapter.setOnItemClickListener(new DevotionalAdapter.MyClickListener() {
                    @Override
                    public void onItemClick(int position, View v) {
                        Devotional devotional = devotionals.get(position);
                        Intent intent = new Intent(getApplicationContext(), ReaderActivity.class);
                        Bundle args = new Bundle();
                        args.putParcelable("devotional", devotional);
                        intent.putExtras(args);
                        startActivity(intent);
                    }
                });


            }
        }

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.loadMoreButton:
                if (!isLoaded) {
                    sendRequest();
                } else {
                    progressDialog.show();
                    sendRequest(devotionals.size());
                }
                break;
        }

    }
}