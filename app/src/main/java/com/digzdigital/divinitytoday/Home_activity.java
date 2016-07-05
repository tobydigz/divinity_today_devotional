package com.digzdigital.divinitytoday;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

public class Home_activity extends Activity implements View.OnClickListener{

    public boolean anySaved;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

//        final Button Save = (Button) findViewById(R.id.reader);

        findViewById(R.id.reader).setOnClickListener(this);
        findViewById(R.id.saveFile).setOnClickListener(this);
        /*ActionBar actionBar = getActionBar();
        actionBar.hide();*/

//        SharedPreferences sharedPreferences = getSharedPreferences("divinity_devotional", Context.MODE_PRIVATE);
//        anySaved = sharedPreferences.getBoolean("anySaved", false);



        //ADView initialiser
        AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
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

    public final boolean isInternetOn(){

        // get Connectivity Manager object to check connection
        ConnectivityManager connec =
                (ConnectivityManager)getSystemService(getBaseContext().CONNECTIVITY_SERVICE);

        //Check for network connections
        if ((connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTED) ||
                (connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTING) ||
                (connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTING) ||
                (connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTED)) {

            //if connected with internet
            startActivity(new Intent("com.digzdigital.DivinityDevotional"));
            Toast.makeText(this, "Connected", Toast.LENGTH_LONG).show();
            return true;

        }else if(
                connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.DISCONNECTED ||
                        connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.DISCONNECTED  ) {

//            startActivity(new Intent("com.digzdigital.DivinityDevotionalerror"));
            Toast.makeText(this, " Not Connected ", Toast.LENGTH_LONG).show();
            return false;
        }
        return false;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.reader:
                isInternetOn();
                break;
            case R.id.saveFile:
                startActivity(new Intent(getApplicationContext(), SavedDevotionals.class));
                break;
        }
    }
}
