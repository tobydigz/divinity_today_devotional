package com.digzdigital.divinitytoday.devlist;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.digzdigital.divinitytoday.R;
import com.digzdigital.divinitytoday.devlist.adapter.DevotionalAdapter;
import com.digzdigital.divinitytoday.model.Devotional;
import com.digzdigital.divinitytoday.reader.ReaderActivity;

import java.util.ArrayList;

import javax.inject.Inject;
/*
TODO refine api endpoint to leave out content
TODO change to recyclerview and use new model for loaded devotionals plus objects
TODO
*/

public class DevListActivity extends AppCompatActivity implements View.OnClickListener, DevListContract.View {

    @Inject
    public DevListPresenter presenter;
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
                presenter.SetupRecycler(isLoaded, presenter.getDevSize());
            }
        });

        // Configure the refreshing colors
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        presenter.SetupRecycler(isLoaded, 0);
    }


    @Override
    public void doRest(final ArrayList<Devotional> devotionals) {
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
    public void addToList(ArrayList<Devotional> devotionals) {


    }

    @Override
    public void showProgressDialog() {
        progressDialog.show();
    }

    @Override
    public void dismissProgressDialog() {
        progressDialog.dismiss();
    }

    @Override
    public Context provideContext() {
        return getApplicationContext();
    }

    @Override
    public void makeToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.loadMoreButton:
                presenter.SetupRecycler(isLoaded, presenter.getDevSize());
                break;
        }

    }
}