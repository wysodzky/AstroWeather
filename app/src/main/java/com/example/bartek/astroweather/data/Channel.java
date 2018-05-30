package com.example.bartek.astroweather.data;

import android.content.Context;

import org.json.JSONObject;

/**
 * Created by Bartek on 2018-05-21.
 */

public class Channel implements JSONPopulator {
    private Item item;
    private Units units;
    private Atmosphere atmosphere;
    private Wind wind;
    private Coordinates coordinates;

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

    public Coordinates getCoordinates() {
        return coordinates;
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

        coordinates = new Coordinates();
        coordinates.populate(data.optJSONObject("item"));
    }
}
