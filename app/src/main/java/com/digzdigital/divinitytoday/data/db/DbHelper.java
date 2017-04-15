package com.digzdigital.divinitytoday.data.db;

import com.digzdigital.divinitytoday.data.model.Devotional;

import java.util.ArrayList;


public interface DbHelper {
    void setDbListener(DbListener listener);

    void savePost(Devotional devotional);

    void queryForPosts();

    void deletePost(Devotional devotional);

    interface DbListener {
        void onPostsLoaded(ArrayList<Devotional> devotionals);

        void onError(String error);
    }
}
