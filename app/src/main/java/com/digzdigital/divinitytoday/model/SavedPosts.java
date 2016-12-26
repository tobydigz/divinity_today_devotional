package com.digzdigital.divinitytoday.model;

import io.realm.RealmObject;

/**
 * Created by Digz on 26/12/2016.
 */

public class SavedPosts extends RealmObject {
    private long id;

    public SavedPosts() {

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
