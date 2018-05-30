package com.example.bartek.astroweather.service;

import com.example.bartek.astroweather.data.Channel;

/**
 * Created by Bartek on 2018-05-21.
 */

public interface WeatherServiceCallback {
    void serviceSuccess(Channel channel);
    void serviceFailure(Exception exception);
}
