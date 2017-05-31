package com.digzdigital.divinitytoday.data.wp;

import android.content.Context;

import com.digzdigital.divinitytoday.data.model.Devotional;

import java.util.ArrayList;

public interface WpHelper {
    void setWpListener(WpListener listener);

    void queryForOnlinePosts(int offset);

    void provideContext(Context context);


    interface WpListener {
        void onPostsLoaded(ArrayList<Devotional> devotionals);

        void onError(String error);
    }
}
