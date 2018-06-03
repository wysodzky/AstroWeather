package com.example.bartek.astroweather.service;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by Bartek on 2018-06-03.
 */

public class ServiceManager {
    Context context;

    public ServiceManager(Context base){
        this.context = base;
    }

    public boolean isNetworkAvailable(){
        ConnectivityManager cm = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();

    }
}
