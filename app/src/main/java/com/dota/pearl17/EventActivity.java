package com.dota.pearl17;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class EventActivity extends AppCompatActivity {


    int event_id;
    Event event;
    EventDatabaseManager eventDB;
    IdTableManager cart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);

        Intent i = getIntent();

        i.getIntExtra("event_id",event_id);

        eventDB = new EventDatabaseManager(this);
        cart = new IdTableManager(this);

        event = eventDB.getEvent(event_id);


    }

    public void add(View v){

        cart.addEntry(event_id);
        //disable button

    }
}
