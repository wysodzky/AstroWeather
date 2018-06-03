package com.example.bartek.astroweather.data;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * Created by Bartek on 2018-05-21.
 */

public class Item implements JSONPopulator {
    private Condition condition;

    private Condition[] forecast;
    private String latitude;
    private String longitude;
    private String link;
    private JSONArray forecastData;
    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public String getLink() {
        return link;
    }

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

        latitude = data.optString("lat");
        longitude = data.optString("long");
        link = data.optString("link");

        forecastData = data.optJSONArray("forecast");

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

    @Override
    public JSONObject toJSON(){
        JSONObject data = new JSONObject();
        try {

            data.put("link", link);
            data.put("lat", latitude);
            data.put("long", longitude);
            data.put("condition",condition.toJSON());
            JSONArray arr = new JSONArray();
            for(int i=0;i<forecast.length;i++){
                arr.put(forecast[i].toJSON());
            }

            data.put("forecast",arr);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return data;
    }
}
