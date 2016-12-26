package com.digzdigital.divinitytoday.devlist;

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
import com.digzdigital.divinitytoday.devlist.adapter.DevotionalAdapter;
import com.digzdigital.divinitytoday.model.Devotional;
import com.digzdigital.divinitytoday.reader.ReaderActivity;

import java.util.ArrayList;
/*
TODO refine api endpoint to leave out content
TODO change to recyclerview and use new model for loaded devotionals plus objects
TODO
*/

public class DevListActivity extends AppCompatActivity implements View.OnClickListener {

    private ArrayList<Devotional> devotionals;
    private RecyclerView rv;
    private DevotionalAdapter devotionalAdapter;
    private Button loadMoreButton;
    private SwipeRefreshLayout swipeContainer;
    private ProgressDialog progressDialog;
    private boolean isLoaded = false;


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
                        RecyclerSetup(response, false);
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
                        Toast.makeText(DevListActivity.this, "Check your internet connection", Toast.LENGTH_LONG).show();
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
                        RecyclerSetup(response, true);
                        Log.d("digz", response);

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        Toast.makeText(DevListActivity.this, "Couldn't load more Devotionals", Toast.LENGTH_LONG).show();
                        Log.d("digz", error.toString());
                    }
                });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void RecyclerSetup(String json, boolean addToList) {
        devotionals = new ParseJSON().createDev(json);
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