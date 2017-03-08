package com.dota.pearl17;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;


import com.squareup.picasso.Picasso;

public class MainActivity extends AppCompatActivity {

    ImageView logo;
    RecyclerView recyclerView;
    LinearLayout.LayoutParams params_empty, params_row;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//       getWindow().addContentView(inflater.inflate(R.layout.main_top, null), new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        initLayoutParams();

        logo = (ImageView) findViewById(R.id.logo);
        Picasso.with(this).load(R.drawable.pearl_button).fit().centerInside().into(logo);
        recyclerView = (RecyclerView) findViewById(R.id.landingRecycler);
        recyclerView.setAdapter(new MyAdapter(this));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setNestedScrollingEnabled(false);


//        Animation animation = new ScaleAnimation(0, 1, 0, 1, Animation.RELATIVE_TO_SELF, 0.5f,
//                Animation.RELATIVE_TO_SELF, 0.5f);
//        animation.setDuration(200);
//        animation.setInterpolator(new DecelerateInterpolator());
//        animation.setFillAfter(true);
//        logo.startAnimation(animation);
    }

    public void initLayoutParams(){
        int height_empty = (int)(getResources().getDimension(R.dimen.landing_padding_height));
        int height_row = (int)(getResources().getDimension(R.dimen.landing_row_height));
        int margin_side = (int)(getResources().getDimension(R.dimen.margin_side));

        params_empty = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,height_empty);
        params_empty.setMargins(margin_side,0,margin_side,0);

        params_row = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,height_row);
        params_row.setMargins(margin_side,0,margin_side,0);
    }

    RecyclerClickListener clickListener=new RecyclerClickListener() {
        @Override
        public void onClick(View v, int pos) {
            switch (pos){
                case 0:
                    //Space
                    break;
                case 1:
                    //Events
                    startActivity(new Intent(MainActivity.this,EventsHomeActivity.class));
                    break;
                case 2:
                    //Pro Shows
                    startActivity(new Intent(MainActivity.this,ProShowActivity.class));
                    break;
                case 3:
                    //Talks
                    startActivity(new Intent(MainActivity.this,TalksActivity.class));
                    break;
                case 4:
                    //Schedule
                    break;
                case 5:
                    //Guide
                    startActivity(new Intent(MainActivity.this,GuideActivity.class));
                    break;
                case 6:
                    //Register
                    startActivity(new Intent(MainActivity.this,RegistrationActivity.class));
                    break;
                case 7:
                    //Sponsor
                    startActivity(new Intent(MainActivity.this,SponsorsActivity.class));

                    break;
                case 8:
                    //App Credits
                    break;
            }
        }
    };

    class MyViewHolder extends RecyclerView.ViewHolder{

        ImageView imageButton;

        public MyViewHolder(View itemView) {
            super(itemView);
            imageButton= (ImageView) itemView.findViewById(R.id.main_events);
            if(clickListener!=null){
                imageButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        clickListener.onClick(v, getLayoutPosition());
                    }
                });
            }
        }
    }

    LayoutInflater inflater;

    class  MyAdapter extends RecyclerView.Adapter<MyViewHolder>{

        Context context;
        int[] resources;


        public MyAdapter(MainActivity context) {
            this.context = context;
            inflater=LayoutInflater.from(context);
            //first value is 0 since it represents empty view; it is never used
            resources= new int[]{
                    0,
                    R.drawable.events_button,
                    R.drawable.pro_shows_button,
                    R.drawable.talks_button,
                    R.drawable.schedule_button,
                    R.drawable.guide_button,
                    R.drawable.register_button,
                    R.drawable.sponsors_button,
                    R.drawable.app_credits_button
            };

        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new MyViewHolder(inflater.inflate(R.layout.custom_main_row,parent,false));
        }

        int prev=-1;

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            if(position==0)
            {
                //Blank case
                holder.imageButton.setImageDrawable(null);
                holder.imageButton.setLayoutParams(params_empty);
                holder.imageButton.requestLayout();
                return;
            }
            else
            {
                holder.imageButton.setLayoutParams(params_row);
                holder.imageButton.requestLayout();
                Picasso.with(MainActivity.this)
                        .load(resources[position])
                        .fit()
                        .centerInside()
                        .into(holder.imageButton);
            }
//            if (prev < position) {
//                prev = position;
//            } else {
//                return;
//            }

//            Animation animation = new ScaleAnimation(0, 1, 0, 1, Animation.RELATIVE_TO_SELF, 0.5f,
//                    Animation.RELATIVE_TO_SELF, 0.5f);
//            animation.setDuration(200);
//            animation.setInterpolator(new DecelerateInterpolator(1.5f));
//            animation.setFillAfter(true);
//            animation.setStartOffset(150 + position * 150);
//            holder.itemView.startAnimation(animation);


        }

        @Override
        public int getItemCount() {
            return resources.length;
        }
    }
    int click_count=0;
    public void easterEgg(View v){
        if(click_count==5){
            click_count=0;
            //open developers screen or show an animation
        }
        click_count++;
    }
}