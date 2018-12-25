package com.example.yaswanth.tourguide;

import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Creating ViewPager for Tabs
        ViewPager viewPager = findViewById(R.id.mViewPager);
        //Creating Instance for FragmentAdapter
        PlacesFragmentAdapter placesFragmentAdapter = new PlacesFragmentAdapter(getSupportFragmentManager());
        viewPager.setAdapter(placesFragmentAdapter);
        //Creating Tab Layout
        TabLayout tabLayout = findViewById(R.id.tabs);
        //Adding a Viewpager to TabLayout
        tabLayout.setupWithViewPager(viewPager);

    }
}
