package com.dota.pearl17;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class TalkDetailsActivity extends AppCompatActivity {


    int event_id;
    Event event;
    EventDatabaseManager eventDB;
    IdTableManager cart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_talk_details);

        Intent i = getIntent();

        i.getIntExtra("event_id",event_id);

//        eventDB = new EventDatabaseManager(this);
//        cart = new IdTableManager(this);
//
//        event = eventDB.getEvent(event_id);

        Picasso.with(this)
                .load(R.drawable.talks_frame)
                .fit()
                .into((ImageView)findViewById(R.id.img_bg));
    }

    public void add(View v){

        cart.addEntry(event_id,"");
        //disable button

    }
}
