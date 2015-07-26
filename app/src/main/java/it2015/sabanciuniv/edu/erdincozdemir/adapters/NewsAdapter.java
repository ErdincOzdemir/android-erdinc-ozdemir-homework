package it2015.sabanciuniv.edu.erdincozdemir.adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.text.DateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it2015.sabanciuniv.edu.erdincozdemir.R;
import it2015.sabanciuniv.edu.erdincozdemir.objects.News;
import it2015.sabanciuniv.edu.erdincozdemir.tasks.GetImageTask;

/**
 * Created by Erdinc on 26.07.2015.
 */
public class NewsAdapter extends ArrayAdapter<News> {

    private Map<Integer, Bitmap> images;

    public NewsAdapter(Context context, List<News> objects) {
        super(context, android.R.layout.simple_list_item_1, objects);
        this.images = new HashMap<>();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        NewsHolder newsHolder = null;

        if(convertView == null) {
            convertView = ((Activity) getContext()).getLayoutInflater().inflate(R.layout.news_line, parent, false);
            newsHolder = new NewsHolder(convertView);
            convertView.setTag(newsHolder);
        }

        newsHolder = (NewsHolder) convertView.getTag();
        newsHolder.getTxtNewsTitle().setText(getItem(position).getTitle());
        DateFormat dateFormat = android.text.format.DateFormat.getDateFormat(getContext());
        newsHolder.getTxtNewsDate().setText(dateFormat.format(getItem(position).getDate()).toString());

        if(images.get(position) == null) {
            GetNewsImageTask task = new GetNewsImageTask(position, newsHolder);
            task.execute(getItem(position).getImage());
        } else {
            newsHolder.getImgNews().setImageBitmap(images.get(position));
        }

        return convertView;
    }

    class NewsHolder {

        private ImageView imgNews;
        private ProgressBar prgNews;
        private TextView txtNewsTitle, txtNewsDate;

        private View base;

        public NewsHolder(View base) {
            this.base = base;
        }

        public ImageView getImgNews() {
            if(this.imgNews == null) {
                this.imgNews = (ImageView) this.base.findViewById(R.id.imgNews);
            }
            return imgNews;
        }

        public ProgressBar getPrgNews() {
            if(this.prgNews == null) {
                this.prgNews = (ProgressBar) this.base.findViewById(R.id.prgNews);
            }
            return prgNews;
        }

        public TextView getTxtNewsTitle() {
            if(this.txtNewsTitle == null) {
                this.txtNewsTitle = (TextView) this.base.findViewById(R.id.txtNewsTitle);
            }
            return txtNewsTitle;
        }

        public TextView getTxtNewsDate() {
            if(this.txtNewsDate == null) {
                this.txtNewsDate = (TextView) this.base.findViewById(R.id.txtNewsDate);
            }
            return txtNewsDate;
        }
    }

    private class GetNewsImageTask extends GetImageTask {

        private int position;
        private NewsHolder holder;

        public GetNewsImageTask(int position, NewsHolder holder) {
            this.position = position;
            this.holder = holder;
        }

        @Override
        protected Bitmap doInBackground(String... params) {
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return super.doInBackground(params);
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            if(result != null) {
                images.put(position, result);
                holder.getImgNews().setImageBitmap(result);
                holder.getImgNews().setLayoutParams(new LinearLayout.LayoutParams(200, 200));
                holder.getImgNews().setVisibility(View.VISIBLE);
                holder.getPrgNews().setLayoutParams(new LinearLayout.LayoutParams(0, 0));
                holder.getPrgNews().setVisibility(View.INVISIBLE);
            }
        }
    }
}
