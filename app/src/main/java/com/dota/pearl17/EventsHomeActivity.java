package com.dota.pearl17;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.daimajia.slider.library.Indicators.PagerIndicator;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.DefaultSliderView;
import com.squareup.picasso.Picasso;

/**
 * Created by Shri Akhil on 06-03-2017.
 */

public class EventsHomeActivity extends AppCompatActivity {

    RecyclerView mRecycler;
    Typeface fontface;
    SliderLayout mDemoSlider;

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

        mRecycler = (RecyclerView) findViewById(R.id.recycler_less_categories);
        mRecycler.setLayoutManager(new GridLayoutManager(this,2));
        mRecycler.setAdapter(new LessEventsAdapter());

        View showMore = findViewById(R.id.btn_show_more);

        showMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //ANIM: This needs to have a slide up animation
                startActivity(new Intent(EventsHomeActivity.this,EventsShowMoreActivity.class));
                finish();
            }
        });

        mDemoSlider = (SliderLayout) findViewById(R.id.headlinersSlider);

        for(int i=0;i<5;i++){
            DefaultSliderView sliderView = new DefaultSliderView(this);
            sliderView.image(R.drawable.headliner_catharsis)
                    .setScaleType(BaseSliderView.ScaleType.CenterCrop); //slight leftover stretch to sides < 10dp, not noticeable
            mDemoSlider.addSlider(sliderView);
        }
        PagerIndicator custom = (PagerIndicator) findViewById(R.id.custom_indicator);
        mDemoSlider.setCustomIndicator(custom);
        mDemoSlider.setCurrentPosition(2,false);
        mDemoSlider.setDuration(3000); //Decide on duration
    }

    @Override
    protected void onStop() {
        mDemoSlider.stopAutoCycle();
        super.onStop();
    }

    View.OnClickListener mClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //handle item clicks here
            //condition should logically be the title
            //from here the EventsListScreen is opened
            startActivity(new Intent(EventsHomeActivity.this,EventDetailsActivity.class));
        }
    };
    class EventCategoryItem extends RecyclerView.ViewHolder{
        TextView title;
        ImageView icon;
        public EventCategoryItem(View v){
            super(v);
            v.setOnClickListener(mClickListener);

            title = (TextView) v.findViewById(R.id.title_category);
            icon = (ImageView) v.findViewById(R.id.icon_category);

            title.setTypeface(fontface);
        }

    }

    class LessEventsAdapter extends RecyclerView.Adapter<EventCategoryItem>{

        String titles[] = new String[]{
                "Journal",
                "ELAS",
                "QUIZZES",
                "HINDI T",
                "FINANCE",
                "VFX"
        };

        int icons[] = new int[]{
                R.drawable.icon_journal,
                R.drawable.icon_elas,
                R.drawable.icon_quiz,
                R.drawable.icon_hindi,
                R.drawable.icon_finance,
                R.drawable.icon_vfx
        };

        @Override
        public EventCategoryItem onCreateViewHolder(ViewGroup parent, int viewType) {
            return new EventCategoryItem(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_event_category,parent,false));
        }

        @Override
        public void onBindViewHolder(EventCategoryItem holder, int position) {
            holder.title.setText(titles[position]);
            holder.icon.setImageResource(icons[position]);
        }

        @Override
        public int getItemCount() {
            return titles.length;
        }
    }
}
