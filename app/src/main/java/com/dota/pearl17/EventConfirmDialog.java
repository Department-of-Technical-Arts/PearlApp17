package com.dota.pearl17;

/**
 * Created by ADMIN on 6.3.17.
 */


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
//import android.support.v7.app.AlertDialog;
// import android.view.View;

/**
 * Created by ADMIN on 23.9.16.
 */
public class EventConfirmDialog extends android.support.v4.app.DialogFragment {

    public confirm mListener;
    public IdTableManager cart;
    ArrayList<String> events;
    RecyclerView eventList;
    Context context;
    LayoutInflater inflater;
    Typeface custom_font;

    public EventConfirmDialog() {
        // Required empty public constructor
    }
    public EventConfirmDialog(Context context){
        this.context = context;
        if (context instanceof confirm) {
            mListener = (confirm) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement deleteInteract");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        super.onCreateDialog(savedInstanceState);

        Typeface custom_font = Typeface.createFromAsset(context.getAssets(),  "fonts/goodpro_condmedium.otf");

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        View view = getActivity().getLayoutInflater().inflate(R.layout.event_confirm, null, false);

        builder.setView(view);

        builder.setNegativeButton("Cancel",null);

        builder.setPositiveButton("Add Events", new DialogInterface.OnClickListener() {

            @Override

            public void onClick(DialogInterface dialog, int which) {
                mListener.confirm_events(events);
            }

        });

        cart = new IdTableManager(context);
        events = new ArrayList<>();

        events = cart.getEventNames();

        eventList = (RecyclerView) view.findViewById(R.id.events);

        eventList.setAdapter(new EventAdapter(context));
        eventList.setAdapter(new EventAdapter(context));
        eventList.setLayoutManager(new LinearLayoutManager(context));
        eventList.setNestedScrollingEnabled(false);
        return builder.create();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        TextView eventName;

        public MyViewHolder(View itemView) {
            super(itemView);
            eventName = (TextView)itemView.findViewById(R.id.event_name);
            eventName.setTypeface(custom_font);
        }
    }

    class EventAdapter extends RecyclerView.Adapter<MyViewHolder>{

        Context ctx;

        public EventAdapter(Context context){

            ctx = context;
            inflater = LayoutInflater.from(ctx);

        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new MyViewHolder(inflater.inflate(R.layout.item_event_confirm,parent,false));
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            holder.eventName.setText(events.get(position));
            Log.v("Recycler bind",events.get(position));
        }

        @Override
        public int getItemCount() {
            return events.size();
        }


    }
    public interface confirm{

        void confirm_events(ArrayList<String> events);
    }
}
