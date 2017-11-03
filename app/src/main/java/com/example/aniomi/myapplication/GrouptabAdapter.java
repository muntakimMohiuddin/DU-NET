package com.example.aniomi.myapplication;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.View;

import static com.example.aniomi.myapplication.GroupTab.int_items;

/**
 * Created by asif on 11/3/17.
 */

public class GrouptabAdapter extends FragmentPagerAdapter {

    public GrouptabAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new Attending_Fragment();
            case 1:
                return new Interested_Fragment();
            case 2:
                return  new Not_Intersted_Fragment();
            case 3:
                return new Invitation_Fragment();


        }
        return null;
    }

    @Override
    public int getCount() {
        return int_items;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return false;
    }

    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Group";
            case 1:
                return "INVITATION";

        }

        return null;
    }
}
