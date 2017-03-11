package com.dota.pearl17;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class AppCreditsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_credits);

    }

    public void openBrowser(View v) {

        String url = (String) v.getTag();

        Intent i = new Intent();
        i.setAction(Intent.ACTION_VIEW);
        i.addCategory(Intent.CATEGORY_BROWSABLE);

        i.setData(Uri.parse(url));

        startActivity(i);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(this, MainActivity.class);
        i.putExtra("scrollTo",7);
        startActivity(i);
        finish();
    }
}
