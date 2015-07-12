package it2015.sabanciuniv.edu.erdincozdemir.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import it2015.sabanciuniv.edu.erdincozdemir.R;
import it2015.sabanciuniv.edu.erdincozdemir.activities.base.BaseActivity;
import it2015.sabanciuniv.edu.erdincozdemir.objects.NewsCategory;
import it2015.sabanciuniv.edu.erdincozdemir.tasks.GetNewsCategories;
import it2015.sabanciuniv.edu.erdincozdemir.tasks.GetTokenTask;
import it2015.sabanciuniv.edu.erdincozdemir.utils.Config;

public class MainActivity extends BaseActivity implements GetNewsCategories.GetNewsCategoriesListener, AdapterView.OnItemClickListener {

    private ListView lstCategories;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lstCategories = (ListView) findViewById(R.id.lstCategories);

        GetNewsCategories categories = new GetNewsCategories(this);
        categories.setListener(this);
        categories.execute(Config.getNewsCategoriesUrl);
    }

    @Override
    public void newsCategoriesFetched(List<NewsCategory> newsCategories) {
        ArrayAdapter<NewsCategory> adapter = new ArrayAdapter<NewsCategory>(this, android.R.layout.simple_list_item_1,newsCategories);
        lstCategories.setAdapter(adapter);
        lstCategories.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //Toast.makeText(this, String.valueOf(((NewsCategory) parent.getAdapter().getItem(position)).getId()), Toast.LENGTH_SHORT).show();
        NewsCategory selectedCategory = (NewsCategory) parent.getAdapter().getItem(position);
        Intent i = new Intent(this, NewsByCategoryActivity.class);
        i.putExtra("newsCategory", selectedCategory);
        startActivity(i);
    }
}
