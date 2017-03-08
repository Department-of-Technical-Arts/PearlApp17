package com.dota.pearl17;

import android.content.Intent;
import android.gesture.Gesture;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

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

        fontface = Typeface.createFromAsset(getAssets(), "fonts/cubano_regular.otf");

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
        PagerIndicator custom = (PagerIndicator) findViewById(R.id.custom_indicator);
        mDemoSlider.setCustomIndicator(custom);
        mDemoSlider.setDuration(3000); //Decide on duration
        loadSliderImages();
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
            //startActivity(new Intent(EventsHomeActivity.this,EventDetailsActivity.class));
        }
    };



    View.OnClickListener mSliderClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

        }
    };
    void loadSliderImages(){

        int resources[] = new int[]{
                R.drawable.headliner_carnival_zone,
                R.drawable.headliner_catharsis,
                R.drawable.headliner_crimson_curtain,
                R.drawable.headliner_fraglore,
                R.drawable.headliner_glitterati,
                R.drawable.headliner_photog,
                R.drawable.headliner_qubits,
                R.drawable.headliner_terpsichore,
                R.drawable.headliner_till_deaf,
        };

        for(int i=0; i < resources.length; i++){
            DefaultSliderView sliderView = new DefaultSliderView(this);
            sliderView.image(resources[i])
                    .setOnSliderClickListener(new BaseSliderView.OnSliderClickListener() {
                        @Override
                        public void onSliderClick(BaseSliderView slider) {

                            switch (mDemoSlider.getCurrentPosition()){
                                case 0:
                                    //Carnival Zone - None
                                    Toast.makeText(EventsHomeActivity.this, "Carnival", Toast.LENGTH_SHORT).show();
                                    break;
                                case 1:
                                    //Catharsis - Movie\
                                    Toast.makeText(EventsHomeActivity.this, "Catharsis", Toast.LENGTH_SHORT).show();
                                    break;
                                case 2:
                                    //Crimson Curtain - Drama
                                    Toast.makeText(EventsHomeActivity.this, "Crimson", Toast.LENGTH_SHORT).show();
                                    break;
                                case 3:
                                    //Fraglore - None
                                    Toast.makeText(EventsHomeActivity.this, "Fraglore", Toast.LENGTH_SHORT).show();
                                    break;
                                case 4:
                                    //Glitterati - None
                                    Toast.makeText(EventsHomeActivity.this, "Glitterati", Toast.LENGTH_SHORT).show();
                                    break;
                                case 5:
                                    //Photog Fest - Photog ?
                                    Toast.makeText(EventsHomeActivity.this, "Photog", Toast.LENGTH_SHORT).show();
                                    break;
                                case 6:
                                    //QuBITS - Quiz ?
                                    Toast.makeText(EventsHomeActivity.this, "QuBits", Toast.LENGTH_SHORT).show();
                                    break;
                                case 7:
                                    //Terpsichore - Dance
                                    Toast.makeText(EventsHomeActivity.this, "Terps", Toast.LENGTH_SHORT).show();
                                    break;
                                case 8:
                                    //Till Deaf - Music
                                    Toast.makeText(EventsHomeActivity.this, "Till Deaf", Toast.LENGTH_SHORT).show();
                                    break;
                            }
                        }
                    })
                    .setScaleType(BaseSliderView.ScaleType.CenterCrop); //slight leftover stretch to sides < 10dp, not noticeable
            mDemoSlider.addSlider(sliderView);
        }
    }

    class EventCategoryItem extends RecyclerView.ViewHolder{
        TextView title;
        ImageView icon;
        RelativeLayout eventButton;

        public EventCategoryItem(View v){
            super(v);
            v.setOnClickListener(mClickListener);

            eventButton = (RelativeLayout) v.findViewById(R.id.layout_event_category);

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
            final EventCategoryItem finalHolder = holder;
            holder.eventButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent i = new Intent(EventsHomeActivity.this,ClubEventsActivity.class);

                    i.putExtra("club_name" , finalHolder.title.getText().toString());
                    startActivity(i);
                }
            });
        }

        @Override
        public int getItemCount() {
            return titles.length;
        }
    }
}