package it2015.sabanciuniv.edu.erdincozdemir.activities;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
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
    private News news;
    private Menu menu;
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
        /*btnPostComment = (Button) findViewById(R.id.btnPostComment);
        btnPostComment.setOnClickListener(this);
        btnAddToFavorites = (Button) findViewById(R.id.btnAddToFavorites);
        btnAddToFavorites.setOnClickListener(this);*/

    }

    @Override
    protected void onStart() {
        super.onStart();

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

            setTitle(news.getTitle());


            isFavorited();
        }
    }

    private void isFavorited() {

        FavoriteNewsDbHelper dbHelper = new FavoriteNewsDbHelper(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.query(FavoriteNewsDbHelper.TABLE_FAVORITE_NEWS, new String[]{FavoriteNewsDbHelper.COLUMN_ID}, FavoriteNewsDbHelper.COLUMN_ID + "=?", new String[]{String.valueOf(news.getId())}, null, null, null, null);

        this.favorited = cursor.getCount() > 0;
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
        /*if(v.getId() == R.id.btnPostComment) {

        } else if(v.getId() == R.id.btnAddToFavorites) {

        }*/
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


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_news_detail, menu);
        this.menu = menu;
        if(this.favorited) {
            this.menu.findItem(R.id.action_add_to_favorites).setIcon(android.R.drawable.btn_star_big_on);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_add_to_favorites) {
            addToFavorites(item);
        } else if(id == R.id.action_comment) {
            comment();
        }

        return super.onOptionsItemSelected(item);
    }

    private void comment() {
        PostCommentFragment postCommentFragment = PostCommentFragment.newInstance(this.news);
        postCommentFragment.setListener(this);
        postCommentFragment.show(getFragmentManager(), null);
    }

    private void addToFavorites(MenuItem item) {
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
            item.setTitle(getString(R.string.remove_from_favorites_text));
            item.setIcon(android.R.drawable.btn_star_big_on);
            this.favorited = true;
            Toast.makeText(getApplicationContext(), getString(R.string.add_to_favorites_result_message), Toast.LENGTH_SHORT).show();
        } else {
            db.delete(FavoriteNewsDbHelper.TABLE_FAVORITE_NEWS, FavoriteNewsDbHelper.COLUMN_ID + "=" + this.news.getId(), null);
            item.setTitle(getString(R.string.add_to_favorites_text));
            item.setIcon(android.R.drawable.btn_star_big_off);
            this.favorited = false;
            Toast.makeText(getApplicationContext(), getString(R.string.remove_from_favorites_result_message), Toast.LENGTH_SHORT).show();
        }
    }
}
