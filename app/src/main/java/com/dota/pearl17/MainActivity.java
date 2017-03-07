package com.dota.pearl17;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.support.v7.widget.RecyclerView;


import com.squareup.picasso.Picasso;

public class MainActivity extends AppCompatActivity {

    ImageView logo;
    RecyclerView recyclerView;

    @Override
    @TargetApi(21)
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//       getWindow().addContentView(inflater.inflate(R.layout.main_top, null), new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        logo = (ImageView) findViewById(R.id.logo);
        Picasso.with(this).load(R.drawable.pearl_button).fit().centerInside().into(logo);
        recyclerView = (RecyclerView) findViewById(R.id.headlinersRecycler);
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

    RecyclerClickListener clickListener=new RecyclerClickListener() {
        @Override
        public void onClick(View v, int pos) {
            switch (pos){
                case 0:
                    //Events
                    startActivity(new Intent(MainActivity.this,EventsActivity.class));
                    break;
                case 1:
                    //Pro Shows
                    startActivity(new Intent(MainActivity.this,ProShowActivity.class));
                    break;
                case 2:
                    //Talks
                    break;
                case 3:
                    //Schedule
                    break;
                case 4:
                    //Guide
                    break;
                case 5:
                    startActivity(new Intent(MainActivity.this,RegistrationActivity.class));
                    //startActivity(intent);
            }
        }
    };


    class MyViewHolder extends RecyclerView.ViewHolder{

        ImageButton imageButton;

        public MyViewHolder(View itemView) {
            super(itemView);
            imageButton= (ImageButton) itemView.findViewById(R.id.main_events);
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
            resources= new int[]{R.drawable.events_button, R.drawable.pro_shows_button, R.drawable.talks_button, R.drawable.schedule_button, R.drawable.guide_button,R.drawable.register_button,R.drawable.sponsors_button,R.drawable.app_credits_button};

        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new MyViewHolder(inflater.inflate(R.layout.custom_main_row,parent,false));
        }

        int prev=-1;

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            Picasso.with(MainActivity.this).load(resources[position]).fit().into(holder.imageButton);
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

}