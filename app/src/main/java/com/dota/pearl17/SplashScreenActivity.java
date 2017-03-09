package com.dota.pearl17;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.AccelerateInterpolator;
import android.widget.RelativeLayout;

import com.plattysoft.leonids.ParticleSystem;

/**
 * Created by Shri Akhil on 09-03-2017.
 */

public class SplashScreenActivity extends AppCompatActivity {

    RelativeLayout container;
    EventDatabaseManager eventDB;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        container = (RelativeLayout) findViewById(R.id.container_splash);

        ViewTreeObserver viewTreeObserver = container.getViewTreeObserver();
        if (viewTreeObserver.isAlive()) {
            viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    container.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    emit();
                }
            });
        }

        eventDB = new EventDatabaseManager(this);
        eventDB.updateEvents();
        //Kaushik you can add the Volley code here

    }

    public void emit(){
        int num = 130;
        int dur = 4000;
        float accel = 0.00015f;
        float vel_y = 0.03f;
        float max_vel_y = 0.05f;

        new ParticleSystem(this,num,R.drawable.dot_red,4000,R.id.bg_hook)
                .setScaleRange(0.8f,1.2f)
                .setAcceleration(accel,90)
                .setInitialRotationRange(-45,45)
                .setSpeedByComponentsRange(0f,0f,vel_y,max_vel_y)
                .setFadeOut(200,new AccelerateInterpolator())
                .emitWithGravity(container, Gravity.TOP,30,4000);

        new ParticleSystem(this,num,R.drawable.dot_blue,4000,R.id.bg_hook)
                .setScaleRange(0.8f,1.2f)
                .setAcceleration(accel,90)
                .setInitialRotationRange(-45,45)
                .setSpeedByComponentsRange(0f,0f,vel_y,max_vel_y)
                .setFadeOut(200,new AccelerateInterpolator())
                .emitWithGravity(container, Gravity.TOP,30,4000);

        new ParticleSystem(this,num,R.drawable.dot_green,4000,R.id.bg_hook)
                .setScaleRange(0.8f,1.2f)
                .setAcceleration(accel,90)
                .setInitialRotationRange(-45,45)
                .setSpeedByComponentsRange(0f,0f,vel_y,max_vel_y)
                .setFadeOut(200,new AccelerateInterpolator())
                .emitWithGravity(container, Gravity.TOP,30,4000);

        new ParticleSystem(this,num,R.drawable.dot_yellow,4000,R.id.bg_hook)
                .setScaleRange(0.8f,1.2f)
                .setAcceleration(accel,90)
                .setInitialRotationRange(-45,45)
                .setSpeedByComponentsRange(0f,0f,vel_y,max_vel_y)
                .setFadeOut(200,new AccelerateInterpolator())
                .emitWithGravity(container, Gravity.TOP,30,4000);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashScreenActivity.this,MainActivity.class));
                finish();
            }
        }, 4000);
    }
}
