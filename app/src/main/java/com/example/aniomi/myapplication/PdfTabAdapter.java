package com.example.aniomi.myapplication;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import static com.example.aniomi.myapplication.PdfTab.int_items;

/**
 * Created by asif on 11/6/17.
 */

public class PdfTabAdapter  extends FragmentPagerAdapter {
    public PdfTabAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new Book_list();
            /*case 1:
                return new Add_book();*/
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
                return "LIBRARY";
            case 1:
                return "ADD BOOK";

        }

        return null;
    }
}

