package com.digzdigital.divinitytoday.reader;

import android.widget.Toast;

import com.digzdigital.divinitytoday.model.Devotional;

import io.realm.Realm;

/**
 * Created by Digz on 26/12/2016.
 */

public class ReaderPresenter implements ReaderContract.Presenter {
    
    private ReaderActivity view;
    private Devotional devotional;

    public ReaderPresenter(){

    }

    @Override
    public void setView(ReaderActivity view, Devotional devotional) {
        this.view = view;
        this.devotional = devotional;
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

        Realm realm = view.getRealmInstance();

        realm.beginTransaction();
        Devotional realmDevotional = realm.createObject(Devotional.class);
        realmDevotional.setPostId(devotional.getPostId());
        realmDevotional.setTitle(devotional.getTitle());
        realmDevotional.setContent(devotional.getContent());
        realmDevotional.setDate(devotional.getDate());
        realmDevotional.setSaved(true);
        realm.commitTransaction();
        view.showToast("Devotional Saved");
    }

    @Override
    public boolean isSaved() {
        // TODO: 26/12/2016 turn to new db query for list of saved ids
        return devotional.isSaved();
    }
}
