package com.digzdigital.divinitytoday.ui.saveddevlist;

import com.digzdigital.divinitytoday.DivinityTodayApp;
import com.digzdigital.divinitytoday.data.DataManager;
import com.digzdigital.divinitytoday.data.model.Devotional;

import java.util.ArrayList;

import javax.inject.Inject;

public class SavedDevotionalsPresenter implements SavedDevotionalsContract.Presenter{

    @SuppressWarnings("WeakerAccess")
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
        // view.notifyAdapter(position, size);
    }


}
