package com.example.bartek.astroweather.fragments;

import android.support.v4.app.Fragment;
import android.os.Bundle;
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
import com.example.bartek.astroweather.data.Condition;
import com.example.bartek.astroweather.data.Item;
import com.example.bartek.astroweather.data.Units;
import com.example.bartek.astroweather.service.WeatherCacheService;
import com.example.bartek.astroweather.service.WeatherServiceCallback;
import com.example.bartek.astroweather.service.YahooWeatherService;

/**
 * Created by Bartek on 2018-05-30.
 */

public class ForecastFragment extends Fragment implements AstroUpdate,WeatherServiceCallback {

     private String unitValue = "\u00b0";
    private WeatherCacheService weatherCacheService;

    //main weather
    private ImageView forecastIconImageView;
    private TextView temperatureHighTextView;
    private TextView locationTextView;
    private TextView conditionTextView;

    //1st day

    private ImageView image_1;
    private TextView dayLabel_1;
    private TextView hTemp_1;
    private TextView lTemp_1;



    //2 day

    private ImageView image_2;
    private TextView dayLabel_2;
    private TextView hTemp_2;
    private TextView lTemp_2;

    //3 day

    private ImageView image_3;
    private TextView dayLabel_3;
    private TextView hTemp_3;
    private TextView lTemp_3;



    //4 day

    private ImageView image_4;
    private TextView dayLabel_4;
    private TextView hTemp_4;
    private TextView lTemp_4;


    //5 day

    private ImageView image_5;
    private TextView dayLabel_5;
    private TextView hTemp_5;
    private TextView lTemp_5;


    Manager manager;



    private YahooWeatherService service;


    void initValues(View view){
        forecastIconImageView = (ImageView)view.findViewById(R.id.forecastIconImageView);
        temperatureHighTextView = (TextView)view.findViewById(R.id.temperatureHighTextView);
        locationTextView = (TextView)view.findViewById(R.id.locationTextView);
        conditionTextView = (TextView)view.findViewById(R.id.conditionTextView);


        image_1 = (ImageView)view.findViewById(R.id.image_1);
        image_2 = (ImageView)view.findViewById(R.id.image_2);
        image_3 = (ImageView)view.findViewById(R.id.image_3);
        image_4 = (ImageView)view.findViewById(R.id.image_4);
        image_5 = (ImageView)view.findViewById(R.id.image_5);


        dayLabel_1 = (TextView)view.findViewById(R.id.dayLabel_1);
        dayLabel_2 = (TextView)view.findViewById(R.id.dayLabel_2);
        dayLabel_3 = (TextView)view.findViewById(R.id.dayLabel_3);
        dayLabel_4 = (TextView)view.findViewById(R.id.dayLabel_4);
        dayLabel_5 = (TextView)view.findViewById(R.id.dayLabel_5);


        hTemp_1 = (TextView)view.findViewById(R.id.hTemp_1);
        hTemp_2 = (TextView)view.findViewById(R.id.hTemp_2);
        hTemp_3 = (TextView)view.findViewById(R.id.hTemp_3);
        hTemp_4 = (TextView)view.findViewById(R.id.hTemp_4);
        hTemp_5 = (TextView)view.findViewById(R.id.hTemp_5);



        lTemp_1 = (TextView)view.findViewById(R.id.lTemp_1);
        lTemp_2 = (TextView)view.findViewById(R.id.lTemp_2);
        lTemp_3 = (TextView)view.findViewById(R.id.lTemp_3);
        lTemp_4 = (TextView)view.findViewById(R.id.lTemp_4);
        lTemp_5 = (TextView)view.findViewById(R.id.lTemp_5);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.forecast_fragment,container,false);
        initValues(view);

        service = new YahooWeatherService(this);
        manager = Manager.getInstance();
        weatherCacheService = new WeatherCacheService(getActivity());
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
            Item item = channel.getItem();
            int weatherIconId = getResources().getIdentifier("drawable/icon_" + item.getCondition().getCode(), null, getActivity().getPackageName());
            forecastIconImageView.setImageResource(weatherIconId);
            temperatureHighTextView.setText(item.getCondition().getTemperature() + unitValue + units.getTemperature());
            conditionTextView.setText(item.getCondition().getDescription());
            locationTextView.setText(channel.getLocation().getCity());



            weatherIconId = getResources().getIdentifier("drawable/icon_" + forecast[1].getCode(), null, getActivity().getPackageName());
            image_1.setImageResource(weatherIconId);
            hTemp_1.setText(forecast[1].getHighTemperature() + unitValue + units.getTemperature());
            lTemp_1.setText(forecast[1].getLowTemperature() + unitValue + units.getTemperature());
            dayLabel_1.setText(forecast[1].getDay());


            weatherIconId = getResources().getIdentifier("drawable/icon_" + forecast[2].getCode(), null, getActivity().getPackageName());
            image_2.setImageResource(weatherIconId);
            hTemp_2.setText(forecast[2].getHighTemperature() + unitValue + units.getTemperature());
            lTemp_2.setText(forecast[2].getLowTemperature() + unitValue + units.getTemperature());
            dayLabel_2.setText(forecast[2].getDay());

            weatherIconId = getResources().getIdentifier("drawable/icon_" + forecast[3].getCode(), null, getActivity().getPackageName());
            image_3.setImageResource(weatherIconId);
            hTemp_3.setText(forecast[3].getHighTemperature() + unitValue + units.getTemperature());
            lTemp_3.setText(forecast[3].getLowTemperature() + unitValue + units.getTemperature());
            dayLabel_3.setText(forecast[3].getDay());


            weatherIconId = getResources().getIdentifier("drawable/icon_" + forecast[4].getCode(), null, getActivity().getPackageName());
            image_4.setImageResource(weatherIconId);
            hTemp_4.setText(forecast[4].getHighTemperature() + unitValue + units.getTemperature());
            lTemp_4.setText(forecast[4].getLowTemperature() + unitValue + units.getTemperature());
            dayLabel_4.setText(forecast[4].getDay());


            weatherIconId = getResources().getIdentifier("drawable/icon_" + forecast[5].getCode(), null, getActivity().getPackageName());
            image_5.setImageResource(weatherIconId);
            hTemp_5.setText(forecast[5].getHighTemperature() + unitValue + units.getTemperature());
            lTemp_5.setText(forecast[5].getLowTemperature() + unitValue + units.getTemperature());
            dayLabel_5.setText(forecast[5].getDay());
            weatherCacheService.save(channel);
        }
    }

    @Override
    public void serviceFailure(Exception exception) {
        weatherCacheService.load(this);

    }
}
