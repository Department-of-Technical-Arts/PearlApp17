package com.dota.pearl17;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.content.Intent;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

/**
 * Created by SHIVIKA NAGPAL on 08-03-2017.
 */

public class GuideActivity extends AppCompatActivity implements View.OnClickListener {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);

        Picasso.with(this)
                .load(R.drawable.guide_home_frame)
                .fit()
                .into((ImageView) findViewById(R.id.bg_guide_home));

        Button b1 = (Button) findViewById(R.id.AboutUs);
        Button b2 = (Button) findViewById(R.id.ReachUs);
        Button b3 = (Button) findViewById(R.id.CampusMap);

        b1.setOnClickListener(this);
        b2.setOnClickListener(this);
        b3.setOnClickListener(this);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(GuideActivity.this, MainActivity.class));
        finish();
    }

    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.AboutUs:
                startActivity(new Intent(GuideActivity.this, AboutUsActivity.class));
                break;
            case R.id.ReachUs:
                startActivity(new Intent(GuideActivity.this, ReachUsActivity.class));
                break;
            case R.id.CampusMap:
                checkPermissionsAndOpen();
                break;
        }

    }

    void checkPermissionsAndOpen() {
        String[] permissions = new String[]{Manifest.permission.ACCESS_FINE_LOCATION};
        if (ContextCompat.checkSelfPermission(this, permissions[0]) != PackageManager.PERMISSION_GRANTED) {
            //Request perms
            ActivityCompat.requestPermissions(this, permissions, 123);
        } else {
            //We have perms, start activity
            startActivity(new Intent(GuideActivity.this, MapsActivity.class));
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        switch (requestCode) {
            case 123: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Perms granted, move along
                    startActivity(new Intent(GuideActivity.this, MapsActivity.class));
                    return;
                } else {
                    //Do nothing, since openeing Maps without location is pointless
                    return;
                }
            }
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }

    }

}
