package com.dota.pearl17;

import android.Manifest;
import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class EventDetailsActivity extends AppCompatActivity {


    String eventName;
    Event event;
    EventDatabaseManager eventDB;
    TextView title, desc;
    Typeface custom_font_bold, custom_font;
    Button rules;

    private Handler handlerUpdate;

    ProgressBar pb;
    int downloadedSize = 0;
    int totalSize = 0;
    TextView cur_val;



    private volatile boolean done;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_details);
        
        title = (TextView) findViewById(R.id.event_title);
        desc = (TextView) findViewById(R.id.event_desc);
        rules = (Button) findViewById(R.id.rules);

        custom_font_bold = Typeface.createFromAsset(getAssets(), "fonts/goodpro_condblack.otf");
        custom_font = Typeface.createFromAsset(getAssets(), "fonts/goodpro_condmedium.otf");

        Intent i = getIntent();

        eventName = i.getStringExtra("event_name");
        eventDB = new EventDatabaseManager(this);

        event = eventDB.getEvent(eventName);

        title.setText(event.getName());
        title.setTypeface(custom_font_bold);
        desc.setText(event.getDesc());
        desc.setTypeface(custom_font);
        rules.setTypeface(custom_font_bold);

        Picasso.with(this)
                .load(R.drawable.event_frame)
                .fit()
                .into((ImageView) findViewById(R.id.img_bg));
    }

    public void rules(View v) {
        downloadFile();
    }

    void downloadFile() {
    }

}


