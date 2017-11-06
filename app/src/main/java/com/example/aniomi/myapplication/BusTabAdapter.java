package com.example.aniomi.myapplication;

/**
 * Created by asif on 11/5/17.
 */

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class BusTabAdapter  extends FragmentPagerAdapter {
    public BusTabAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new BusTabFind();
            case 1:
                return new BusTabTrack();
        }
        return null;
    }

    @Override
    public int getCount() {


        return 2;
    }

    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "FIND BUS";
            case 1:
                return "TRACK BUS";

        }

        return null;
    }
}
