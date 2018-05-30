package com.example.bartek.astroweather.fragments;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bartek.astroweather.AstroUpdate;
import com.example.bartek.astroweather.Manager;
import com.example.bartek.astroweather.R;
import com.example.bartek.astroweather.data.Channel;
import com.example.bartek.astroweather.data.Condition;
import com.example.bartek.astroweather.data.Units;
import com.example.bartek.astroweather.service.WeatherServiceCallback;
import com.example.bartek.astroweather.service.YahooWeatherService;

/**
 * Created by Bartek on 2018-05-30.
 */

public class ForecastFragment extends Fragment implements AstroUpdate,WeatherServiceCallback {
    private ImageView forecastIconImageView;
    private TextView temperatureHighTextView;
    private TextView temperatureLowTextView;
    private TextView locationTextView;
    private TextView conditionTextView;

    Manager manager;



    private YahooWeatherService service;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.forecast_fragment,container,false);

        forecastIconImageView = (ImageView)view.findViewById(R.id.forecastIconImageView);
        temperatureHighTextView = (TextView)view.findViewById(R.id.temperatureHighTextView);
        temperatureLowTextView = (TextView)view.findViewById(R.id.temperatureLowTextView);
        locationTextView = (TextView)view.findViewById(R.id.locationTextView);
        conditionTextView = (TextView)view.findViewById(R.id.conditionTextView);
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
        if(isAdded()) {
            Condition[] forecast = channel.getItem().getForecast();
            Units units = channel.getUnits();

            int weatherIconId = getResources().getIdentifier("drawable/icon_" + forecast[0].getCode(), null, getActivity().getPackageName());
            forecastIconImageView.setImageResource(weatherIconId);
            temperatureHighTextView.setText(forecast[1].getHighTemperature() + "\u00b0" + units.getTemperature());
        }
    }

    @Override
    public void serviceFailure(Exception exception) {
        service.refreshWeather(service.getLocation());
    }
}
