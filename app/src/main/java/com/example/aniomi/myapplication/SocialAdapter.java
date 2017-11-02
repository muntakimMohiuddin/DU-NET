package com.example.aniomi.myapplication;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import static com.example.aniomi.myapplication.SocialTab.int_items;

/**
 * Created by aniomi on 10/1/17.
 */

public class SocialAdapter extends FragmentPagerAdapter{
    public SocialAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new Find();
            case 1:
                return new Stream();
            case 2:
                return  new Massege();
            case 3:
                return new messanger_home();


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
                return "find";
            case 1:
                return "Stream";
            case 2:
                return "massege";
            case 3:
                return "messenger";

        }

        return null;
    }
}
