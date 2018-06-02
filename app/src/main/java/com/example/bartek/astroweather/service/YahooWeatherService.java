package com.example.bartek.astroweather.service;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.renderscript.ScriptGroup;

import com.example.bartek.astroweather.data.Channel;
import com.example.bartek.astroweather.fragments.WeatherFragment;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;

import java.io.InputStream;
import java.io.InputStreamReader;

import java.net.URL;
import java.net.URLConnection;


/**
 * Created by Bartek on 2018-05-21.
 */

public class YahooWeatherService {

    private WeatherServiceCallback callback;
    private static String location = "Lodz";



    private Exception error;
    private static String temperatureUnit = "C";



    public YahooWeatherService(WeatherServiceCallback callback) {
        this.callback = callback;
    }


    public static String getTemperatureUnit() {
        return temperatureUnit;

    }

    public static void setTemperatureUnit(String temperatureUnit) {
        YahooWeatherService.temperatureUnit = temperatureUnit;
    }

    public static String getLocation() {
        return location;
    }

    public static void setLocation(String l) {
        location = l;
    }

    public void refreshWeather(final String l) {
        this.location = l;
        new AsyncTask<String, Void, String>() {
            @Override
            protected String doInBackground(String... strings) {
                String unit = getTemperatureUnit().equalsIgnoreCase("f") ? "f" : "c";
                String YQL = String.format("select * from weather.forecast where woeid in (select woeid from geo.places(1) where text=\"%s\") and u='" + unit + "'", location);
                String endpoint = String.format("https://query.yahooapis.com/v1/public/yql?q=%s&format=json", Uri.encode(YQL));
                try {
                    URL url = new URL(endpoint);
                    InputStream inputStream = null;
                    URLConnection connection = url.openConnection();

                    inputStream = connection.getInputStream();


                    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

                    StringBuilder result = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        result.append(line);
                    }
                    return result.toString();

                } catch (Exception e) {
                    error = e;
                }
                return null;
            }

            @Override
            protected void onPostExecute(String s) {

                if (s == null && error != null) {
                    callback.serviceFailure(error);
                    return;
                }
                try {
                    JSONObject data = new JSONObject(s);
                    JSONObject queryResults = data.optJSONObject("query");
                    int count = queryResults.optInt("count");
                    if (count == 0) {
                        callback.serviceFailure(new LocationWeatherException("No weather information found" + location));
                        return;
                    }
                    Channel channel = new Channel();
                    channel.populate(queryResults.optJSONObject("results").optJSONObject("channel"));
                    callback.serviceSuccess(channel);
                } catch (JSONException e) {
                    callback.serviceFailure(e);
                }
            }
        }.execute(location);
    }

    public class LocationWeatherException extends Exception {

        public LocationWeatherException(String detailMessage) {
            super(detailMessage);
        }
    }

}
