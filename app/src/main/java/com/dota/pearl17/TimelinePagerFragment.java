package com.dota.pearl17;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Created by SHREEDA on 07-03-2017.
 */

public class TimelinePagerFragment {
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

}
