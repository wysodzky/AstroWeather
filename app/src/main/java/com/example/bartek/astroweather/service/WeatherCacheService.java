package com.example.bartek.astroweather.service;

import android.content.Context;
import android.os.AsyncTask;
import android.support.constraint.solver.Cache;

import com.example.bartek.astroweather.R;
import com.example.bartek.astroweather.data.Channel;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.json.JSONObject;

/**
 * Created by Bartek on 2018-06-03.
 */

public class WeatherCacheService {
    private Context context;
    private Exception error;
    private final String CACHED_WEATHER_FILE = "weather.data";
    private WeatherCacheService weatherCacheService;

    public WeatherCacheService(Context context){
        this.context = context;
    }

    public void save(Channel channel){
        new AsyncTask<Channel,Void, Void>(){

            @Override
            protected Void doInBackground(Channel... channels) {
                FileOutputStream outputStream;

                try {
                    outputStream = context.openFileOutput(CACHED_WEATHER_FILE,Context.MODE_PRIVATE);
                    outputStream.write(channels[0].toJSON().toString().getBytes());
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }
        }.execute(channel);
    }
    public void load(final WeatherServiceCallback listener){
        new AsyncTask<WeatherServiceCallback,Void,Channel>(){
            private WeatherServiceCallback weatherServiceCallback;
            @Override
            protected Channel doInBackground(WeatherServiceCallback... weatherServiceCallbacks) {
                weatherServiceCallback = weatherServiceCallbacks[0];

                try {
                    FileInputStream inputStream = context.openFileInput(CACHED_WEATHER_FILE);
                    StringBuilder cache = new StringBuilder();
                    int content;
                    while((content = inputStream.read()) != -1){
                        cache.append((char) content);
                    }
                    inputStream.close();

                    JSONObject jsonCache = new JSONObject(cache.toString());
                    Channel channel = new Channel();
                    channel.populate(jsonCache);
                    return channel;
                } catch (FileNotFoundException e) {
                    error = new CacheException("File not found!");
                }catch (Exception e){
                    error = e;
                }
                return null;
            }
            @Override
            protected void onPostExecute(Channel channel){
                if(channel == null && error != null){
                    weatherServiceCallback.serviceFailure(error);
                }else {
                    weatherServiceCallback.serviceSuccess(channel);
                }
            }


        }.execute(listener);
    }

    private class CacheException extends Exception{
        CacheException(String detailMessage){
            super(detailMessage);
        }
    }
}
