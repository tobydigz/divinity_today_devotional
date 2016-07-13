package com.digzdigital.divinitytoday;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import io.realm.Realm;
import io.realm.RealmConfiguration;

//import android.os.AsyncTask;

public class Reader extends AppCompatActivity {

    public static String id, title, content, contentLoad, date;
    MenuItem mnu1;
    private TextView textTitle, textDate, textContent;

    @Override
    public void onCreate(Bundle c) {
        super.onCreate(c);
        this.setContentView(R.layout.reader_wrapper);
        Toolbar divinityBar = (Toolbar) findViewById(R.id.divinity_toolbar);
        setSupportActionBar(divinityBar);

        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setDisplayHomeAsUpEnabled(true);
        }


        Intent i = getIntent();
        title = i.getStringExtra("title");
        date = i.getStringExtra("date");
        id = i.getStringExtra("id");
        //TODO use id to send new request
//        if (i.getBooleanExtra("saved", false)) {
//
//        } else {
            content = i.getStringExtra("content");
            Log.d("title", title);
//        }

        CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        final Typeface tf = Typeface.createFromAsset(this.getAssets(), "fonts/Nexa_Bold.otf");
        if (collapsingToolbar != null) {
            collapsingToolbar.setTitle(title);
            collapsingToolbar.setCollapsedTitleTypeface(tf);
            collapsingToolbar.setExpandedTitleTypeface(tf);
        }


        textDate = (TextView) findViewById(R.id.readerDate);
        textContent = (TextView) findViewById(R.id.readerContent);
        textDate.setTypeface(tf);
        textDate.setText(date);
        textContent.setText(content);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return MenuChoice(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.divinity_bar, menu);
        return true;
    }

    private boolean MenuChoice(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.saveDevotional:
                saveDevotional();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void saveDevotional() {
        updateDb(id, title, content, date);
        SharedPreferences sharedPreferences = Reader.this.getSharedPreferences("divinity_devotional", Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sharedPreferences.edit();

        //Shared preferences storing goes on here
        edit.putBoolean("anySaved", true);
        edit.commit();
    }

    public void updateDb(String id, String title, String content, String date) {
        RealmConfiguration config = new RealmConfiguration.Builder(this)
                .name("divinity.today")
                .schemaVersion(1)
                .build();
        // Use the config
        Realm realm = Realm.getInstance(config);

        realm.beginTransaction();
        Devotional devotional = realm.createObject(Devotional.class);
        devotional.setPostId(id);
        devotional.setTitle(title);
        devotional.setContent(content);
        devotional.setDate(date);
        realm.commitTransaction();
        Toast.makeText(this, "Devotional Saved", Toast.LENGTH_SHORT).show();
    }
}