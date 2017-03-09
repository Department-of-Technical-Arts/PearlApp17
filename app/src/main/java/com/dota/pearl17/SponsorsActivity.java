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

    private String base_spons_url="";
    RecyclerView sponsorRecycler;
    private int numberOfSponsors=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sponsors);

        sponsorRecycler = (RecyclerView) findViewById(R.id.sponsor_recycler);
        sponsorRecycler.setAdapter(new SponsAdapter(this));
        sponsorRecycler.setLayoutManager(new LinearLayoutManager(this));

        numberOfSponsors = getNumberOfSponsors();
    }

    int getNumberOfSponsors(){
        // Add code to send Volley request
        // Receive number of sponsors
        // return this integer value
        return 0;
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

        public SponsAdapter(Context context) {
            this.context = context;
            inflater=LayoutInflater.from(context);
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new MyViewHolder(inflater.inflate(R.layout.custom_main_row,parent,false));
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {

            holder.imageButton.requestLayout();
            Picasso.with(context)
                    .load(base_spons_url+"/"+position) //This is the varying url
                    .fit()
                    .centerInside()
                    .into(holder.imageButton);
        }

        @Override
        public int getItemCount() {
            return numberOfSponsors;
        }
    }
}

