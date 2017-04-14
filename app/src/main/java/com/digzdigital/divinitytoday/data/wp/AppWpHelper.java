package com.digzdigital.divinitytoday.data.wp;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.digzdigital.divinitytoday.data.Devotional;
import com.digzdigital.divinitytoday.data.utils.ParseJSON;

import java.util.ArrayList;

import javax.inject.Inject;


public class AppWpHelper implements WpHelper, Response.Listener<String>, Response.ErrorListener {

    @Inject
    public ParseJSON parseJSON;
    private WpListener listener;
    private Context context;

    public AppWpHelper(Context context) {
        this.context = context;
    }

    @Override
    public void setWpListener(WpListener listener) {
        this.listener = listener;
    }

    @Override
    public void queryForOnlinePosts(int offset) {
        String JSON_URL = "http://divinitytodaydevotional.org/index.php/wp-json/wp/v2/posts?per_page=7&offset=" + offset;//todo test this
        StringRequest stringRequest = new StringRequest(Request.Method.GET, JSON_URL, this, this);
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        listener.onError(error.toString());
    }

    @Override
    public void onResponse(String response) {
        ArrayList<Devotional> devotionals = parseJSON.createDev(response);
        listener.onPostsLoaded(devotionals);
    }
}
