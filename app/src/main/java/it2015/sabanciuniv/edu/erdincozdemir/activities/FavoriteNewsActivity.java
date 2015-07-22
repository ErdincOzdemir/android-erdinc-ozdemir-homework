package it2015.sabanciuniv.edu.erdincozdemir.activities;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import it2015.sabanciuniv.edu.erdincozdemir.R;
import it2015.sabanciuniv.edu.erdincozdemir.activities.base.BaseActivity;
import it2015.sabanciuniv.edu.erdincozdemir.objects.News;
import it2015.sabanciuniv.edu.erdincozdemir.utils.FavoriteNewsDbHelper;

public class FavoriteNewsActivity extends BaseActivity implements AdapterView.OnItemClickListener {

    private ListView lstFavoriteNews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_favorite_news);
        super.onCreate(savedInstanceState);

        lstFavoriteNews = (ListView) findViewById(R.id.lstFavoriteNews);

        FavoriteNewsDbHelper dbHelper = new FavoriteNewsDbHelper(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.query(FavoriteNewsDbHelper.TABLE_FAVORITE_NEWS, new String[]{FavoriteNewsDbHelper.COLUMN_ID,
                FavoriteNewsDbHelper.COLUMN_TITLE}, null, null, null, null, null, null);

        SimpleCursorAdapter adp = new SimpleCursorAdapter(this, android.R.layout.simple_expandable_list_item_1, cursor, new String[]{FavoriteNewsDbHelper.COLUMN_TITLE},
                new int[]{android.R.id.text1});

        lstFavoriteNews.setAdapter(adp);
        lstFavoriteNews.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        FavoriteNewsDbHelper dbHelper = new FavoriteNewsDbHelper(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();



        Cursor cursor = db.query(FavoriteNewsDbHelper.TABLE_FAVORITE_NEWS, new String[]{FavoriteNewsDbHelper.COLUMN_ID,
                        FavoriteNewsDbHelper.COLUMN_TITLE, FavoriteNewsDbHelper.COLUMN_TEXT, FavoriteNewsDbHelper.COLUMN_DATE,
                        FavoriteNewsDbHelper.COLUMN_IMAGE, FavoriteNewsDbHelper.COLUMN_CATEGORY_NAME}, null, null, null, null,
                null, null);


        News n = new News();

        try {
            cursor.moveToFirst();
            while(!cursor.isAfterLast()){
                n.setId(cursor.getInt(0));
                n.setTitle(cursor.getString(1));
                n.setText(cursor.getString(2));
                Log.i("DEVELOPER", cursor.getString(3));

                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date date = null;
                try {
                    date = format.parse(cursor.getString(3));
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                n.setDate(date);
                n.setImage(cursor.getString(4));
                n.setCategoryName(cursor.getString(5));
                cursor.moveToNext();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        Intent intent = new Intent(this, NewsDetailActivity.class);
        intent.putExtra("news", n);
        startActivity(intent);
        finish();
    }
}
