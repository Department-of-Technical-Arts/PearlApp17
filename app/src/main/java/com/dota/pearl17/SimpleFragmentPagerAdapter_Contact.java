package com.example.shivikanagpal.contacts;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by SHIVIKA NAGPAL on 07-03-2017.
 */

public class SimpleFragmentPagerAdapter_Contact extends FragmentPagerAdapter {

    private String[] tabTitle=new String[]{"FOB","Club Senate"};
    Context context;

    public SimpleFragmentPagerAdapter_Contact(FragmentManager fm, Context context) {
        super(fm);
        this.context=context;
    }

    @Override
    public Fragment getItem(int position) {

        FragmentDemo_Contact fragmentDemo=new FragmentDemo_Contact();
        return fragmentDemo;

    }

    @Override
    public int getCount() {
        return tabTitle.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitle[position];
    }
}
