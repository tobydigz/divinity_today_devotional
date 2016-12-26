package com.digzdigital.divinitytoday.saveddevlist;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.digzdigital.divinitytoday.home.HomeActivity;
import com.digzdigital.divinitytoday.model.Devotional;
import com.digzdigital.divinitytoday.R;
import com.digzdigital.divinitytoday.devotionallist.adapter.SavedDevotionalsAdapter;
import com.digzdigital.divinitytoday.reader.ReaderActivity;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmQuery;
import io.realm.RealmResults;

public class SavedDevotionalsActivity extends AppCompatActivity {

    RealmResults<Devotional> devotionals;

    SavedDevotionalsAdapter savedDevotionalsAdapter;
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
                savedDevotionalsAdapter = new SavedDevotionalsAdapter(devList, getApplicationContext());
                devotionalListView.setAdapter(savedDevotionalsAdapter);

                savedDevotionalsAdapter.setOnItemClickListener(new SavedDevotionalsAdapter.MyClickListener(){
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

        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }


}
