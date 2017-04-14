package com.digzdigital.divinitytoday.ui.reader;

import io.realm.Realm;

/**
 * Created by Digz on 26/12/2016.
 */

public interface ReaderContract {
    interface View {

        void displayTitle(String title);

        void displayDate(String date);

        void displayContent(String content);

        void showToast(String message);

    }

    interface Presenter {

        void updateDb();

        boolean isSaved();

        void setView(ReaderActivity view);

        void loadDevotional();
    }
}
