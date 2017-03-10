package com.dota.pearl17;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class AboutUsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);

        TextView tx = (TextView) findViewById(R.id.about_us_textView);
        Typeface custom_font = Typeface.createFromAsset(getAssets(), "fonts/goodpro_condmedium.otf");
        tx.setTypeface(custom_font);

    }
}
