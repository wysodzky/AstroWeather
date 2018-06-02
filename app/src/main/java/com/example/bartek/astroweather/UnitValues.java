package com.example.bartek.astroweather;

/**
 * Created by Bartek on 2018-06-02.
 */

public enum UnitValues {
    CELSIUS("C"),
    FARENTHEIT("F");

    public final String unit;
    UnitValues(String unit){
        this.unit = unit;
    }
}
