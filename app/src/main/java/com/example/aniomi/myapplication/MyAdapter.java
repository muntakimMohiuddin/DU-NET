package com.example.aniomi.myapplication;

/**
 * Created by aniomi on 9/29/17.
 */

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import static com.example.aniomi.myapplication.BusTab.int_items;

/**
 * Created by Admin on 3/1/2017.
 */

public class MyAdapter  extends FragmentPagerAdapter {


    public MyAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new SearchByRoot();
            case 1:
                return new SearchByBus();


        }
        return null;
    }

    @Override
    public int getCount() {


        return int_items;
    }

    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Root";
            case 1:
                return "Bus";


        }

        return null;
    }
}
