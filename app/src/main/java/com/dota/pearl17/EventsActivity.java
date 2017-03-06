package com.dota.pearl17;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

/**
 * Created by Shri Akhil on 06-03-2017.
 */

public class EventsActivity extends AppCompatActivity {

    GridLayout mGridLayout;
    Typeface fontface;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events_home);
         fontface = Typeface.createFromAsset(getAssets(),"cubano_regular.otf");
        ImageView topbar = (ImageView) findViewById(R.id.events_topbar);
        ImageView middlebar = (ImageView) findViewById(R.id.events_middlebar);
        Picasso.with(this)
                .load(R.drawable.events_top_bar)
                .fit()
                .into(topbar);
        Picasso.with(this)
                .load(R.drawable.events_middle_bar)
                .fit()
                .into(middlebar);
        mGridLayout = (GridLayout) findViewById(R.id.grid);

        initGrid();

        addItemToGrid();
        addItemToGrid();

        addItemToGrid();
        addItemToGrid();

        addItemToGrid();
        addItemToGrid();
    }
    void initGrid(){

    }
    void addItemToGrid(){
        View layout_button = LayoutInflater.from(this).inflate(R.layout.item_event_category,mGridLayout,false);
        TextView textView = (TextView) layout_button.findViewById(R.id.tv_category);
        textView.setTypeface(fontface);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("width",""+v.getWidth());
            }
        });
//        textView.setText(category);
        mGridLayout.addView(layout_button);
    }
}
