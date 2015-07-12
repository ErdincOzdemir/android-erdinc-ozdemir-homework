package it2015.sabanciuniv.edu.erdincozdemir.tasks;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import it2015.sabanciuniv.edu.erdincozdemir.objects.Comment;
import it2015.sabanciuniv.edu.erdincozdemir.objects.News;
import it2015.sabanciuniv.edu.erdincozdemir.objects.ServiceResponseObject;
import it2015.sabanciuniv.edu.erdincozdemir.tasks.base.BaseGetDataTask;
import it2015.sabanciuniv.edu.erdincozdemir.utils.Config;
import it2015.sabanciuniv.edu.erdincozdemir.utils.SharedPreferencesHelper;

/**
 * Created by Erdinc on 12.07.2015.
 */
public class GetCommentsTask extends BaseGetDataTask implements GetTokenTask.GetTokenListener {

    public interface GetCommentsTaskListener {
        void commentsFetched(List<Comment> comments);
    }


    private Context context;
    private SharedPreferencesHelper sharedPreferencesHelper;
    private GetCommentsTaskListener listener;
    private News news;

    public GetCommentsTask(Context context, News news) {
        this.context = context;
        this.news = news;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        sharedPreferencesHelper = new SharedPreferencesHelper(this.context);
        if (sharedPreferencesHelper.getToken() == null) {
            GetTokenTask getTokenTask = new GetTokenTask();
            getTokenTask.setListener(this);
            getTokenTask.execute(Config.loginUrl);
        }
    }

    @Override
    protected String doInBackground(String... params) {
        params[0] = params[0] + news.getId() + "?token=" + sharedPreferencesHelper.getToken();
        return super.doInBackground(params);
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

        ServiceResponseObject<Comment> commentsResponseObject = new ServiceResponseObject<>();
        try {
            JSONObject jsonObject = new JSONObject(s);
            commentsResponseObject.setServiceMessageCode(jsonObject.getInt("serviceMessageCode"));
            commentsResponseObject.setServiceMessageText(jsonObject.getString("serviceMessageText"));
            JSONArray arr = jsonObject.getJSONArray("items");
            List<Comment> comments = new ArrayList<>();
            for (int i = 0; i < arr.length(); i++) {
                JSONObject obj = arr.getJSONObject(i);
                Comment comment = new Comment(obj.getInt("id"), obj.getInt("news_id"), obj.getString("text"), obj.getString("name"), obj.getString("lastname"));
                comments.add(comment);
            }
            commentsResponseObject.setResponseItems(comments);
            if(commentsResponseObject.getServiceMessageCode() == 1) {
                this.listener.commentsFetched(commentsResponseObject.getResponseItems());
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
        GetCommentsTask getCommentsTask = new GetCommentsTask(this.context, this.news);
        getCommentsTask.setListener(this.listener);
        getCommentsTask.execute(Config.getCommentsByNewsIdUrl);
    }

    public void setListener(GetCommentsTaskListener listener) {
        this.listener = listener;
    }
}
