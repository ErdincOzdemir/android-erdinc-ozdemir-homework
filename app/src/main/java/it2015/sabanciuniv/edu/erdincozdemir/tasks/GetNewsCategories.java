package it2015.sabanciuniv.edu.erdincozdemir.tasks;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import it2015.sabanciuniv.edu.erdincozdemir.objects.NewsCategory;
import it2015.sabanciuniv.edu.erdincozdemir.objects.ServiceResponseObject;
import it2015.sabanciuniv.edu.erdincozdemir.tasks.base.BaseGetDataTask;
import it2015.sabanciuniv.edu.erdincozdemir.utils.Config;
import it2015.sabanciuniv.edu.erdincozdemir.utils.SharedPreferencesHelper;

/**
 * Created by Erdinc on 12.07.2015.
 */
public class GetNewsCategories extends BaseGetDataTask implements GetTokenTask.GetTokenListener {

    public interface GetNewsCategoriesListener {
        void newsCategoriesFetched(List<NewsCategory> newsCategories);
    }

    private ProgressDialog dialog;
    private Context context;
    private SharedPreferencesHelper sharedPreferencesHelper;
    private GetNewsCategoriesListener listener;

    public GetNewsCategories(Context context) {
        this.context = context;
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
        params[0] = params[0] + sharedPreferencesHelper.getToken();

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return super.doInBackground(params);
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

        if(dialog != null) {
            dialog.dismiss();
        }

        ServiceResponseObject<NewsCategory> newsCategoryServiceResponseObject = new ServiceResponseObject<>();
        try {
            JSONObject jsonObject = new JSONObject(s);
            newsCategoryServiceResponseObject.setServiceMessageCode(jsonObject.getInt("serviceMessageCode"));
            newsCategoryServiceResponseObject.setServiceMessageText(jsonObject.getString("serviceMessageText"));
            JSONArray arr = jsonObject.getJSONArray("items");
            List<NewsCategory> newsCategories = new ArrayList<>();
            for (int i = 0; i < arr.length(); i++) {
                JSONObject obj = arr.getJSONObject(i);
                NewsCategory category = new NewsCategory(obj.getInt("id"), obj.getString("name"));
                newsCategories.add(category);
            }
            newsCategoryServiceResponseObject.setResponseItems(newsCategories);
            if(newsCategoryServiceResponseObject.getServiceMessageCode() == 1) {
                this.listener.newsCategoriesFetched(newsCategoryServiceResponseObject.getResponseItems());
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
        GetNewsCategories getNewsCategories = new GetNewsCategories(this.context);
        getNewsCategories.setListener(this.listener);
        getNewsCategories.execute(Config.getNewsCategoriesUrl);
    }

    public void setListener(GetNewsCategoriesListener listener) {
        this.listener = listener;
    }
}
