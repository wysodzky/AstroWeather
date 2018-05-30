package com.example.bartek.astroweather.fragments;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.astrocalculator.AstroCalculator;
import com.example.bartek.astroweather.AstroUpdate;
import com.example.bartek.astroweather.Manager;
import com.example.bartek.astroweather.R;

/**
 * Created by Bartek on 2018-04-20.
 */

public class MoonFragment extends Fragment implements AstroUpdate {
    AstroCalculator astroCalculator;
    View view;
    TextView longitude;
    TextView latitude;
    TextView moonRise;
    TextView moonSet;
    TextView newMoon;
    TextView fullMoon;
    TextView illumination;
    TextView age;
    Manager manager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        view = inflater.inflate(R.layout.fragment_moon,container,false);

        longitude = (TextView)view.findViewById(R.id.longitude);
        latitude = (TextView)view.findViewById(R.id.latitude);
        moonRise = (TextView)view.findViewById(R.id.moonRise);
        moonSet = (TextView)view.findViewById(R.id.moonSet);
        newMoon = (TextView)view.findViewById(R.id.newMoon);
        fullMoon = (TextView)view.findViewById(R.id.fullMoon);
        illumination = (TextView)view.findViewById(R.id.illumination);
        age = (TextView)view.findViewById(R.id.age);


        manager = Manager.getInstance();
        manager.registerForUpdates(this);
        initValues();



        return view;
    }

    public void initValues(){
        astroCalculator = manager.getAstroCalculator();

        longitude.setText(String.valueOf(astroCalculator.getLocation().getLongitude()));
        latitude.setText(String.valueOf(astroCalculator.getLocation().getLatitude()));
        moonRise.setText(String.format("%02d",astroCalculator.getMoonInfo().getMoonrise().getHour()) + ":"+ String.format("%02d",astroCalculator.getMoonInfo().getMoonrise().getMinute()));
        moonSet.setText(String.format("%02d",astroCalculator.getMoonInfo().getMoonset().getHour()) + ":"+ String.format("%02d",astroCalculator.getMoonInfo().getMoonset().getMinute()));

        newMoon.setText(astroCalculator.getMoonInfo().getNextNewMoon().getDay() +"."+(String.format("%02d",astroCalculator.getMoonInfo().getNextNewMoon().getMonth()+1))+"."+astroCalculator.getMoonInfo().getNextNewMoon().getYear());

        fullMoon.setText(String.format("%02d",astroCalculator.getMoonInfo().getNextFullMoon().getHour()) +":" + String.format("%02d",astroCalculator.getMoonInfo().getNextFullMoon().getMinute()));
        illumination.setText(String.valueOf(astroCalculator.getMoonInfo().getIllumination() * 100).substring(0,5) + "%");
        age.setText(String.valueOf(astroCalculator.getMoonInfo().getAge()).substring(0,6));


    }

    @Override
    public void onSettingsUpdate(){
        initValues();
    }

    public void onDestroy(){
        manager.unregisterForUpdates(this);
        super.onDestroy();
    }
}
