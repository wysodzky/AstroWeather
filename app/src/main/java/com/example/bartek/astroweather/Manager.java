package com.example.bartek.astroweather;

import com.astrocalculator.AstroCalculator;
import com.astrocalculator.AstroDateTime;
import android.os.Handler;
import android.widget.Toast;

import java.sql.Time;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;


/**
 * Created by Bartek on 2018-04-24.
 */

public class Manager {

    private static final Manager manager = new Manager();

    private Set<AstroUpdate> subscribers = new HashSet<>();

    private AstroCalculator astroCalculator;
    private AstroCalculator.Location location;
    private AstroDateTime astroDateTime;
    
    private Runnable updateAstro;

    private long timeInterval = 1000*60;
    final android.os.Handler handler = new android.os.Handler();
    public Manager(){


        long deviceDate = System.currentTimeMillis();

        int year = Integer.parseInt(new SimpleDateFormat("yyyy", Locale.ENGLISH).format(deviceDate));
        int month = Integer.parseInt(new SimpleDateFormat("MM", Locale.ENGLISH).format(deviceDate));
        int day = Integer.parseInt(new SimpleDateFormat("dd", Locale.ENGLISH).format(deviceDate));
        int hour = Integer.parseInt(new SimpleDateFormat("hh", Locale.ENGLISH).format(deviceDate));
        int minute = Integer.parseInt(new SimpleDateFormat("mm", Locale.ENGLISH).format(deviceDate));
        int second = Integer.parseInt(new SimpleDateFormat("ss", Locale.ENGLISH).format(deviceDate));
        int timeZoneOffset = 1;




        astroDateTime = new AstroDateTime(year,month,day,hour,
                minute,second,timeZoneOffset,true);
        this.location = new AstroCalculator.Location(51.75,19.46);
        astroCalculator = new AstroCalculator(astroDateTime,location);

        changeCoordinates();
    }


    public void changeCoordinates(){
        updateAstro = new Runnable() {
            @Override
            public void run() {
                update();
                notifySubscribers();
                handler.postDelayed(this,timeInterval);
            }
        };

        handler.post(updateAstro);

    }



    public AstroCalculator getAstroCalculator() {
        return astroCalculator;
    }

    public AstroCalculator.Location getLocation() {
        return location;
    }

    public void update(){
        astroCalculator = new AstroCalculator(getAstroDateTime(),location);
    }

    public AstroDateTime getAstroDateTime() {
        return astroDateTime;
    }

    public void setTimeInterval(long time){
        this.timeInterval = time;
    }

    public long getTimeInterval() {
        return timeInterval;
    }



    public static Manager getInstance(){
        return manager;
    }

    public void setLocation(AstroCalculator.Location location){
        this.location = location;
    }

    public void registerForUpdates(AstroUpdate subscriber){
        subscribers.add(subscriber);
    }



    public void unregisterForUpdates(AstroUpdate subscriber){
        subscribers.remove(subscriber);
    }

    public  void notifySubscribers(){
        for(AstroUpdate subscriber : subscribers){
            subscriber.onSettingsUpdate();
        }
    }

}
