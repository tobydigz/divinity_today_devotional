package com.digzdigital.divinitytoday.data;

import android.os.Parcel;
import android.os.Parcelable;

import com.orm.SugarRecord;

/**
 * Created by Digz on 09/02/2016.
 * Defining getters and setters
 */
public class Devotional extends SugarRecord implements Parcelable {


    public static final Creator<Devotional> CREATOR = new Creator<Devotional>() {
        @Override
        public Devotional createFromParcel(Parcel parcel) {
            return new Devotional(parcel);
        }

        @Override
        public Devotional[] newArray(int i) {
            return new Devotional[i];
        }
    };
    private String title;
    private String date;
    private String content;
    private int postId;
    private boolean isSaved;

    public Devotional() {
    }

    public Devotional(int postId, String title, String date, String content, boolean isSaved) {
        this.postId = postId;
        this.title = title;
        this.date = date;
        this.content = content;
        this.isSaved = isSaved;
    }

    public Devotional(Parcel in) {
        this.postId = in.readInt();
        this.title = in.readString();
        this.date = in.readString();
        this.content = in.readString();
        this.isSaved = (in.readInt() != 0);
    }

    public Devotional(String title, String date, String content) {
        this.title = title;
        this.date = date;
        this.content = content;
    }

    @Override
    public int describeContents() {
        return hashCode();
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(postId);
        parcel.writeString(title);
        parcel.writeString(date);
        parcel.writeString(content);
        parcel.writeInt(isSaved ? 1 : 0);
    }

    @Override
    public boolean equals(Object object) {
        if (object instanceof Devotional) {
            Devotional toCompare = (Devotional) object;
            return (this.postId==toCompare.getPostId());
        }
        return false;
    }

    @Override
    public int hashCode() {
        return postId;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return this.date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getPostId() {
        return this.postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }


    public boolean isSaved() {
        return isSaved;
    }

    public void setSaved(boolean saved) {
        isSaved = saved;
    }
}
