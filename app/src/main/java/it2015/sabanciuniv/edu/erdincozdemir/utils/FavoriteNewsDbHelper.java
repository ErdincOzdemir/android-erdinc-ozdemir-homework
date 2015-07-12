package it2015.sabanciuniv.edu.erdincozdemir.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Erdinc on 12.07.2015.
 */
public class FavoriteNewsDbHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "favorites.db";
    public static final int DATABASE_VERSION = 1;
    public static final String TABLE_FAVORITE_NEWS = "favoritenews";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_TEXT = "text";
    public static final String COLUMN_DATE = "date";
    public static final String COLUMN_IMAGE = "image";
    public static final String COLUMN_CATEGORY_NAME = "categoryname";

    public static final String CREATE_QUERY = "create table "+ TABLE_FAVORITE_NEWS +
            " (" + COLUMN_ID +" integer primary key, " +
            COLUMN_TITLE + " text not null, "+
            COLUMN_TEXT + " text not null, " +
            COLUMN_DATE + " datetime DEFAULT CURRENT_TIMESTAMP," +
            COLUMN_IMAGE + " text not null," +
            COLUMN_CATEGORY_NAME + " text not null)";


    public FavoriteNewsDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_QUERY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + TABLE_FAVORITE_NEWS);
        onCreate(db);
    }
}
