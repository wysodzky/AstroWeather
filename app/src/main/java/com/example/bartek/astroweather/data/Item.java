package com.example.bartek.astroweather.data;

import org.json.JSONObject;

/**
 * Created by Bartek on 2018-05-21.
 */

public class Item implements JSONPopulator {
    private Condition condition;



    public Condition getCondition() {
        return condition;
    }



    @Override
    public void populate(JSONObject data) {
        condition = new Condition();
        condition.populate(data.optJSONObject("condition"));


    }
}
