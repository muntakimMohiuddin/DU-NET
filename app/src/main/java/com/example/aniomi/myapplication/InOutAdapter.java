package com.example.aniomi.myapplication;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import static com.example.aniomi.myapplication.In_out_tab.int_items;

/**
 * Created by aniomi on 9/29/17.
 */

public class InOutAdapter extends  FragmentPagerAdapter{
    public InOutAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new logIn_Fragment();
            case 1:
                return new signUP_fragment();


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
                return "SignIn";
            case 1:
                return "SignOut";


        }

        return null;
    }
}
