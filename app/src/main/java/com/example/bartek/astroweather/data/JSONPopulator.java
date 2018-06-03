package com.example.bartek.astroweather.data;

import org.json.JSONObject;

/**
 * Created by Bartek on 2018-05-21.
 */

public interface  JSONPopulator {
    void populate(JSONObject data);

    JSONObject toJSON();
}
