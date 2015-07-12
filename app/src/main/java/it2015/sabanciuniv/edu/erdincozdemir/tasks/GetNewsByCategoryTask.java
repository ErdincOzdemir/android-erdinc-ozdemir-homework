package it2015.sabanciuniv.edu.erdincozdemir.tasks;

import android.app.ProgressDialog;
import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import it2015.sabanciuniv.edu.erdincozdemir.objects.News;
import it2015.sabanciuniv.edu.erdincozdemir.objects.NewsCategory;
import it2015.sabanciuniv.edu.erdincozdemir.objects.ServiceResponseObject;
import it2015.sabanciuniv.edu.erdincozdemir.tasks.base.BaseGetDataTask;
import it2015.sabanciuniv.edu.erdincozdemir.utils.Config;
import it2015.sabanciuniv.edu.erdincozdemir.utils.SharedPreferencesHelper;
import it2015.sabanciuniv.edu.erdincozdemir.utils.Utilities;

/**
 * Created by Erdinc on 12.07.2015.
 */
public class GetNewsByCategoryTask extends BaseGetDataTask implements GetTokenTask.GetTokenListener {

    public interface GetNewsByCategoryListener {
        void newsFetched(List<News> news);
    }

    private ProgressDialog dialog;
    private Context context;
    private SharedPreferencesHelper sharedPreferencesHelper;
    private GetNewsByCategoryListener listener;
    private NewsCategory newsCategory;

    public GetNewsByCategoryTask(Context context, NewsCategory newsCategory) {
        this.context = context;
        this.newsCategory = newsCategory;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        sharedPreferencesHelper = new SharedPreferencesHelper(this.context);
        if(sharedPreferencesHelper.getToken() == null) {
            GetTokenTask getTokenTask = new GetTokenTask();
            getTokenTask.setListener(this);
            getTokenTask.execute(Config.loginUrl);
        } else {
            dialog = new ProgressDialog(this.context);
            dialog.setTitle("Loading");
            dialog.setMessage("Please wait");
            dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            dialog.show();
        }
    }

    @Override
    protected String doInBackground(String... params) {
        params[0] = params[0] + newsCategory.getId() + "?token=" + sharedPreferencesHelper.getToken();
        return super.doInBackground(params);
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

        if(dialog != null) {
            dialog.dismiss();
        }

        ServiceResponseObject<News> newsResponseObject = new ServiceResponseObject<>();
        try {
            JSONObject jsonObject = new JSONObject(s);
            newsResponseObject.setServiceMessageCode(jsonObject.getInt("serviceMessageCode"));
            newsResponseObject.setServiceMessageText(jsonObject.getString("serviceMessageText"));
            JSONArray arr = jsonObject.getJSONArray("items");
            List<News> newsList = new ArrayList<>();
            for (int i = 0; i < arr.length(); i++) {
                JSONObject obj = arr.getJSONObject(i);
                News news = new News(obj.getInt("id"), obj.getString("title"), obj.getString("text"), Utilities.getDate(obj.getLong("date")),
                        obj.getString("image"), obj.getString("categoryName"));
                newsList.add(news);

            }
            newsResponseObject.setResponseItems(newsList);
            if(newsResponseObject.getServiceMessageCode() == 1) {
                this.listener.newsFetched(newsResponseObject.getResponseItems());
            } else {
                GetTokenTask task = new GetTokenTask();
                task.setListener(this);
                task.execute(Config.loginUrl);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    @Override
    public void tokenFetched(String token) {
        sharedPreferencesHelper.setToken(token);
        GetNewsByCategoryTask getNewsByCategory = new GetNewsByCategoryTask(this.context, this.newsCategory);
        getNewsByCategory.setListener(this.listener);
        getNewsByCategory.execute(Config.getNewsCategoriesUrl);
    }

    public void setListener(GetNewsByCategoryListener listener) {
        this.listener = listener;
    }
}
