package com.digzdigital.divinitytoday.data.db;

import com.digzdigital.divinitytoday.data.Devotional;

import java.util.ArrayList;
import java.util.List;


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
        List<Devotional> savedDevotionals = Devotional.find(Devotional.class, "postId = ?", String.valueOf(devotional.getPostId()));
        if (savedDevotionals.size() > 0) {
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
        devotional.delete();
    }
}
