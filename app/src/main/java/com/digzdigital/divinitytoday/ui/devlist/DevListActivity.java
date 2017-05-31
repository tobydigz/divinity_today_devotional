package com.digzdigital.divinitytoday.ui.devlist;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.digzdigital.divinitytoday.DivinityTodayApp;
import com.digzdigital.divinitytoday.R;
import com.digzdigital.divinitytoday.data.model.Devotional;
import com.digzdigital.divinitytoday.ui.devlist.adapter.DevotionalAdapter;
import com.digzdigital.divinitytoday.ui.reader.ReaderActivity;

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
    private SwipeRefreshLayout swipeContainer;
    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle b) {
        super.onCreate(b);
        setContentView(R.layout.devotionals_layout);
        ((DivinityTodayApp) getApplication()).getAppComponent().inject(this);

        presenter.setView(this);

        Button loadMoreButton = (Button) findViewById(R.id.loadMoreButton);
        loadMoreButton.setOnClickListener(this);

        rv = (RecyclerView) findViewById(R.id.devotionalsList);
        rv.setLayoutManager(new LinearLayoutManager(this));

        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Your code to refresh the list here.
                // Make sure you call swipeContainer.setRefreshing(false)
                // once the network request has completed successfully.
                presenter.loadDevotionals(presenter.getDevSize());
                swipeContainer.setRefreshing(false);
            }
        });

        // Configure the refreshing colors
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        presenter.loadDevotionals(presenter.getDevSize());
    }


    @Override
    public void doRest(final ArrayList<Devotional> devotionals) {
        if (devotionals == null) return;
        if (devotionals.size() == 0) return;

        DevotionalAdapter devotionalAdapter = new DevotionalAdapter(devotionals);
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


    @Override
    public void showProgressDialog() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Loading");
        progressDialog.setMessage("Please wait...");
        progressDialog.show();
    }

    @Override
    public void dismissProgressDialog() {
        progressDialog.dismiss();
    }

    @Override
    public void makeToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.loadMoreButton:
                presenter.loadDevotionals(presenter.getDevSize());
                break;
        }

    }


}