package com.digzdigital.divinitytoday.ui.reader;


import com.digzdigital.divinitytoday.data.Devotional;

public class ReaderPresenter implements ReaderContract.Presenter {

    private ReaderActivity view;
    private Devotional devotional;

    public ReaderPresenter() {

    }

    @Override
    public void setView(ReaderActivity view) {
        this.view = view;
    }

    @Override
    public void loadDevotional() {
        if (view == null) return;
        view.displayTitle(devotional.getTitle());
        view.displayDate(devotional.getDate());
        view.displayContent(devotional.getContent());
    }

    @Override
    public void updateDb() {


        view.showToast("Devotional Saved");
    }

    @Override
    public boolean isSaved() {
        // TODO: 26/12/2016 turn to new db query for list of saved ids
        return devotional.isSaved();
    }

}
