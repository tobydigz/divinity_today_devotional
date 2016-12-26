package com.digzdigital.divinitytoday.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.digzdigital.divinitytoday.R;
import com.digzdigital.divinitytoday.model.Devotional;

import io.realm.Realm;
import io.realm.RealmConfiguration;

//import android.os.AsyncTask;

public class ReaderActivity extends AppCompatActivity implements View.OnClickListener{

    private int id;
    private Devotional devotional;
    private FloatingActionButton saveFab;
    private TextView textContent;

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


        Intent intent = getIntent();

        Bundle bundle = intent.getExtras();
        devotional = bundle.getParcelable("devotional");

        CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        final Typeface tf = Typeface.createFromAsset(this.getAssets(), "fonts/Nexa_Bold.otf");
        if (collapsingToolbar != null) {
            collapsingToolbar.setTitle(devotional.getTitle());
            collapsingToolbar.setCollapsedTitleTypeface(tf);
            collapsingToolbar.setExpandedTitleTypeface(tf);
        }


        TextView textDate = (TextView) findViewById(R.id.readerDate);
         textContent = (TextView) findViewById(R.id.readerContent);
        saveFab = (FloatingActionButton) findViewById(R.id.save_fab);
        //// TODO: 01/11/2016 make savefab white with pink background
        saveFab.setOnClickListener(this);

        textDate.setTypeface(tf);
        textDate.setText(devotional.getDate());
        textContent.setText(devotional.getContent());
    }



    private void saveDevotional() {
        updateDb();
        SharedPreferences sharedPreferences = ReaderActivity.this.getSharedPreferences("divinity_devotional", Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sharedPreferences.edit();

        //Shared preferences storing goes on here
        edit.putBoolean("anySaved", true);
        edit.commit();
    }

    public void updateDb() {
        RealmConfiguration config = new RealmConfiguration.Builder(this)
                .name("divinity.today")
                .schemaVersion(1)
                .build();
        // Use the config
        Realm realm = Realm.getInstance(config);

        realm.beginTransaction();
        Devotional devotional = realm.createObject(Devotional.class);
        devotional.setPostId(devotional.getPostId());
        devotional.setTitle(devotional.getTitle());
        devotional.setContent(devotional.getContent());
        devotional.setDate(devotional.getDate());
        devotional.setSaved(true);
        realm.commitTransaction();
        Toast.makeText(this, "Devotional Saved", Toast.LENGTH_SHORT).show();
    }

    private boolean isSaved() {
        return devotional.isSaved();
    }

    @Override
    public void onClick(View view) {
        if (!isSaved()){
            saveDevotional();
        }else {
            Snackbar.make(textContent, "This Devotional is already saved", Snackbar.LENGTH_LONG).show();
        }
    }
}