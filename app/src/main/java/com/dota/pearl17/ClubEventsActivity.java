package com.dota.pearl17;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.icu.text.DisplayContext;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ClubEventsActivity extends AppCompatActivity {

    ArrayList<Event> clubEvents;
    RecyclerView recycler;
    TextView clubName;
    Typeface custom_font, custom_font_bold, custom_font_bungee;
    EventDatabaseManager eventDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_club_events);

        eventDB = new EventDatabaseManager(this);

        clubEvents = eventDB.getClubEvents(getIntent().getStringExtra("club_name"));
        Log.v("clubEvents",clubEvents.toString());

        custom_font_bold = Typeface.createFromAsset(getAssets(),  "fonts/goodpro_condblack.otf");
        custom_font = Typeface.createFromAsset(getAssets(),  "fonts/goodpro_condmedium.otf");
        custom_font_bungee = Typeface.createFromAsset(getAssets(),  "fonts/bungee_regular.ttf");
        clubName = (TextView) findViewById(R.id.club_name);
        clubName.setText(getIntent().getStringExtra("club_name"));
        clubName.setTypeface(custom_font_bungee);


        recycler = (RecyclerView)findViewById(R.id.recycler);
        recycler.setLayoutManager(new LinearLayoutManager(this));
        recycler.setAdapter( new adapter(this,clubEvents));
    }


    public class adapter extends RecyclerView.Adapter<adapter.Item>{
        Context context;
        ArrayList<Event> events;
        public adapter(Context context, ArrayList<Event> items)
        {
            this.context=context;
            this.events=items;
        }
        @Override
        public Item onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater =LayoutInflater.from(context);
            View row= inflater.inflate(R.layout.event_row,parent,false);
            return new Item(row);
        }

        @Override
        public void onBindViewHolder(Item holder, int position) {

            if(position == getItemCount()-1){
                // Last Elem
                holder.b.setVisibility(Button.GONE);
            }

            holder.txt.setText(events.get(position).getName());
            final String clubName = holder.txt.getText().toString();
            holder.txt.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(ClubEventsActivity.this, EventDetailsActivity.class);
                    i.putExtra("event_name", clubName);
                    startActivity(i);
                }
            });
            holder.txt.setTypeface(custom_font);
        }

        @Override
        public int getItemCount() {
            return events.size();
        }
        class Item extends RecyclerView.ViewHolder{
            TextView txt ;
            Button b;
            Item(View itemView) {
                super(itemView);
                txt = (TextView) itemView.findViewById(R.id.text);
                b = (Button) itemView.findViewById(R.id.button_bottom);
            }
        }
    }


}
