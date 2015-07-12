package it2015.sabanciuniv.edu.erdincozdemir.tasks;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

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
        return super.doInBackground(params);
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

        if(dialog != null) {
            dialog.dismiss();
        }

    }

    @Override
    public void tokenFetched(String token) {
        sharedPreferencesHelper.setToken(token);
    }

    public void setListener(GetNewsCategoriesListener listener) {
        this.listener = listener;
    }
}
