package com.example.bartek.astroweather.data;

import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Bartek on 2018-05-21.
 */

public class Channel implements JSONPopulator {
    private Item item;
    private Units units;
    private Atmosphere atmosphere;
    private Wind wind;


    public Item getItem() {
        return item;
    }

    public Units getUnits() {
        return units;
    }

    public Atmosphere getAtmosphere() {
        return atmosphere;
    }

    public Wind getWind() {
        return wind;
    }



    @Override
    public void populate(JSONObject data) {
        units = new Units();
        units.populate(data.optJSONObject("units"));

        item = new Item();
        item.populate(data.optJSONObject("item"));

        atmosphere = new Atmosphere();
        atmosphere.populate(data.optJSONObject("atmosphere"));

        wind = new Wind();
        wind.populate(data.optJSONObject("wind"));


    }

    @Override
    public JSONObject toJSON(){
        JSONObject data = new JSONObject();

        try {
            data.put("atmosphere",atmosphere.toJSON());
            data.put("wind",wind.toJSON());
            data.put("units",units.toJSON());
            data.put("item",item.toJSON());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return data;
    }
}
