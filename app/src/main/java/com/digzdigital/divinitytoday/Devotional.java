package com.digzdigital.divinitytoday;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Digz on 09/02/2016.
 * Defining getters and setters
 */
public class Devotional extends RealmObject {

    @PrimaryKey
    int _id;

    String _title;
    String _date;
    String _content;
    String _postId;

    public Devotional(){}

    public Devotional(String _postId, String title, String date, String content){
        this._postId = _postId;
        this._title = title;
        this._date = date;
        this._content = content;
    }

    public Devotional( String title, String date, String content){
        this._title = title;
        this._date = date;
        this._content = content;
    }

    public Integer getID(){
        return this._id;
    }

    public void setID(Integer id){
        this._id = id;
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

    public String getPostId(){return this._postId;}
    public void setPostId(String postId){this._postId=postId;}
}
