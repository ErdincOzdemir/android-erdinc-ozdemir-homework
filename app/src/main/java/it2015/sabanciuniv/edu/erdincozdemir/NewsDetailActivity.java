package it2015.sabanciuniv.edu.erdincozdemir;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.text.DateFormat;

import it2015.sabanciuniv.edu.erdincozdemir.activities.base.BaseActivity;
import it2015.sabanciuniv.edu.erdincozdemir.objects.News;
import it2015.sabanciuniv.edu.erdincozdemir.tasks.GetImageTask;

public class NewsDetailActivity extends BaseActivity implements GetImageTask.GetImageTaskListener {

    private ImageView imgNews;
    private TextView txtNewsDate, txtNewsCategory, txtNewsDetail;
    private ListView lstNewsComments;
    private Button btnPostComment, btnAddToFavorites;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);

        imgNews = (ImageView) findViewById(R.id.imgNews);
        txtNewsDate = (TextView) findViewById(R.id.txtNewsDate);
        txtNewsCategory = (TextView) findViewById(R.id.txtNewsCategory);
        txtNewsDetail = (TextView) findViewById(R.id.txtNewsDetail);
        lstNewsComments = (ListView) findViewById(R.id.lstNewsComments);
        btnPostComment = (Button) findViewById(R.id.btnPostComment);
        btnAddToFavorites = (Button) findViewById(R.id.btnAddToFavorites);

        News news = (News) getIntent().getSerializableExtra("news");
        if(news != null) {
            DateFormat dateFormat = android.text.format.DateFormat.getDateFormat(this);
            txtNewsDate.setText(dateFormat.format(news.getDate()).toString());
            txtNewsCategory.setText(news.getCategoryName());
            txtNewsDetail.setText(news.getText());

            GetImageTask getImageTask = new GetImageTask();
            getImageTask.setListener(this);
            getImageTask.execute(news.getImage());

        }

    }

    @Override
    public void imageFetched(Bitmap image) {
        imgNews.setImageBitmap(image);
    }
}
