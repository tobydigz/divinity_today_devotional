package com.digzdigital.divinitytoday.ui.reader;


import com.digzdigital.divinitytoday.data.DataManager;
import com.digzdigital.divinitytoday.data.model.Devotional;

import javax.inject.Inject;

public class ReaderPresenter implements ReaderContract.Presenter {

    private ReaderActivity view;
    @SuppressWarnings("WeakerAccess")
    @Inject
    public DataManager dataManager;

    public ReaderPresenter() {

    }

    @Override
    public void setView(ReaderActivity view) {
        this.view = view;
    }



    @Override
    public void saveDevotional(Devotional devotional) {

        dataManager.savePost(devotional);

        view.showToast("Devotional Saved");
    }



}
