package com.digzdigital.divinitytoday.ui.reader;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.digzdigital.divinitytoday.DivinityTodayApp;
import com.digzdigital.divinitytoday.R;
import com.digzdigital.divinitytoday.data.Devotional;

import javax.inject.Inject;

import io.realm.Realm;
import io.realm.RealmConfiguration;

//import android.os.AsyncTask;

public class ReaderActivity extends AppCompatActivity implements View.OnClickListener, ReaderContract.View {

    // TODO: 26/12/2016 remember to inject this
    @Inject
    public ReaderPresenter presenter;
    private FloatingActionButton saveFab;
    private TextView textContent, textDate;
    private CollapsingToolbarLayout collapsingToolbar;

    @Override
    public void onCreate(Bundle c) {
        super.onCreate(c);
        this.setContentView(R.layout.reader_wrapper);
        ((DivinityTodayApp) getApplication()).getAppComponent().inject(this);
        Toolbar divinityBar = (Toolbar) findViewById(R.id.divinity_toolbar);
        setSupportActionBar(divinityBar);

        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setDisplayHomeAsUpEnabled(true);
        }


        Intent intent = getIntent();

        Bundle bundle = intent.getExtras();
        Devotional devotional = bundle.getParcelable("devotional");
        presenter.setView(this);
        presenter.loadDevotional();

        collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);


        textDate = (TextView) findViewById(R.id.readerDate);
        textContent = (TextView) findViewById(R.id.readerContent);
        saveFab = (FloatingActionButton) findViewById(R.id.save_fab);
        //// TODO: 01/11/2016 make savefab white with pink background
        saveFab.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {
        if (presenter.isSaved()) {
            presenter.updateDb();
            return;
        }
        Snackbar.make(textContent, "This OnlineDevotional is already saved", Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void displayTitle(String title) {
        Typeface tf = createTypeface();
        if (collapsingToolbar != null) {
            collapsingToolbar.setTitle(title);
            collapsingToolbar.setCollapsedTitleTypeface(tf);
            collapsingToolbar.setExpandedTitleTypeface(tf);
        }
    }

    private Typeface createTypeface() {
        return Typeface.createFromAsset(this.getAssets(), "fonts/Nexa_Bold.otf");

    }

    @Override
    public void displayDate(String date) {
        textDate.setTypeface(createTypeface());
        textDate.setText(date);
    }

    @Override
    public void displayContent(String content) {
        textContent.setText(content);
    }

    @Override
    public void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();

    }
}