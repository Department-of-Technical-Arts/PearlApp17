package com.dota.pearl17;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class EventDetailsActivity extends AppCompatActivity {


    String eventName;
    Event event;
    EventDatabaseManager eventDB;
    IdTableManager cart;
    TextView title, desc;
    Typeface custom_font_bold, custom_font;
    Button participate, rules;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_details);
        title = (TextView) findViewById(R.id.event_title);
        desc = (TextView) findViewById(R.id.event_desc);

        rules = (Button) findViewById(R.id.rules);
        participate = (Button) findViewById(R.id.add);

        custom_font_bold = Typeface.createFromAsset(getAssets(),  "fonts/goodpro_condblack.otf");
        custom_font = Typeface.createFromAsset(getAssets(),  "fonts/goodpro_condmedium.otf");

        Intent i = getIntent();

        eventName = i.getStringExtra("event_name");
        eventDB = new EventDatabaseManager(this);
        cart = new IdTableManager(this);

        event = eventDB.getEvent(eventName);

        title.setText(event.getName());
        title.setTypeface(custom_font_bold);
        desc.setText(event.getDesc());
        desc.setTypeface(custom_font);
        rules.setTypeface(custom_font_bold);
        participate.setTypeface(custom_font_bold);

        Picasso.with(this)
                .load(R.drawable.event_frame)
                .fit()
                .into((ImageView)findViewById(R.id.img_bg));
    }

    public void add(View v){

        cart.addEntry(event.getId(),event.getName());
        //disable button

    }

    public void rules(View v){

        //download rules
    }
}
