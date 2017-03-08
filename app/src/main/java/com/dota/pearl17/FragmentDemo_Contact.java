package com.dota.pearl17;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dota.pearl17.R;

/**
 * Created by SHIVIKA NAGPAL on 07-03-2017.
 */

public class FragmentDemo_Contact extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragmentdemo_contactpage, container, false);
    }
}
