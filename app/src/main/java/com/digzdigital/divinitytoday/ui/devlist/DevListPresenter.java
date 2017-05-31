package com.digzdigital.divinitytoday.ui.devlist;

import android.util.Log;

import com.digzdigital.divinitytoday.DivinityTodayApp;
import com.digzdigital.divinitytoday.data.DataManager;
import com.digzdigital.divinitytoday.data.model.Devotional;

import java.util.ArrayList;

import javax.inject.Inject;

public class DevListPresenter implements DevListContract.Presenter, DataManager.WpListener {

    @Inject
    public DataManager dataManager;
    private DevListActivity view;
    private ArrayList<Devotional> devotionals = new ArrayList<>();

    public DevListPresenter() {
        DivinityTodayApp.getInstance().getAppComponent().inject(this);
    }

    @Override
    public void setView(DevListActivity view) {
        this.view = view;
        dataManager.setWpListener(this);
        dataManager.provideContext(view.getApplicationContext());
    }

    @Override
    public void loadDevotionals(int endpoint) {
        view.showProgressDialog();
        dataManager.queryForOnlinePosts(endpoint);
    }

    @Override
    public int getDevSize() {
        if (devotionals == null) return 0;
        return devotionals.size();
    }

    @Override
    public void onPostsLoaded(ArrayList<Devotional> devotionals) {
        view.dismissProgressDialog();
        this.devotionals.addAll(devotionals);
        view.doRest(devotionals);
    }

    @Override
    public void onError(String error) {
        view.dismissProgressDialog();
        view.makeToast("Couldn't load Devotionals");
        Log.d("digz", error);
    }
}
