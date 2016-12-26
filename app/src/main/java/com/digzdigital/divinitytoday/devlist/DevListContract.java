package com.digzdigital.divinitytoday.devlist;

import android.content.Context;

import com.digzdigital.divinitytoday.model.Devotional;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by Digz on 26/12/2016.
 *
 */

public interface DevListContract {
    interface View{

       void doRest(final ArrayList<Devotional> devotionals);
        void addToList(final ArrayList<Devotional> devotionals);
        void showProgressDialog();
        void dismissProgressDialog();
        Context provideContext();
        void makeToast(String message);

    }

    interface Presenter{

        void setView(DevListActivity view);
        ArrayList<Devotional> getDevotional(int endpoint);
        String sendRequest(int endpoint);
        void SetupRecycler(boolean loadMore, int endpoint);
        int getDevSize();

    }
}
