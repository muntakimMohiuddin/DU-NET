package com.example.aniomi.myapplication;

/**
 * Created by aniomi on 10/24/17.
 */
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by asif on 10/2/17.
 */

public class SectionsPagerAdapter extends FragmentPagerAdapter {

    private final List<Fragment> mFragmentList = new ArrayList<>();
    private final List<String> mFragmentTitleList = new ArrayList<>();

    public void addFragment(Fragment fragment,String title)
    {
        mFragmentList.add(fragment);
        mFragmentTitleList.add(title);
    }

    public void replaceFragment(Fragment fragment,String title,int position)
    {
        mFragmentList.set(position,fragment);
        mFragmentTitleList.set(position,title);
        notifyDataSetChanged();
    }

    public void reset()
    {
        mFragmentTitleList.clear();
        mFragmentList.clear();
        notifyDataSetChanged();
    }
    public SectionsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mFragmentTitleList.get(position);
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }
}