package com.dota.pearl17;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.dota.pearl17.R;

public class ContactActivity extends AppCompatActivity {

    Toolbar toolbar;
    TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_contactpage);

        ViewPager viewPager=(ViewPager)findViewById(R.id.view_pager);
        viewPager.setAdapter(new SimpleFragmentPagerAdapter_Contact(getSupportFragmentManager(),this));

        tabLayout=(TabLayout)findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(viewPager);

    }
}