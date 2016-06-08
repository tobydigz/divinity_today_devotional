package com.digzdigital.dtd;

import io.realm.RealmObject;

/**
 * Created by Digz on 09/02/2016.
 * Defining getters and setters
 */
public class Devotional extends RealmObject {

    int _id;
    String _title;
    String _date;
    String _content;

    public Devotional(){}

    public Devotional(int id, String title, String date, String content){
        this._id = id;
        this._title = title;
        this._date = date;
        this._content = content;
    }

    public Devotional( String title, String date, String content){
        this._title = title;
        this._date = date;
        this._content = content;
    }

    public int getID(){
        return this._id;
    }

    public void setID(int id){
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
}
