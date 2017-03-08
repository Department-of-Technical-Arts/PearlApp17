package com.dota.pearl17;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.TabHost;

import com.dota.pearl17.R;

public class ContactActivity extends AppCompatActivity {
    TabHost tabHost;
    private RecyclerView fob_veiw , club_senate_veiw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_contactpage);

        tabHost= (TabHost) findViewById(R.id.tabhost);
        tabHost.setup();
        TabHost.TabSpec tabSpec=tabHost.newTabSpec("fob");
        tabSpec.setContent(R.id.tab1);
        tabSpec.setIndicator("FOB");
        tabHost.addTab(tabSpec);

        tabSpec=tabHost.newTabSpec("club_senate");
        tabSpec.setContent(R.id.tab2);
        tabSpec.setIndicator("CLUB SENATE");
        tabHost.addTab(tabSpec);

        fob_veiw= (RecyclerView) findViewById(R.id.fob_list);
        club_senate_veiw= (RecyclerView) findViewById(R.id.club_senate);



    }
}
