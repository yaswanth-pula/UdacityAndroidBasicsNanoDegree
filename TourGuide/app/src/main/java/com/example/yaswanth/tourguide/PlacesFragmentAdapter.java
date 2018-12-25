package com.example.yaswanth.tourguide;


import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class PlacesFragmentAdapter extends FragmentPagerAdapter {
    protected PlacesFragmentAdapter(FragmentManager fm) {
        super(fm);
    }

    //Array of Tabs Titles
    private String titles[] = {"About", "Sights", "Amusements", "Eat-drink", "Shop"};

    @Override
    public Fragment getItem(int position) {
        if (position == 0)
            return new AboutFragment();
        else if (position == 1)
            return new SightsFragment();
        else if (position == 2)
            return new AmusementFragment();
        else if (position == 3)
            return new EatDrinkFragment();
        else
            return new ShopFragment();
    }

    @Override
    public int getCount() {
        //5 slides of tabs
        return 5;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        //setting Tab Titles
        return titles[position];
    }

}
