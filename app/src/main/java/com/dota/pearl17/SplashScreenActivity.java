package com.dota.pearl17;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
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
    }

    public void emit() {
        int num = 130;
        final int dur = 4300;
        float accel = 0.00015f;
        float vel_y = 0.03f;
        float max_vel_y = 0.05f;

        new ParticleSystem(this, num, R.drawable.confetti_red, dur, R.id.bg_hook)
                .setScaleRange(0.3f, 0.4f)
                .setAcceleration(accel, 90)
                .setInitialRotationRange(-45, 45)
                .setSpeedByComponentsRange(0f, 0f, vel_y, max_vel_y)
                .setFadeOut(200, new AccelerateInterpolator())
                .emitWithGravity(container, Gravity.TOP, 30, dur);

        new ParticleSystem(this, num, R.drawable.confetti_blue, dur, R.id.bg_hook)
                .setScaleRange(0.3f, 0.4f)
                .setAcceleration(accel, 90)
                .setInitialRotationRange(-45, 45)
                .setSpeedByComponentsRange(0f, 0f, vel_y, max_vel_y)
                .setFadeOut(200, new AccelerateInterpolator())
                .emitWithGravity(container, Gravity.TOP, 30, dur);

        new ParticleSystem(this, num, R.drawable.confetti_green, dur, R.id.bg_hook)
                .setScaleRange(0.3f, 0.4f)
                .setAcceleration(accel, 90)
                .setInitialRotationRange(-45, 45)
                .setSpeedByComponentsRange(0f, 0f, vel_y, max_vel_y)
                .setFadeOut(200, new AccelerateInterpolator())
                .emitWithGravity(container, Gravity.TOP, 30, dur);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(SplashScreenActivity.this, MainActivity.class);
                i.putExtra("fromSplash", 1);
                startActivity(i);
                eventDB = new EventDatabaseManager(SplashScreenActivity.this);
                eventDB.updateEvents(); //TODO Check if Async needed here. Progress Dialog might also be included
                finish();
            }
        }, dur);
    }
}
