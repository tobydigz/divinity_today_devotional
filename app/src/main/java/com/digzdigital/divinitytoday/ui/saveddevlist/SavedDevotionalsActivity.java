package com.digzdigital.divinitytoday.ui.saveddevlist;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.digzdigital.divinitytoday.DivinityTodayApp;
import com.digzdigital.divinitytoday.R;
import com.digzdigital.divinitytoday.data.model.Devotional;
import com.digzdigital.divinitytoday.ui.home.HomeActivity;
import com.digzdigital.divinitytoday.ui.reader.ReaderActivity;
import com.digzdigital.divinitytoday.ui.saveddevlist.adapter.SavedDevotionalsAdapter;

import java.util.ArrayList;

import javax.inject.Inject;

public class SavedDevotionalsActivity extends AppCompatActivity implements SavedDevotionalsContract.View {

    @Inject
    public SavedDevotionalsPresenter presenter;
    private SavedDevotionalsAdapter savedDevotionalsAdapter;
    private RecyclerView devotionalListView;
    private TextView dbErrorHandle;
    private ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_saved_devotionals);


        ((DivinityTodayApp) getApplication()).getAppComponent().inject(this);
presenter.setView(this);

        dbErrorHandle = (TextView) findViewById(R.id.dbErrorHandle);
        image = (ImageView) findViewById(R.id.dropLogo);
        devotionalListView = (RecyclerView) findViewById(R.id.devotionalsListdb);
        presenter.loadDevotionals();

    }

    @Override
    public void doRest(final ArrayList<Devotional> devList) {
        LinearLayoutManager llm = new LinearLayoutManager(this);
        devotionalListView.setLayoutManager(llm);

        if (devList == null || devList.size() == 0) {
            dbErrorHandle.setVisibility(View.VISIBLE);
            image.setVisibility(View.VISIBLE);
            return;
        }
        savedDevotionalsAdapter = new SavedDevotionalsAdapter(devList);
        devotionalListView.setAdapter(savedDevotionalsAdapter);

        savedDevotionalsAdapter.setOnItemClickListener(new SavedDevotionalsAdapter.MyClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                Devotional devotional = devList.get(position);
                Intent intent = new Intent(getApplicationContext(), ReaderActivity.class);
                Bundle args = new Bundle();
                args.putParcelable("devotional", devotional);
                intent.putExtras(args);
                startActivity(intent);
            }
        });

        savedDevotionalsAdapter.setOnDeleteClickedListener(new SavedDevotionalsAdapter.DeleteClickedListener() {
            @Override
            public void onDeleteClicked(int position) {
                Devotional devotional = devList.get(position);
                showAlertDialog(devotional);
            }
        });

    }

    @Override
    public void notifyAdapter(int position, int size) {
        savedDevotionalsAdapter.notifyItemRemoved(position);
        savedDevotionalsAdapter.notifyItemRangeChanged(position, size);
    }

    private void showAlertDialog(final Devotional devotional) {
        // TODO: 14/04/2017 come back here and add listener
        String title = "Delete Devotional";
        String accept = "Yes";
        String reject = "Cancel";
        AlertDialog.Builder ad = new AlertDialog.Builder(this);
        ad.setTitle(title);
        ad.setMessage("Are you sure you want to delete this devotional");
        ad.setPositiveButton(accept,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        presenter.deleteDevotionals(devotional);

                    }
                });
        ad.setNegativeButton(reject,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
        final AlertDialog alert = ad.create();
        alert.show();

    }


    @Override
    public void onBackPressed() {

        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }


}
