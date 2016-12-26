package com.digzdigital.divinitytoday.reader;

import com.digzdigital.divinitytoday.model.Devotional;

import io.realm.Realm;

/**
 * Created by Digz on 26/12/2016.
 */

public interface ReaderContract {
    interface View{

        void displayTitle(String title);
        void displayDate(String date);
        void displayContent(String content);
        void showToast(String message);
        Realm getRealmInstance();
    }

    interface Presenter{

        void updateDb();
        boolean isSaved();
        void setView(ReaderActivity view, Devotional devotional);
        void loadDevotional();
    }
}
