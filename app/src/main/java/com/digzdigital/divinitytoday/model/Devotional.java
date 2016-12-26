package com.digzdigital.divinitytoday.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.v7.widget.LinearLayoutCompat;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Digz on 09/02/2016.
 * Defining getters and setters
 */
public class Devotional extends RealmObject implements Parcelable{


    private String _title;
    private String _date;
    private String _content;
    @PrimaryKey
    private int _postId;
    private boolean isSaved;

    public Devotional(){}

    public Devotional(int _postId, String title, String date, String content, boolean isSaved){
        this._postId = _postId;
        this._title = title;
        this._date = date;
        this._content = content;
        this.isSaved = isSaved;
    }

    public Devotional(Parcel in){
        this._postId = in.readInt();
        this._title = in.readString();
        this._date = in.readString();
        this._content = in.readString();
        this.isSaved = (in.readInt() != 0);
    }

    @Override
    public int describeContents(){
        return hashCode();
    }

    @Override
    public void writeToParcel(Parcel parcel, int i){
        parcel.writeInt(_postId);
        parcel.writeString(_title);
        parcel.writeString(_date);
        parcel.writeString(_content);
        parcel.writeInt(isSaved ? 1:0);
    }

    public static final Parcelable.Creator<Devotional> CREATOR = new Parcelable.Creator<Devotional>(){
        @Override
                public Devotional createFromParcel(Parcel parcel){
            return new Devotional(parcel);
        }
      @Override
        public Devotional[] newArray(int i){
          return new Devotional[i];
      }
    };

    @Override
    public boolean equals(Object object){
        if (object instanceof Devotional){
            Devotional toCompare = (Devotional) object;
            return (this._postId == toCompare.getPostId());
        }
        return false;
    }

    @Override
    public int hashCode(){
        return this.getPostId();
    }

    public Devotional( String title, String date, String content){
        this._title = title;
        this._date = date;
        this._content = content;
    }

    public String getTitle(){
        return this._title;
    }

    public void setTitle(String title){
        this._title = title;
    }

    public String getDate(){
        return this._date;
    }

    public void setDate(String date){
        this._date = date;
    }

    public String getContent(){
        return this._content;
    }

    public void setContent(String content){
        this._content = content;
    }

    public int getPostId(){
        return this._postId;
    }

    public void setPostId(int postId){
        this._postId=postId;
    }


    public boolean isSaved() {
        return isSaved;
    }

    public void setSaved(boolean saved) {
        isSaved = saved;
    }
}
