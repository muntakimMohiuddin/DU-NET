package com.example.aniomi.myapplication;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by asif on 11/3/17.
 */

public class GrouptabAdapter extends FragmentPagerAdapter {

    public GrouptabAdapter(FragmentManager fm)   {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new GroupList_fragment();
            case 1:
                return new Group_Search();


        }
        return null;
    }

    @Override
    public int getCount() {
        return GroupTab.int_items;
    }

    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Group";
            case 1:
                return "Search";

        }

        return null;
    }
}
