package com.digzdigital.divinitytoday.data.db;

import com.digzdigital.divinitytoday.data.model.Devotional;

import java.util.ArrayList;


public class AppDbHelper implements DbHelper {

    private DbListener listener;

    public AppDbHelper() {
    }

    @Override
    public void setDbListener(DbListener listener) {
        this.listener = listener;
    }

    @Override
    public void savePost(Devotional devotional) {
        Devotional sugarDevotional = Devotional.findById(Devotional.class, devotional.getId());
        if (sugarDevotional != null) {
            listener.onError("OnlineDevotional already saved");
            return;
        }
        devotional.save();
    }

    @Override
    public void queryForPosts() {
        ArrayList<Devotional> devotionals = new ArrayList<>(Devotional.listAll(Devotional.class));
        listener.onPostsLoaded(devotionals);

    }

    @Override
    public void deletePost(Devotional devotional) {
        Devotional sugarDevotional = Devotional.findById(Devotional.class, devotional.getId());
        sugarDevotional.delete();
    }
}
