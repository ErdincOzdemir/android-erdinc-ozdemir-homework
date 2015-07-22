package it2015.sabanciuniv.edu.erdincozdemir.activities.base;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import it2015.sabanciuniv.edu.erdincozdemir.R;
import it2015.sabanciuniv.edu.erdincozdemir.activities.FavoriteNewsActivity;
import it2015.sabanciuniv.edu.erdincozdemir.activities.MainActivity;

/**
 * Created by Erdinc on 12.07.2015.
 */
public class BaseActivity extends Activity implements View.OnClickListener {

    private Button btnFooterMenuHome, btnFooterMenuFavorites;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);

        LinearLayout footer = (LinearLayout)findViewById(R.id.footerMenu);

        btnFooterMenuHome = (Button) footer.findViewById(R.id.btnFooterMenuHome);
        btnFooterMenuHome.setOnClickListener(this);

        btnFooterMenuFavorites = (Button) footer.findViewById(R.id.btnFooterMenuFavorites);
        btnFooterMenuFavorites.setOnClickListener(this);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.trans_right_in, R.anim.trans_right_out);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btnFooterMenuFavorites) {
            Intent intent = new Intent(this, FavoriteNewsActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);

        } else if(v.getId() == R.id.btnFooterMenuHome) {
            Intent intent = new Intent(this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
        if(!(this instanceof MainActivity) && !(this instanceof FavoriteNewsActivity))
            finish();
    }
}
