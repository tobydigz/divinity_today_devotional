package com.digzdigital.divinitytoday.data;

import com.digzdigital.divinitytoday.DivinityTodayApp;
import com.digzdigital.divinitytoday.data.db.DbHelper;
import com.digzdigital.divinitytoday.data.wp.WpHelper;

import javax.inject.Inject;


public class AppDataManager implements DataManager {
    @Inject
    public WpHelper wpHelper;
    @Inject
    public DbHelper dbHelper;

    public AppDataManager() {
        DivinityTodayApp.getInstance().getAppComponent().inject(this);

    }

    @Override
    public void setWpListener(WpListener listener) {
        wpHelper.setWpListener(listener);
    }

    @Override
    public void queryForOnlinePosts(int offset) {
        wpHelper.queryForOnlinePosts(offset);
    }

    @Override
    public void setDbListener(DbListener listener) {
        dbHelper.setDbListener(listener);
    }

    @Override
    public void savePost(Devotional devotional) {
        dbHelper.savePost(devotional);
    }

    @Override
    public void queryForPosts() {
        dbHelper.queryForPosts();
    }

    @Override
    public void deletePost(Devotional devotional) {
        dbHelper.deletePost(devotional);
    }
}
