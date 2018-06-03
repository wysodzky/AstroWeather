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
import com.example.bartek.astroweather.service.WeatherCacheService;
import com.example.bartek.astroweather.service.WeatherServiceCallback;
import com.example.bartek.astroweather.service.YahooWeatherService;

public class WeatherFragment extends Fragment implements AstroUpdate, WeatherServiceCallback {

    private ImageView weatherIconImageView;
    private TextView temperatureTextView;
    private TextView conditionTextView;
    private TextView locationTextView;

    Manager manager = Manager.getInstance();


    private TextView humidityTextView;
    private TextView pressureTextView;
    private TextView visibilityTextView;


    private TextView speedTextView;
    private TextView chillTextView;
    private TextView directionTextView;


    private TextView latitudeTextView;
    private TextView longitudeTextView;


    private WeatherCacheService weatherCacheService;


    private YahooWeatherService service;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_weather,container,false);
        weatherIconImageView = (ImageView)view.findViewById(R.id.weatherIconImageView);
        temperatureTextView = (TextView)view.findViewById(R.id.temperatureTextView);
        conditionTextView = (TextView)view.findViewById(R.id.conditionTextView);
        locationTextView = (TextView)view.findViewById(R.id.locationTextView);

        humidityTextView = (TextView)view.findViewById(R.id.humidity);
        pressureTextView = (TextView)view.findViewById(R.id.pressure);
        visibilityTextView = (TextView)view.findViewById(R.id.visibility);

        speedTextView = (TextView)view.findViewById(R.id.speed);
        chillTextView = (TextView)view.findViewById(R.id.chill);
        directionTextView = (TextView)view.findViewById(R.id.direction);

        longitudeTextView = (TextView)view.findViewById(R.id.longitude);
        latitudeTextView = (TextView)view.findViewById(R.id.latitude);


        service = new YahooWeatherService(this);

        weatherCacheService = new WeatherCacheService(getActivity());


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
            locationTextView.setText(channel.getLocation().getCity());
            temperatureTextView.setText(item.getCondition().getTemperature() + "\u00b0" + channel.getUnits().getTemperature());
            conditionTextView.setText(item.getCondition().getDescription());

            pressureTextView.setText(channel.getAtmosphere().getPressure());
            humidityTextView.setText(channel.getAtmosphere().getHumidity());
            visibilityTextView.setText(channel.getAtmosphere().getVisibility());

            speedTextView.setText(channel.getWind().getSpeed());
            chillTextView.setText(channel.getWind().getChill());
            directionTextView.setText(channel.getWind().getDirection());

            longitudeTextView.setText(channel.getItem().getLongitude());
            latitudeTextView.setText(channel.getItem().getLatitude());

            weatherCacheService.save(channel);
        }
    }

    @Override
    public void serviceFailure(Exception exception) {
        weatherCacheService.load(this);
    }
}
