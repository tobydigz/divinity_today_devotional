package com.digzdigital.divinitytoday.ui.reader;


import com.digzdigital.divinitytoday.data.model.Devotional;

public interface ReaderContract {
    interface View {

        void displayTitle(String title);

        void displayDate(String date);

        void displayContent(String content);

        void showToast(String message);

    }

    interface Presenter {

        void saveDevotional(Devotional devotional);

        void setView(ReaderActivity view);

    }
}
