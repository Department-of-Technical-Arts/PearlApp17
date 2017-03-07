package com.example.shivikanagpal.contacts;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by SHIVIKA NAGPAL on 07-03-2017.
 */

public class SimpleFragmentPagerAdapter extends FragmentPagerAdapter {

    private String[] tabTitle=new String[]{"FOB","Club Senate"};
    Context context;
    private int pageCount=2;

    public SimpleFragmentPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context=context;
    }

    @Override
    public Fragment getItem(int position) {

        FragmentDemo fragmentDemo=new FragmentDemo();
        return fragmentDemo;

    }

    @Override
    public int getCount() {
        return pageCount;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitle[position];
    }
}
