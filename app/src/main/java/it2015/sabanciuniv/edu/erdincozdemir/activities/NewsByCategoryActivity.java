package it2015.sabanciuniv.edu.erdincozdemir.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

import it2015.sabanciuniv.edu.erdincozdemir.NewsDetailActivity;
import it2015.sabanciuniv.edu.erdincozdemir.R;
import it2015.sabanciuniv.edu.erdincozdemir.activities.base.BaseActivity;
import it2015.sabanciuniv.edu.erdincozdemir.objects.News;
import it2015.sabanciuniv.edu.erdincozdemir.objects.NewsCategory;
import it2015.sabanciuniv.edu.erdincozdemir.tasks.GetNewsByCategoryTask;
import it2015.sabanciuniv.edu.erdincozdemir.utils.Config;

public class NewsByCategoryActivity extends BaseActivity implements GetNewsByCategoryTask.GetNewsByCategoryListener, AdapterView.OnItemClickListener {

    private ListView lstNewsByCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_by_category);

        lstNewsByCategory = (ListView) findViewById(R.id.lstNewsByCategory);

        NewsCategory selectedCategory = (NewsCategory) getIntent().getSerializableExtra("newsCategory");
        if(selectedCategory != null) {
            GetNewsByCategoryTask getNewsByCategory = new GetNewsByCategoryTask(this, selectedCategory);
            getNewsByCategory.setListener(this);
            getNewsByCategory.execute(Config.getAllNewsByCategoryUrl);
        }

    }

    @Override
    public void newsFetched(List<News> news) {
        ArrayAdapter<News> adapter = new ArrayAdapter<News>(this, android.R.layout.simple_list_item_1, news);
        lstNewsByCategory.setAdapter(adapter);
        lstNewsByCategory.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        News selectedNews = (News) parent.getAdapter().getItem(position);
        Intent i = new Intent(this, NewsDetailActivity.class);
        i.putExtra("news", selectedNews);
        startActivity(i);
    }
}
