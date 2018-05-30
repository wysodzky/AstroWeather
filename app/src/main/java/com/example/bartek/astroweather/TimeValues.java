package com.example.bartek.astroweather;

/**
 * Created by Bartek on 2018-04-28.
 */

public enum TimeValues {
    FIVE_SECONDS(5000),
    TEN_SECONDS(10000),
    THIRTY_SECONDS(30000),
    MINUTE(1000*60),
    FIFTEEN_MINUTES(1000*60*15);

    public final int seconds;

    TimeValues(int seconds){
        this.seconds = seconds;
    }

}
