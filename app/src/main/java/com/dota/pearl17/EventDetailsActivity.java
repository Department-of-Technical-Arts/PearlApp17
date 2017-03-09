package com.dota.pearl17;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
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
    IdTableManager cart;
    TextView title, desc;
    Typeface custom_font_bold, custom_font;
    Button participate, rules;

    ProgressBar pb;
    Dialog dialog;
    int downloadedSize = 0;
    int totalSize = 0;
    TextView cur_val;

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
        downloadFile();
    }

    void downloadFile(){

        try {
            URL url = new URL(event.getRules());
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

            urlConnection.setRequestMethod("GET");
            urlConnection.setDoOutput(true);

            //connect
            urlConnection.connect();

            //set the path where we want to save the file
            File SDCardRoot = Environment.getExternalStorageDirectory();
            //create a new file, to save the downloaded file
            File file = new File(SDCardRoot,eventName + " Rules");

            FileOutputStream fileOutput = new FileOutputStream(file);

            //Stream used for reading the data from the internet
            InputStream inputStream = urlConnection.getInputStream();

            //this is the total size of the file which we are downloading
            totalSize = urlConnection.getContentLength();

            runOnUiThread(new Runnable() {
                public void run() {
                    pb.setMax(totalSize);
                }
            });

            //create a buffer...
            byte[] buffer = new byte[1024];
            int bufferLength = 0;

            while ( (bufferLength = inputStream.read(buffer)) > 0 ) {
                fileOutput.write(buffer, 0, bufferLength);
                downloadedSize += bufferLength;
                // update the progressbar //
                runOnUiThread(new Runnable() {
                    public void run() {
                        pb.setProgress(downloadedSize);
                        float per = ((float)downloadedSize/totalSize) * 100;
                        cur_val.setText("Downloaded " + downloadedSize + "KB / " + totalSize + "KB (" + (int)per + "%)" );
                    }
                });
            }
            //close the output stream when complete //
            fileOutput.close();
            runOnUiThread(new Runnable() {
                public void run() {
                    // pb.dismiss(); // if you want close it..
                }
            });

        } catch (final MalformedURLException e) {
            showError("Error : MalformedURLException " + e);
            e.printStackTrace();
        } catch (final IOException e) {
            showError("Error : IOException " + e);
            e.printStackTrace();
        }
        catch (final Exception e) {
            showError("Error : Please check your internet connection " + e);
        }
    }

    void showError(final String err){
        runOnUiThread(new Runnable() {
            public void run() {
                Toast.makeText(EventDetailsActivity.this, err, Toast.LENGTH_LONG).show();
            }
        });
    }

}
