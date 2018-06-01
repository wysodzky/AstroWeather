package com.example.bartek.astroweather.data;

import java.util.Objects;

import io.realm.RealmObject;

/**
 * Created by Bartek on 2018-06-01.
 */

public class FavouriteLocation extends RealmObject {
    private String location;

    public FavouriteLocation(String location){
        this.location = location;
    }

    public FavouriteLocation(){}

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Override
    public String toString(){
        return location;
    }

}
