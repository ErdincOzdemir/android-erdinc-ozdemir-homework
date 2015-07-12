package it2015.sabanciuniv.edu.erdincozdemir.tasks;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import it2015.sabanciuniv.edu.erdincozdemir.objects.Comment;
import it2015.sabanciuniv.edu.erdincozdemir.objects.ServiceResponseObject;
import it2015.sabanciuniv.edu.erdincozdemir.utils.SharedPreferencesHelper;

/**
 * Created by Erdinc on 12.07.2015.
 */
public class PostCommentTask extends AsyncTask<String, Void, String> {

    public interface PostCommentTaskListener {
        void commentPostedSuccessfully();
        void commentPostFailed();
    }

    private Comment comment;
    private Context context;
    private ProgressDialog dialog;
    private PostCommentTaskListener listener;
    private SharedPreferencesHelper sharedPreferencesHelper;

    public PostCommentTask(Context context, Comment comment) {
        this.context = context;
        this.comment = comment;
    }

    @Override
    protected void onPreExecute() {
        dialog = new ProgressDialog(this.context);
        dialog.setTitle("Comment Sending");
        dialog.setMessage("Please wait");
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.show();
    }

    @Override
    protected String doInBackground(String... params) {
        sharedPreferencesHelper = new SharedPreferencesHelper(this.context);
        params[0] = params[0] + sharedPreferencesHelper.getToken();
        HttpURLConnection conn = null;
        StringBuilder builder = new StringBuilder();
        try {
            URL url = new URL(params[0]);
            conn = (HttpURLConnection)url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
            String paramStr = "newsid=" + comment.getNewsId()
                    +"&text=" + comment.getText()
                    +"&name=" + comment.getName()
                    + "&lastname=" + comment.getLastName();

            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));

            writer.write(paramStr);
            writer.flush();

            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            builder = new StringBuilder();
            String line;

            while((line=reader.readLine())!=null){
                builder.append(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conn.disconnect();
        }

        return builder.toString();
    }

    @Override
    protected void onPostExecute(String s) {

        ServiceResponseObject<String> serviceResponseObject = new ServiceResponseObject<>();

        try {
            JSONObject jsonObject = new JSONObject(s);
            serviceResponseObject.setServiceMessageCode(jsonObject.getInt("serviceMessageCode"));
            serviceResponseObject.setServiceMessageText(jsonObject.getString("serviceMessageText"));
            if(serviceResponseObject.getServiceMessageCode() == 1) {
                this.listener.commentPostedSuccessfully();
            } else if(serviceResponseObject.getServiceMessageCode() == 0) {
                this.listener.commentPostFailed();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        dialog.dismiss();

    }

    public void setListener(PostCommentTaskListener listener) {
        this.listener = listener;
    }
}
