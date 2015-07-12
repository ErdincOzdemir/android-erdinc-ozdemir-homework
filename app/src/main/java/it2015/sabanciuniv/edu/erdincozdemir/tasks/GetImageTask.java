package it2015.sabanciuniv.edu.erdincozdemir.tasks;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import java.net.URL;

/**
 * Created by Erdinc on 12.07.2015.
 */
public class GetImageTask extends AsyncTask<String, Void, Bitmap> {

    public interface GetImageTaskListener {
        void imageFetched(Bitmap image);
    }

    private GetImageTaskListener listener;

    @Override
    protected Bitmap doInBackground(String... params) {
        Bitmap bmp = null;
        try {
            URL imageURL = new URL(params[0]);
            bmp = BitmapFactory.decodeStream(imageURL.openStream());

        } catch (Exception e) {
            // TODO: handle exception
            Log.e("error", "Downloading Image Failed");

        }

        return bmp;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        if(bitmap != null) {
            this.listener.imageFetched(bitmap);
        }
    }

    public void setListener(GetImageTaskListener listener) {
        this.listener = listener;
    }
}
