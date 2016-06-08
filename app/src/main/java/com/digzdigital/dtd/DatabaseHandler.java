package com.digzdigital.dtd;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Digz on 10/02/2016.
 */
public class DatabaseHandler extends SQLiteOpenHelper{

    //Database Version
    private static final int DATABASE_VERSION = 1;

    //Database Name
    private static final String DATABASE_NAME = "divinityDevotional";

    //Devotional Table Name
    private static final String TABLE_DEVOTIONAL = "devotional_post";

    //Devotional Table columns name
    private static final String KEY_ID = "id";
    private static final String KEY_TITLE = "title";
    private static final String KEY_DATE = "date";
    private static final String KEY_CONTENT= "content";

    public DatabaseHandler(SavedDevotionals context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public DatabaseHandler(Reader reader) {
        super(reader, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_DEVOTIONAL_TABLE = "CREATE TABLE " + TABLE_DEVOTIONAL +
                "(" + KEY_ID + " INTEGER PRIMARY KEY," + KEY_TITLE + " TEXT," +
                KEY_DATE + " TEXT," + KEY_CONTENT + " TEXT" + ")";
        db.execSQL(CREATE_DEVOTIONAL_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DEVOTIONAL);
        onCreate(db);
    }

    public void addDevotional(Devotional devotional){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_TITLE, devotional.getTitle());
        values.put(KEY_DATE, devotional.getDate());
        values.put(KEY_CONTENT, devotional.getContent());

        db.insert(TABLE_DEVOTIONAL, null, values);
        db.close();
    }

    public Devotional getDevotional(int id){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_DEVOTIONAL, new String[] {KEY_ID, KEY_TITLE,
        KEY_DATE, KEY_CONTENT}, KEY_ID + "=?",  new String[]{String.valueOf(id)},
                null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Devotional devotional = new Devotional(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), cursor.getString(2), cursor.getString(3));
        return devotional;
    }

    public List<Devotional> getAllDevotionals(){
        List<Devotional> devotionalList = new ArrayList<Devotional>();
        String selectQuery = "SELECT * FROM " + TABLE_DEVOTIONAL;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()){
            do {
                Devotional devotional = new Devotional();
                devotional.setID(Integer.parseInt(cursor.getString(0)));
                devotional.setTitle(cursor.getString(1));
                devotional.setDate(cursor.getString(2));
                devotional.setContent(cursor.getString(3));
                devotionalList.add(devotional);
            }while (cursor.moveToNext());
        }
        return devotionalList;
    }

    public int getDevotionalsCount(){
        String countQuery = "SELECT * FROM " + TABLE_DEVOTIONAL;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        return cursor.getCount();
    }

    public void deleteDevotional(Devotional devotional){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_DEVOTIONAL, KEY_ID + " = ?",
                new String[]{String.valueOf(devotional.getID())});
        db.close();
    }
}
