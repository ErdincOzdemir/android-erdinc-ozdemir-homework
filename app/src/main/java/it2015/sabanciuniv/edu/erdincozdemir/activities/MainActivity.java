package it2015.sabanciuniv.edu.erdincozdemir.activities;

import android.os.Bundle;

import java.util.List;

import it2015.sabanciuniv.edu.erdincozdemir.R;
import it2015.sabanciuniv.edu.erdincozdemir.activities.base.BaseActivity;
import it2015.sabanciuniv.edu.erdincozdemir.objects.NewsCategory;
import it2015.sabanciuniv.edu.erdincozdemir.tasks.GetNewsCategories;
import it2015.sabanciuniv.edu.erdincozdemir.tasks.GetTokenTask;
import it2015.sabanciuniv.edu.erdincozdemir.utils.Config;

public class MainActivity extends BaseActivity implements GetNewsCategories.GetNewsCategoriesListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        GetNewsCategories categories = new GetNewsCategories(this);
        categories.setListener(this);
    }

    @Override
    public void newsCategoriesFetched(List<NewsCategory> newsCategories) {

    }
}
