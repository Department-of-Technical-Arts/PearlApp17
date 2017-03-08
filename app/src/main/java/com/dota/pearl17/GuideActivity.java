package com.dota.pearl17;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ButtonBarLayout;
import android.view.View;
import android.widget.Button;
import android.content.Intent;

/**
 * Created by SHIVIKA NAGPAL on 08-03-2017.
 */

public class GuideActivity extends AppCompatActivity implements View.OnClickListener{

    private Button b1;
    private Button b2;
    private Button b3;
    private Button b4;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);

        b1=(Button)findViewById(R.id.AboutUs);
        b2=(Button)findViewById(R.id.ReachUs);
        b3=(Button)findViewById(R.id.CampusMap);
        b4=(Button)findViewById(R.id.ContactUs);

        b1.setOnClickListener(this);
        b2.setOnClickListener(this);
        b3.setOnClickListener(this);
        b4.setOnClickListener(this);

    }

    public void onClick(View v){

        switch(v.getId()){

            case R.id.AboutUs:
                startActivity(new Intent(GuideActivity.this,AboutUsActivity.class));
                break;
            case R.id.ReachUs:
                startActivity(new Intent(GuideActivity.this,ReachUsActivity.class));
                break;
            case R.id.CampusMap:
                startActivity(new Intent(GuideActivity.this,CampusMapActivity.class));
                break;
            case R.id.ContactUs:
                startActivity(new Intent(GuideActivity.this,ContactActivity.class));
                break;


        }

    }


}
