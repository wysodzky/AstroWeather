package com.example.bartek.astroweather.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by Bartek on 2018-06-03.
 */

public class NetworkChangeReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        if(!checkInternet(context)){
            Toast.makeText(context,"Network is not available",Toast.LENGTH_LONG).show();
        }
    }

    boolean checkInternet(Context context){
        ServiceManager serviceManager = new ServiceManager(context);
        if(serviceManager.isNetworkAvailable()){
            return true;
        }else {
            return false;
        }
    }
}
