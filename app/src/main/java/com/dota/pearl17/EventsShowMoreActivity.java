package com.dota.pearl17;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class EventsShowMoreActivity extends AppCompatActivity {

    Typeface fontface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events_show_more);

        fontface = Typeface.createFromAsset(getAssets(), "fonts/cubano_regular.otf");

        ImageView topbar = (ImageView) findViewById(R.id.events_topbar);
        Picasso.with(this)
                .load(R.drawable.events_top_bar)
                .fit()
                .into(topbar);

        RecyclerView mRecycler = (RecyclerView) findViewById(R.id.recycler_more_categories);
        mRecycler.setLayoutManager(new GridLayoutManager(this,2));
        mRecycler.setAdapter(new MyAdapter());
    }

    public void showLess(View v){
        //ANIM: This needs a slide down animation
        startActivity(new Intent(EventsShowMoreActivity.this,EventsHomeActivity.class));
        finish();
    }

    class EventCategoryItem extends RecyclerView.ViewHolder{
        TextView title;
        ImageView icon;
        RelativeLayout eventButton;
        public EventCategoryItem(View v){
            super(v);
            title = (TextView) v.findViewById(R.id.title_category);
            icon = (ImageView) v.findViewById(R.id.icon_category);

            eventButton = (RelativeLayout) v.findViewById(R.id.layout_event_category);


            title.setTypeface(fontface);
        }



    }

    class MyAdapter extends RecyclerView.Adapter<EventCategoryItem>{

        String titles[] = new String[]{
                "DANCE",
                "MUSIC",
                "DRAMA",
                "PHOTOG",
                "SHADES",
                "MOVIE",
                "DESIGN",
                "JOURNAL",
                "ELAS",
                "QUIZZES",
                "HINDI T",
                "FINANCE",
                "VFX"
        };

        int icons[] = new int[]{
                R.drawable.icon_dance,
                R.drawable.icon_music,
                R.drawable.icon_drama,
                R.drawable.icon_photog,
                R.drawable.icon_shades,
                R.drawable.icon_movie,
                R.drawable.icon_design,
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

                    Intent i = new Intent(EventsShowMoreActivity.this,ClubEventsActivity.class);

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
