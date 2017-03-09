package com.dota.pearl17;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.squareup.picasso.Picasso;

public class SponsorsActivity extends AppCompatActivity {

    RecyclerView sponsorRecycler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sponsors);

        Picasso.with(this)
                .load(R.drawable.sponsors_frame)
                .fit()
                .into((ImageView)findViewById(R.id.bg_sponsors));

        sponsorRecycler = (RecyclerView) findViewById(R.id.sponsor_recycler);
        sponsorRecycler.setAdapter(new SponsAdapter(this));
        sponsorRecycler.setLayoutManager(new LinearLayoutManager(this));

    }


    class MyViewHolder extends RecyclerView.ViewHolder{

        ImageView imageButton;

        public MyViewHolder(View itemView) {
            super(itemView);
            imageButton= (ImageView) itemView.findViewById(R.id.main_events);
        }
    }

    LayoutInflater inflater;

    class  SponsAdapter extends RecyclerView.Adapter<MyViewHolder>{

        Context context;
        int[] resources;


        public SponsAdapter(Context context) {
            this.context = context;
            inflater=LayoutInflater.from(context);
            //first value is 0 since it represents empty view; it is never used
            resources= new int[]{
                    R.drawable.sponsor1,
                    R.drawable.sponsor2,
                    R.drawable.sponsor3,
                    R.drawable.sponsor4,
                    R.drawable.sponsor5,
                    R.drawable.sponsor6,
                    R.drawable.sponsor7,
                    R.drawable.sponsor8
            };
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new MyViewHolder(inflater.inflate(R.layout.custom_main_row,parent,false));
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {

            holder.imageButton.requestLayout();
            Picasso.with(context)
                    .load(resources[position])
                    .fit()
                    .centerInside()
                    .into(holder.imageButton);
        }

        @Override
        public int getItemCount() {
            return resources.length;
        }
    }
}

