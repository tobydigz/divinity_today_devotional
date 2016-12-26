package com.digzdigital.divinitytoday.devlist;

import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.digzdigital.divinitytoday.model.Devotional;

import java.util.ArrayList;

/**
 * Created by Digz on 26/12/2016.
 *
 */

public class DevListPresenter implements DevListContract.Presenter{

    private DevListActivity view;
    private ArrayList<Devotional> globalDevotionals = null;

    public DevListPresenter(){

    }

    @Override
    public void setView(DevListActivity view) {
        this.view = view;
    }

    @Override
    public ArrayList<Devotional> getDevotional(int endpoint) {
        String json = null;
        json = sendRequest(endpoint);
        if (json == null) return null;
        return new ParseJSON().createDev(json);
    }


    @Override
    public String sendRequest(int endpoint) {
        final String[] json = {null};
        // TODO: 01/11/2016 Refine endpoint to load data from end
        String JSON_URL = "http://divinitytodaydevotional.org/index.php/wp-json/wp/v2/posts?per_page=7&offset=" + endpoint;//todo test this
        StringRequest stringRequest = new StringRequest(Request.Method.GET, JSON_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        view.dismissProgressDialog();
                        // RecyclerSetup(response, true);
                        json[0] = response;
                        Log.d("digz", response);

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        view.dismissProgressDialog();
                        view.makeToast("Couldn't load more Devotionals");
                        Log.d("digz", error.toString());
                    }
                });
        RequestQueue requestQueue = Volley.newRequestQueue(view.provideContext());
        requestQueue.add(stringRequest);
        return json[0];
    }

    @Override
    public void SetupRecycler(boolean loadMore, int endpoint) {
        view.showProgressDialog();
        if (loadMore){
            ArrayList<Devotional> newDevs = getDevotional(endpoint);
            for (int i = 0; i < newDevs.size(); i++) {
                globalDevotionals.add(newDevs.get(i));
            }
            view.doRest(globalDevotionals);
            return;
        }
        view.doRest(getDevotional(endpoint));

    }

    @Override
    public int getDevSize() {
        if (globalDevotionals == null)return 0;
        return globalDevotionals.size();
    }
}
