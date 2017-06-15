package com.digzdigital.divinitytoday.ui.home;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.digzdigital.divinitytoday.R;
import com.digzdigital.divinitytoday.ui.saveddevlist.SavedDevotionalsActivity;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

public class HomeActivity extends Activity implements View.OnClickListener {

    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        sharedPreferences = getSharedPreferences("dtd", Context.MODE_PRIVATE);
        if (getFirstRun()) {
            setRunned();
        }

        findViewById(R.id.reader).setOnClickListener(this);
        findViewById(R.id.saveFile).setOnClickListener(this);

        //ADView initialiser
        AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
    }

    public boolean getFirstRun() {
        return sharedPreferences.getBoolean("firstRun", true);
    }

    public boolean setRunned() {
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putBoolean("firstRun", false);
        return edit.commit();
    }

    public void onBackPressed() {
        System.exit(0);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.reader:
                // startActivity(new Intent(this, DevListActivity.class));
                break;
            case R.id.saveFile:
                startActivity(new Intent(this, SavedDevotionalsActivity.class));
                break;
        }
    }
}
