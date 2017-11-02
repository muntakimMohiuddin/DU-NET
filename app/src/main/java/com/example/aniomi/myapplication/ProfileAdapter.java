package com.example.aniomi.myapplication;

/**
 * Created by aniomi on 9/29/17.
 */
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;

import static com.example.aniomi.myapplication.ProfileTab.int_items;


public class ProfileAdapter extends FragmentPagerAdapter {
    public ProfileAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new ProfileMy();
            case 1:
                return new ProfileEdit();


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
                return "My Profile";
            case 1:
                return "Edit Profile";


        }

        return null;
    }

}
