package com.dota.pearl17;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class EventsShowMoreActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events_show_more);
    }

    public void showLess(View v){
        //ANIM: This needs a slide down animation
        startActivity(new Intent(EventsShowMoreActivity.this,EventsHomeActivity.class));
        finish();
    }
}
