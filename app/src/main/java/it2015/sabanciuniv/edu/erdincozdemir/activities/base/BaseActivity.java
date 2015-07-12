package it2015.sabanciuniv.edu.erdincozdemir.activities.base;

import android.app.Activity;
import android.os.Bundle;

import it2015.sabanciuniv.edu.erdincozdemir.R;

/**
 * Created by Erdinc on 12.07.2015.
 */
public class BaseActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.trans_right_in, R.anim.trans_right_out);
    }
}
