package com.example.bartek.astroweather.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bartek.astroweather.Manager;
import com.example.bartek.astroweather.R;

/**
 * Created by Bartek on 2018-05-30.
 */

public class ForecastFragment extends Fragment {
    private ImageView forecastIconImageView;
    private TextView temperatureHighTextView;
    private TextView temperatureLowTextView;
    private TextView locationTextView;
    private TextView conditionTextView;

    Manager manager = Manager.getInstance();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.forecast_fragment,container,false);

        forecastIconImageView = (ImageView)view.findViewById(R.id.forecastIconImageView);
        temperatureHighTextView = (TextView)view.findViewById(R.id.temperatureHighTextView);
        temperatureLowTextView = (TextView)view.findViewById(R.id.temperatureLowTextView);
        locationTextView = (TextView)view.findViewById(R.id.locationTextView);
        conditionTextView = (TextView)view.findViewById(R.id.conditionTextView);
        return view;
    }

    void initValues(){

    }
}
