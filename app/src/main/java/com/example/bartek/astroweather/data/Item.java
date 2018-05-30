package com.example.bartek.astroweather.data;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Bartek on 2018-05-21.
 */

public class Item implements JSONPopulator {
    private Condition condition;

    private Condition[] forecast;

    public Condition[] getForecast() {
        return forecast;
    }

    public Condition getCondition() {
        return condition;
    }


    @Override
    public void populate(JSONObject data) {
        condition = new Condition();
        condition.populate(data.optJSONObject("condition"));
        JSONArray forecastData = data.optJSONArray("forecast");

        forecast = new Condition[forecastData.length()];

        for (int i = 0; i < forecastData.length(); i++) {
            forecast[i] = new Condition();
            try {
                forecast[i].populate(forecastData.getJSONObject(i));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }
}
