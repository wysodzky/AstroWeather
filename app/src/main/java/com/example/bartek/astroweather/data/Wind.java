package com.example.bartek.astroweather.data;

import org.json.JSONObject;

/**
 * Created by Bartek on 2018-05-30.
 */

public class Wind implements JSONPopulator {
    private String chill;
    private String direction;
    private String speed;

    public String getChill() {
        return chill;
    }

    public String getDirection() {
        return direction;
    }

    public String getSpeed() {
        return speed;
    }

    @Override
    public void populate(JSONObject data) {
        chill = data.optString("chill");
        direction = data.optString("direction");
        speed = data.optString("speed");
    }
}
