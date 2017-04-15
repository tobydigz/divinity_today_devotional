package com.digzdigital.divinitytoday.ui.saveddevlist;

import com.digzdigital.divinitytoday.data.model.Devotional;

import java.util.ArrayList;

/**
 * Created by Digz on 14/04/2017.
 */

public interface SavedDevotionalsContract {
    interface View {

        void doRest(final ArrayList<Devotional> devotionals);

        void notifyAdapter(int position, int size);

    }

    interface Presenter {

        void setView(SavedDevotionalsActivity view);

        void loadDevotionals();

        int getDevSize();

        void deleteDevotionals(Devotional devotional);

    }
}
