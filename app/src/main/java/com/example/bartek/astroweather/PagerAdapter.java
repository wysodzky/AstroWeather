package com.example.bartek.astroweather;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.bartek.astroweather.fragments.ForecastFragment;
import com.example.bartek.astroweather.fragments.MoonFragment;
import com.example.bartek.astroweather.fragments.SunFragment;
import com.example.bartek.astroweather.fragments.WeatherFragment;

/**
 * Created by Bartek on 2018-04-20.
 */

public class PagerAdapter extends FragmentStatePagerAdapter {
    int numOfTabs;

    public PagerAdapter(FragmentManager fm,int numOfTabs) {
        super(fm);
        this.numOfTabs = numOfTabs;
    }

    @Override
    public android.support.v4.app.Fragment getItem(int position) {
        switch (position){
            case 0:
                SunFragment sunFragment = new SunFragment();
                return sunFragment;
            case 1:
                MoonFragment moonFragment = new MoonFragment();
                return moonFragment;
            case 2:
                WeatherFragment weatherFragment = new WeatherFragment();
                return weatherFragment;
            case 3:
                ForecastFragment forecastFragment = new ForecastFragment();
                return forecastFragment;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return numOfTabs;
    }



}
