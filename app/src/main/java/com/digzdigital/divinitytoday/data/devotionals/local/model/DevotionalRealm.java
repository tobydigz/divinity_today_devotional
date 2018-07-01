package com.digzdigital.divinitytoday.data.devotionals.local.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class DevotionalRealm extends RealmObject {
    @PrimaryKey
    private String id;
    private String title;
    private Long date;
    private String content;
    private String excerpt;
    private Boolean isBookmarked;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getDate() {
        return date;
    }

    public void setDate(Long date) {
        this.date = date;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getExcerpt() {
        return excerpt;
    }

    public void setExcerpt(String excerpt) {
        this.excerpt = excerpt;
    }

    public Boolean getBookmarked() {
        return isBookmarked;
    }

    public void setBookmarked(Boolean bookmarked) {
        isBookmarked = bookmarked;
    }
}
