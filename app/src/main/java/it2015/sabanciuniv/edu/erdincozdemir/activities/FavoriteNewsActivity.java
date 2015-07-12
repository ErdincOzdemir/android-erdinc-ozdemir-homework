package it2015.sabanciuniv.edu.erdincozdemir.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import it2015.sabanciuniv.edu.erdincozdemir.R;
import it2015.sabanciuniv.edu.erdincozdemir.activities.base.BaseActivity;

public class FavoriteNewsActivity extends BaseActivity {

    private ListView lstFavoriteNews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_favorite_news);
        super.onCreate(savedInstanceState);

        lstFavoriteNews = (ListView) findViewById(R.id.lstFavoriteNews);
    }
}
