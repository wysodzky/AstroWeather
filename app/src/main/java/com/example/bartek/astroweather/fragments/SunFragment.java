package com.example.bartek.astroweather.fragments;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.astrocalculator.AstroCalculator;
import com.example.bartek.astroweather.AstroUpdate;
import com.example.bartek.astroweather.Manager;
import com.example.bartek.astroweather.R;

import java.util.Date;


/**
 * Created by Bartek on 2018-04-20.
 */

public class SunFragment extends Fragment implements AstroUpdate {
    AstroCalculator astroCalculator;
    TextView longitude;
    TextView latitude;
    TextView azimuthRise;
    TextView azimuthSet;
    TextView sunrise;
    TextView sunset;
    TextView twilightEvening;
    TextView twilightMorning;
    View view;
    Manager manager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_sun, container, false);

        longitude = (TextView)view.findViewById(R.id.longitude);
        latitude = (TextView)view.findViewById(R.id.latitude);
        azimuthRise = (TextView) view.findViewById(R.id.azimuthRise);
        azimuthSet = (TextView) view.findViewById(R.id.azimuthSet);
        sunrise = (TextView) view.findViewById(R.id.sunrise);
        sunset = (TextView) view.findViewById(R.id.sunset);
        twilightEvening = (TextView) view.findViewById(R.id.twilightEvening);
        twilightMorning = (TextView) view.findViewById(R.id.twilightMorning);

        manager = Manager.getInstance();
        manager.registerForUpdates(this);

        initValues();

        return view;

    }


    public void initValues() {
        astroCalculator = manager.getAstroCalculator();
        longitude.setText(String.valueOf(astroCalculator.getLocation().getLongitude()));
        latitude.setText(String.valueOf(astroCalculator.getLocation().getLatitude()));
        azimuthRise.setText(String.valueOf(astroCalculator.getSunInfo().getAzimuthRise()).substring(0,6));
        sunrise.setText(String.format("%02d",astroCalculator.getSunInfo().getSunrise().getHour()) + ":" + String.format("%02d",astroCalculator.getSunInfo().getSunrise().getMinute()));
        azimuthSet.setText( String.valueOf(astroCalculator.getSunInfo().getAzimuthSet()).substring(0,6));
        sunset.setText(String.format("%02d",astroCalculator.getSunInfo().getSunset().getHour()) + ":" + String.format("%02d",astroCalculator.getSunInfo().getSunset().getMinute()));

        twilightMorning.setText(String.format("%02d",astroCalculator.getSunInfo().getTwilightMorning().getHour())+ ":" + String.format("%02d",astroCalculator.getSunInfo().getTwilightMorning().getMinute()));
        twilightEvening.setText(String.format("%02d",astroCalculator.getSunInfo().getTwilightEvening().getHour()) + ":" + String.format("%02d",astroCalculator.getSunInfo().getTwilightEvening().getMinute()));

    }

    @Override
    public void onSettingsUpdate() {
        initValues();
    }

    public void onDestroy(){
        manager.unregisterForUpdates(this);
        super.onDestroy();
    }
}
