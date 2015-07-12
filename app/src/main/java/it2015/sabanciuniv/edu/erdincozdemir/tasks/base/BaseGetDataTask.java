package it2015.sabanciuniv.edu.erdincozdemir.tasks.base;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Erdinc on 12.07.2015.
 */
public class BaseGetDataTask extends AsyncTask<String, Void, String> {


    @Override
    protected String doInBackground(String... params) {
        Log.i("DEVELOPER", params[0]);

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        HttpURLConnection conn = null;
        StringBuilder builder = null;
        try {
            URL url = new URL(params[0]);
            conn = (HttpURLConnection)url.openConnection();

            BufferedReader reader
                    = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));

            builder = new StringBuilder();
            String line;

            while((line=reader.readLine())!=null){
                builder.append(line);
            }


        } catch (Exception ex){
            ex.printStackTrace();
        }finally {
            conn.disconnect();
        }

        return builder.toString();
    }
}
