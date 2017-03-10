package com.dota.pearl17;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.graphics.Typeface;

public class ReachUsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reach_us);

        TextView tx = (TextView) findViewById(R.id.reach_us_textView);
        Typeface custom_font = Typeface.createFromAsset(getAssets(), "fonts/goodpro_condmedium.otf");
        tx.setTypeface(custom_font);

    }
}