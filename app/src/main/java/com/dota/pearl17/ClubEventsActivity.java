package com.dota.pearl17;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class ClubEventsActivity extends AppCompatActivity {

    ArrayList<Event> clubEvents;
    RecyclerView recycler;
    TextView tv_clubName;
    String clubName;
    Typeface cubano,bungee;
    EventDatabaseManager eventDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_club_events);


        eventDB = new EventDatabaseManager(this);
        clubName = getIntent().getStringExtra("club_name");
        clubEvents = eventDB.getClubEvents(clubName);
//        Log.v("clubEvents", clubEvents.toString());

        cubano = Typeface.createFromAsset(getAssets(), "fonts/cubano.otf");
        bungee = Typeface.createFromAsset(getAssets(), "fonts/bungee.ttf");

        tv_clubName = (TextView) findViewById(R.id.club_name);
        tv_clubName.setText(getClubTitle());
        tv_clubName.setTypeface(bungee);


        recycler = (RecyclerView) findViewById(R.id.recycler);
        recycler.setLayoutManager(new LinearLayoutManager(this));
        recycler.setAdapter(new adapter(this, clubEvents));
    }

    String getClubTitle() {
        //For special cases where the name is not same as that on button
        if (clubName.equalsIgnoreCase("ELAS")) {
            return "ENGLISH LANGUAGE";
        } else if (clubName.equalsIgnoreCase("HINDI T")) {
            return "HINDI TARANG";
        } else {
            return clubName;
        }
    }

    public class adapter extends RecyclerView.Adapter<adapter.Item> {
        Context context;
        ArrayList<Event> events;

        public adapter(Context context, ArrayList<Event> items) {
            this.context = context;
            this.events = items;
        }

        @Override
        public Item onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(context);
            View row = inflater.inflate(R.layout.event_row, parent, false);
            return new Item(row);
        }

        @Override
        public void onBindViewHolder(Item holder, int position) {

            if (position == getItemCount() - 1) {
                // Last Elem
                holder.b.setVisibility(Button.GONE);
                holder.lines.setVisibility(ImageView.VISIBLE);
            }

            holder.txt.setText(events.get(position).getName());
            final String eventName = holder.txt.getText().toString();
            holder.txt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(ClubEventsActivity.this, EventDetailsActivity.class);
                    i.putExtra("event_name", eventName);
                    startActivity(i);
                }
            });
            holder.txt.setTypeface(cubano);
        }

        @Override
        public int getItemCount() {
            return events.size();
        }

        class Item extends RecyclerView.ViewHolder {
            TextView txt;
            Button b;
            ImageView lines;

            Item(View itemView) {
                super(itemView);
                txt = (TextView) itemView.findViewById(R.id.text);
                b = (Button) itemView.findViewById(R.id.button_bottom);
                lines = (ImageView) itemView.findViewById(R.id.lines_bottom);
            }
        }
    }


}
