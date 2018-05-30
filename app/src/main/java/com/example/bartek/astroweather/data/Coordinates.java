package com.example.bartek.astroweather.data;

import org.json.JSONObject;

/**
 * Created by Bartek on 2018-05-30.
 */

public class Coordinates implements JSONPopulator {
    private String latitude;
    private String longitude;

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    @Override
    public void populate(JSONObject data) {
        latitude = data.optString("lat");
        longitude = data.optString("long");
    }
}
