package com.digzdigital.divinitytoday;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmQuery;
import io.realm.RealmResults;

public class SavedDevotionals extends AppCompatActivity {

    RealmResults<Devotional> devotionals;

    devListAdapter devListAdapter;
    RecyclerView devotionalListView;
    TextView dbErrorHandle;
    ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.saved_layout);

        dbErrorHandle = (TextView)findViewById(R.id.dbErrorHandle);
        image = (ImageView)findViewById(R.id.dropLogo);
        devotionalListView = (RecyclerView) findViewById(R.id.devotionalsListdb);


        devotionals = getDevotionals();
        doRest(devotionals);
    }


    public RealmResults<Devotional> getDevotionals() {
        RealmConfiguration config = new RealmConfiguration.Builder(this)
                .name("divinity.today")
                .schemaVersion(1)
                .build();
        // Use the config
        Realm realm = Realm.getInstance(config);

        realm.beginTransaction();
        RealmQuery<Devotional> query = realm.where(Devotional.class);
        RealmResults<Devotional> results = query.findAll();
        realm.commitTransaction();
        return results;
    }

    protected void doRest(RealmResults<Devotional> devList) {
        LinearLayoutManager llm = new LinearLayoutManager(this);
        devotionalListView.setLayoutManager(llm);


        devotionals = devList;
        if (devList != null) {
            if (devList.size() != 0) {
                devListAdapter = new devListAdapter(devList, getApplicationContext());
                devotionalListView.setAdapter(devListAdapter);

                devListAdapter.setOnItemClickListener(new devListAdapter.MyClickListener(){
                    @Override
                    public void onItemClick(int position, View v) {
                        Devotional devotional = devotionals.get(position);
                        String title = devotional.getTitle();
                        String date = devotional.getDate();
                        String content = devotional.getContent();
                        String id = devotional.getPostId();
                        Intent intent = new Intent(getApplicationContext(), Reader.class);
                        intent.putExtra("title", title);
                        intent.putExtra("date", date);
                        intent.putExtra("id", id);
                        intent.putExtra("content", content);
                        startActivity(intent);
                    }
                });
            } else {
                //TODO custom no bookings handling
                dbErrorHandle.setVisibility(View.VISIBLE);
                image.setVisibility(View.VISIBLE);
            }
        } else {dbErrorHandle.setVisibility(View.VISIBLE);
            image.setVisibility(View.VISIBLE);}
    }


    @Override
    public void onBackPressed() {

        Intent intent = new Intent(getApplicationContext(), Home_activity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }


}
