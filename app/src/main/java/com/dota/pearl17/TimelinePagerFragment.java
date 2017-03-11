package com.dota.pearl17;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by SHREEDA on 07-03-2017.
 */

public class TimelinePagerFragment extends Fragment {
    ArrayList<Long> times;
    ScheduleTableManager mTableManager;

    public TimelinePagerFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_schedule, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.container);
        TextView textView = (TextView) view.findViewById(R.id.notpresent);
        mTableManager = new ScheduleTableManager(getActivity());
//        final LinearLayout mYourLayout = (LinearLayout) view.findViewById(R.id.linlayout);

        recyclerView.setVisibility(View.GONE);
        textView.setVisibility(View.VISIBLE);

//        Picasso.with(getActivity())
//                .load(R.drawable.schedule_background_bottom)
//                .into(new Target() {
//                    @Override
//                    @TargetApi(16)
//                    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
//                        int sdk = android.os.Build.VERSION.SDK_INT;
//                        if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
//                            mYourLayout.setBackground(new BitmapDrawable(getResources(), bitmap));
//                        } else {
//                            mYourLayout.setBackground(new BitmapDrawable(getResources(), bitmap));
//                        }
//                    }
//
//                    @Override
//                    public void onBitmapFailed(Drawable errorDrawable) {
//                        // use error drawable if desired
//                    }
//
//                    @Override
//                    public void onPrepareLoad(Drawable placeHolderDrawable) {
//                        // use placeholder drawable if desired
//                    }
//                });


        times = mTableManager.getDistinctTime(getArguments().getInt("day"));

        if (times.isEmpty()) {
//            Log.e("TimelineFrag1", times.toString());
            recyclerView.setVisibility(View.GONE);
            textView.setVisibility(View.VISIBLE);
        } else {

            MyAdapter mAdapter = new MyAdapter(getActivity());
            mAdapter.setTimes(times);
            recyclerView.setAdapter(mAdapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
//            Log.e("Day " + getArguments().getInt("day"), String.valueOf(times.size()));
        }

    }

    private String getTime(long time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time);
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        return sdf.format(calendar.getTime());

    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        View topLine, bottomLine;
        TextView time;
        View circuarTop, circularBottom;
        //        RecyclerView recyclerView;
        LinearLayout linearLayout;
        View itemView;

        public MyViewHolder(View itemView) {
            super(itemView);
            time = (TextView) itemView.findViewById(R.id.time);
            topLine = itemView.findViewById(R.id.topLine);
            bottomLine = itemView.findViewById(R.id.bottomLine1);
//            recyclerView= (RecyclerView) itemView.findViewById(R.id.container);
            linearLayout = (LinearLayout) itemView.findViewById(R.id.timelineCard);
            circuarTop = itemView.findViewById(R.id.imageTop);
            circularBottom = itemView.findViewById(R.id.imageBottom);
            this.itemView = itemView;
        }

        public void bindRecycler(Long time) {

            final ArrayList<ScheduleSet> sets = mTableManager.getSchedule(time);
            linearLayout.removeAllViews();
            for (int i = 0; i < sets.size(); i++) {
                final ScheduleSet set = sets.get(i);
                View v = LayoutInflater.from(getActivity()).inflate(R.layout.component_timeline_row, linearLayout, false);
                Picasso.with(getContext())
                        .load(R.drawable.schedulerow)
                        .fit()
                        .centerCrop()
                        .into((ImageView) v.findViewById(R.id.bg_schedule_row));
                ((TextView) v.findViewById(R.id.event_name)).setText(set.getName());
                ((TextView) v.findViewById(R.id.round_name)).setText(set.getRound());
                ((TextView) v.findViewById(R.id.venue)).setText(set.getVenue());
                v.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //Intent intent = new Intent(getActivity(), EventDetailsActivity.class);
                        //intent.putExtra("event_id", set.getEvent_id());
                        //startActivity(intent);
                    }
                });
                linearLayout.addView(v);
                if (i != sets.size() - 1) {
                    View divider = LayoutInflater.from(getActivity()).inflate(R.layout.divider, linearLayout, false);
                    linearLayout.addView(divider);
                }
            }

        }
    }

    class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {

        Context context;
        LayoutInflater inflater;
        ArrayList<Long> times;

        public MyAdapter(Context context) {
            this.context = context;
            inflater = LayoutInflater.from(context);
            times = new ArrayList<>();
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new MyViewHolder(inflater.inflate(R.layout.custom_timeline_row, parent, false));
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            holder.time.setText(getTime(times.get(position)));
            if (position == times.size() - 1) {
                holder.bottomLine.setVisibility(View.GONE);
                holder.circularBottom.setVisibility(View.GONE);
            } else {
                holder.bottomLine.setVisibility(View.VISIBLE);
                holder.circularBottom.setVisibility(View.VISIBLE);
            }
            if (position == 0) {
                holder.topLine.setVisibility(View.GONE);
                holder.circuarTop.setVisibility(View.GONE);
            } else {
                holder.topLine.setVisibility(View.VISIBLE);
                holder.circuarTop.setVisibility(View.VISIBLE);
            }
            holder.bindRecycler(times.get(position));

        }

        public void setTimes(ArrayList<Long> times) {
            this.times = times;
            notifyDataSetChanged();
        }

        @Override
        public int getItemCount() {
            return times.size();
        }
    }

}
