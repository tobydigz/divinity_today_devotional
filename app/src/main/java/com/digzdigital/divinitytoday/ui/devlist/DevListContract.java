package com.digzdigital.divinitytoday.ui.devlist;

import com.digzdigital.divinitytoday.data.Devotional;

import java.util.ArrayList;


interface DevListContract {
    interface View {

        void doRest(final ArrayList<Devotional> devotionals);

        void showProgressDialog();

        void dismissProgressDialog();

        void makeToast(String message);

    }

    interface Presenter {

        void setView(DevListActivity view);

        void loadDevotionals(int endpoint);

        int getDevSize();

    }
}
