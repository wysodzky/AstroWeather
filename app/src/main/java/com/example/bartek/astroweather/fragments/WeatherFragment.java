package com.example.bartek.astroweather.fragments;

/**
 * Created by Bartek on 2018-05-20.
 */
import android.app.ProgressDialog;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bartek.astroweather.AstroUpdate;
import com.example.bartek.astroweather.Manager;
import com.example.bartek.astroweather.R;
import com.example.bartek.astroweather.data.Channel;
import com.example.bartek.astroweather.data.Item;
import com.example.bartek.astroweather.service.WeatherServiceCallback;
import com.example.bartek.astroweather.service.YahooWeatherService;

public class WeatherFragment extends Fragment implements AstroUpdate, WeatherServiceCallback {

    private ImageView weatherIconImageView;
    private TextView temperatureTextView;
    private TextView conditionTextView;
    private TextView locationTextView;
    Manager manager = Manager.getInstance();


    private YahooWeatherService service;
    private ProgressDialog dialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_weather,container,false);
        weatherIconImageView = (ImageView)view.findViewById(R.id.weatherIconImageView);
        temperatureTextView = (TextView)view.findViewById(R.id.temperatureTextView);
        conditionTextView = (TextView)view.findViewById(R.id.conditionTextView);
        locationTextView = (TextView)view.findViewById(R.id.locationTextView);

        service = new YahooWeatherService(this);


        manager = Manager.getInstance();
        manager.registerForUpdates(this);
        service.refreshWeather(service.getLocation());


        return view;
    }





    @Override
    public void onSettingsUpdate() {
        service.refreshWeather(service.getLocation());
    }

    @Override
    public void serviceSuccess(Channel channel) {

        Item item = channel.getItem();

        if(isAdded()) {
            int resourceID = getResources().getIdentifier("drawable/icon_" + channel.getItem().getCondition().getCode(), null, getActivity().getPackageName());

            @SuppressWarnings("depracation")
            Drawable weatherIconDrawable = getResources().getDrawable(resourceID);
            weatherIconImageView.setImageDrawable(weatherIconDrawable);
            locationTextView.setText(service.getLocation());
            temperatureTextView.setText(item.getCondition().getTemperature() + "\u00b0" + channel.getUnits().getTemperature());
            conditionTextView.setText(item.getCondition().getDescription());
        }
    }

    @Override
    public void serviceFailure(Exception exception) {
        service.refreshWeather(service.getLocation());
    }
}
