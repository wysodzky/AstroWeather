package com.example.bartek.astroweather.data;

import org.json.JSONObject;

/**
 * Created by Bartek on 2018-05-30.
 */

public class Atmosphere implements JSONPopulator {
    private String humidity;
    private String pressure;
    private String visibility;

    public String getHumidity() {
        return humidity;
    }

    public String getPressure() {
        return pressure;
    }

    public String getVisibility() {
        return visibility;
    }


    @Override
    public void populate(JSONObject data) {
        humidity = data.optString("humidity");
        pressure = data.optString("pressure");
        visibility = data.optString("visibility");

    }
}
