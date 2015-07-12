package it2015.sabanciuniv.edu.erdincozdemir.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import it2015.sabanciuniv.edu.erdincozdemir.R;
import it2015.sabanciuniv.edu.erdincozdemir.activities.base.BaseActivity;

public class NewsByCategoryActivity extends BaseActivity {

    private ListView lstNewsByCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_by_category);
        overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);

        lstNewsByCategory = (ListView) findViewById(R.id.lstNewsByCategory);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.trans_right_in, R.anim.trans_right_out);
    }
}
