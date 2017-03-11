package com.dota.pearl17;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

public class EventDetailsActivity extends AppCompatActivity {


    String eventName;
    Event event;
    EventDatabaseManager eventDB;
    TextView title, desc;
    Typeface custom_font_bold, custom_font;
    Button rules;

    BroadcastReceiver onComplete;

    DownloadManager downloadManager;


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
                .into((ImageView) findViewById(R.id.bg_club_details));

    }

    private long downloadFile(Uri uri) {

        final long downloadReference;

        // Create request for android download manager
        downloadManager = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
        DownloadManager.Request request = new DownloadManager.Request(uri);

        //Setting title of request
        request.setTitle(eventName + " Rules");

        //Setting description of request
        request.setDescription("Rules for " + eventName);

        //Set the download completed notification as VISIBLE
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);

        //Set the local destination for the downloaded file to a path within the application's external files directory
        request.setDestinationInExternalFilesDir(EventDetailsActivity.this, Environment.DIRECTORY_DOWNLOADS, eventName + "Rules");

        downloadReference = downloadManager.enqueue(request);

        //Enqueue download and save into referenceId

        return downloadReference;
    }


    public void rules(View v) {

        if(event.getRules().trim().equals("")){
            Toast.makeText(this,"Event rules not available. Try again later.",Toast.LENGTH_SHORT).show();
            return;
        }
        downloadFile(Uri.parse(event.getRules()));
    }

}


