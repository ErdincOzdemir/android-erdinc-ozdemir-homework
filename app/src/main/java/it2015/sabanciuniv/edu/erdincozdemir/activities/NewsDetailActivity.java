package it2015.sabanciuniv.edu.erdincozdemir.activities;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import it2015.sabanciuniv.edu.erdincozdemir.utils.FavoriteNewsDbHelper;

public class NewsDetailActivity extends BaseActivity implements GetImageTask.GetImageTaskListener, GetCommentsTask.GetCommentsTaskListener, View.OnClickListener, PostCommentTask.PostCommentTaskListener {

    private ImageView imgNews;
    private TextView txtNewsDate, txtNewsCategory, txtNewsDetail;
    private ListView lstNewsComments;
    private Button btnPostComment, btnAddToFavorites;
    private News news;
    private boolean favorited = false;


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

            isFavorited();
        }

    }

    private void isFavorited() {

        FavoriteNewsDbHelper dbHelper = new FavoriteNewsDbHelper(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.query(FavoriteNewsDbHelper.TABLE_FAVORITE_NEWS, new String[]{FavoriteNewsDbHelper.COLUMN_ID}, null, null, null, null, null, null);

        this.favorited = cursor.getCount() > 0;

        if(this.favorited) {
            btnAddToFavorites.setText(getString(R.string.remove_from_favorites_text));
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
        super.onClick(v);
        if(v.getId() == R.id.btnPostComment) {
            PostCommentFragment postCommentFragment = new PostCommentFragment();
            Bundle bundle = new Bundle();
            bundle.putSerializable("news", this.news);
            postCommentFragment.setArguments(bundle);
            postCommentFragment.setListener(this);
            postCommentFragment.show(getFragmentManager(), null);
        } else if(v.getId() == R.id.btnAddToFavorites) {
            FavoriteNewsDbHelper dbHelper = new FavoriteNewsDbHelper(this);
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            if(!this.favorited) {
                ContentValues favoriteNews = new ContentValues();
                favoriteNews.put(FavoriteNewsDbHelper.COLUMN_ID, this.news.getId());
                favoriteNews.put(FavoriteNewsDbHelper.COLUMN_TITLE, this.news.getTitle());
                favoriteNews.put(FavoriteNewsDbHelper.COLUMN_TEXT, this.news.getText());
                DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                String date = df.format(this.news.getDate().getTime());
                favoriteNews.put(FavoriteNewsDbHelper.COLUMN_DATE, date);
                favoriteNews.put(FavoriteNewsDbHelper.COLUMN_IMAGE, this.news.getImage());
                favoriteNews.put(FavoriteNewsDbHelper.COLUMN_CATEGORY_NAME, this.news.getCategoryName());
                db.insert(FavoriteNewsDbHelper.TABLE_FAVORITE_NEWS, null, favoriteNews);
                btnAddToFavorites.setText(getString(R.string.remove_from_favorites_text));
                this.favorited = true;
                Toast.makeText(getApplicationContext(), getString(R.string.add_to_favorites_result_message), Toast.LENGTH_SHORT).show();
            } else {
                db.delete(FavoriteNewsDbHelper.TABLE_FAVORITE_NEWS, FavoriteNewsDbHelper.COLUMN_ID + "=" + this.news.getId(), null);
                btnAddToFavorites.setText(getString(R.string.add_to_favorites_text));
                this.favorited = false;
                Toast.makeText(getApplicationContext(), getString(R.string.remove_from_favorites_result_message), Toast.LENGTH_SHORT).show();
            }
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
        Toast.makeText(getApplicationContext(), getString(R.string.post_comment_error), Toast.LENGTH_SHORT).show();
    }
}
