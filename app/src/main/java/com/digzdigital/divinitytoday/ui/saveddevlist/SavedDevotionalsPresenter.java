package com.digzdigital.divinitytoday.ui.saveddevlist;

import com.digzdigital.divinitytoday.DivinityTodayApp;
import com.digzdigital.divinitytoday.data.DataManager;
import com.digzdigital.divinitytoday.data.model.Devotional;

import java.util.ArrayList;

import javax.inject.Inject;

/**
 * Created by Digz on 14/04/2017.
 */

public class SavedDevotionalsPresenter implements SavedDevotionalsContract.Presenter, DataManager.DbListener {
    @Inject
    public DataManager dataManager;
    private ArrayList<Devotional> devotionals = new ArrayList<>();
    private SavedDevotionalsActivity view;

    public SavedDevotionalsPresenter() {
        DivinityTodayApp.getInstance().getAppComponent().inject(this);
    }

    @Override
    public void setView(SavedDevotionalsActivity view) {
        this.view = view;
    }

    @Override
    public void loadDevotionals() {
        dataManager.queryForPosts();
    }

    @Override
    public int getDevSize() {
        return devotionals.size();
    }

    @Override
    public void deleteDevotionals(Devotional devotional) {
        int position = devotionals.indexOf(devotional);
        devotional.delete();
        devotionals.remove(devotional);
        int size = getDevSize();
        view.notifyAdapter(position, size);
    }

    @Override
    public void onPostsLoaded(ArrayList<Devotional> devotionals) {
        this.devotionals = devotionals;
        view.doRest(devotionals);
    }

    @Override
    public void onError(String error) {

    }
}
