package it2015.sabanciuniv.edu.erdincozdemir.activities;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.util.List;

import it2015.sabanciuniv.edu.erdincozdemir.R;
import it2015.sabanciuniv.edu.erdincozdemir.activities.base.BaseActivity;
import it2015.sabanciuniv.edu.erdincozdemir.adapters.CommentsAdapter;
import it2015.sabanciuniv.edu.erdincozdemir.fragments.PostCommentFragment;
import it2015.sabanciuniv.edu.erdincozdemir.objects.Comment;
import it2015.sabanciuniv.edu.erdincozdemir.objects.News;
import it2015.sabanciuniv.edu.erdincozdemir.tasks.GetCommentsTask;
import it2015.sabanciuniv.edu.erdincozdemir.tasks.GetImageTask;
import it2015.sabanciuniv.edu.erdincozdemir.tasks.PostCommentTask;
import it2015.sabanciuniv.edu.erdincozdemir.utils.Config;

public class NewsDetailActivity extends BaseActivity implements GetImageTask.GetImageTaskListener, GetCommentsTask.GetCommentsTaskListener, View.OnClickListener, PostCommentTask.PostCommentTaskListener {

    private ImageView imgNews;
    private TextView txtNewsDate, txtNewsCategory, txtNewsDetail;
    private ListView lstNewsComments;
    private Button btnPostComment, btnAddToFavorites;
    private News news;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_news_detail);
        super.onCreate(savedInstanceState);

        imgNews = (ImageView) findViewById(R.id.imgNews);
        txtNewsDate = (TextView) findViewById(R.id.txtNewsDate);
        txtNewsCategory = (TextView) findViewById(R.id.txtNewsCategory);
        txtNewsDetail = (TextView) findViewById(R.id.txtNewsDetail);
        lstNewsComments = (ListView) findViewById(R.id.lstNewsComments);
        btnPostComment = (Button) findViewById(R.id.btnPostComment);
        btnPostComment.setOnClickListener(this);
        btnAddToFavorites = (Button) findViewById(R.id.btnAddToFavorites);
        btnAddToFavorites.setOnClickListener(this);

        news = (News) getIntent().getSerializableExtra("news");
        if(news != null) {
            DateFormat dateFormat = android.text.format.DateFormat.getDateFormat(this);
            txtNewsDate.setText(dateFormat.format(news.getDate()).toString());
            txtNewsCategory.setText(news.getCategoryName());
            txtNewsDetail.setText(news.getText());

            GetImageTask getImageTask = new GetImageTask();
            getImageTask.setListener(this);
            getImageTask.execute(news.getImage());

            GetCommentsTask getCommentsTask = new GetCommentsTask(this, news);
            getCommentsTask.setListener(this);
            getCommentsTask.execute(Config.getCommentsByNewsIdUrl);

        }

    }

    @Override
    public void imageFetched(Bitmap image) {
        imgNews.setImageBitmap(image);
    }

    @Override
    public void commentsFetched(List<Comment> comments) {
        CommentsAdapter adapter = new CommentsAdapter(this, comments);
        lstNewsComments.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btnPostComment) {
            PostCommentFragment postCommentFragment = new PostCommentFragment();
            Bundle bundle = new Bundle();
            bundle.putSerializable("news", this.news);
            postCommentFragment.setArguments(bundle);
            postCommentFragment.setListener(this);
            postCommentFragment.show(getFragmentManager(), null);
        } else if(v.getId() == R.id.btnAddToFavorites) {

        }
    }

    @Override
    public void commentPostedSuccessfully() {
        GetCommentsTask getCommentsTask = new GetCommentsTask(this, this.news);
        getCommentsTask.setListener(this);
        getCommentsTask.execute(Config.getCommentsByNewsIdUrl);
    }

    @Override
    public void commentPostFailed() {
        Toast.makeText(getApplicationContext(), getString(R.string.app_name), Toast.LENGTH_SHORT).show();
    }
}
