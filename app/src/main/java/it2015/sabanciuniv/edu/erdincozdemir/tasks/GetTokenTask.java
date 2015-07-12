package it2015.sabanciuniv.edu.erdincozdemir.tasks;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import it2015.sabanciuniv.edu.erdincozdemir.objects.ServiceResponseObject;
import it2015.sabanciuniv.edu.erdincozdemir.tasks.base.BaseGetDataTask;

/**
 * Created by Erdinc on 12.07.2015.
 */
public class GetTokenTask extends BaseGetDataTask {

    public interface GetTokenListener {
        void tokenFetched(String token);
    }

    private GetTokenListener listener;

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... params) {
        return super.doInBackground(params);
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

        Log.i("DEVELOPER", s);
        try {
            JSONObject jsonObject = new JSONObject(s);
            ServiceResponseObject<String> response = new ServiceResponseObject<>();
            response.setServiceMessageCode(jsonObject.getInt("serviceMessageCode"));
            response.setServiceMessageText(jsonObject.getString("serviceMessageText"));
            this.listener.tokenFetched(response.getServiceMessageText());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void setListener(GetTokenListener listener) {
        this.listener = listener;
    }
}
