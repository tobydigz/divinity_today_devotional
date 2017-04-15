package com.digzdigital.divinitytoday.data.wp;

import com.digzdigital.divinitytoday.data.model.Devotional;

import java.util.ArrayList;

public interface WpHelper {
    void setWpListener(WpListener listener);

    void queryForOnlinePosts(int offset);


    interface WpListener {
        void onPostsLoaded(ArrayList<Devotional> devotionals);

        void onError(String error);
    }
}
