package com.example.bartek.astroweather;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.astrocalculator.AstroCalculator;
import com.example.bartek.astroweather.adapter.LocationSpinnerAdapter;
import com.example.bartek.astroweather.data.FavouriteLocation;
import com.example.bartek.astroweather.service.YahooWeatherService;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;

/**
 * Created by Bartek on 2018-04-24.
 */

public class SettingsActivity extends AppCompatActivity {
    private Spinner refresh;
    private EditText newLongitude;
    private EditText newLatitude;
    private EditText location;
    private Manager manager;

    private Spinner locationSpinner;
    private Spinner unitSpinner;
    private Realm realm;
    private LocationSpinnerAdapter adapter;
    private List<FavouriteLocation> listLocations = new ArrayList<>();

    private YahooWeatherService weatherService;



    @Override
    protected void onCreate(Bundle savedInstaneState){
        super.onCreate(savedInstaneState);
        Realm.init(this);
        realm = Realm.getDefaultInstance();
        setContentView(R.layout.activity_settings);
        manager = Manager.getInstance();

        initSpinner();
        initValues();
        initSpinnerLocation();
        initSpinnerUnit();


    }


    List<String> getIntervalNames(){
        List<String> intervalNames = new ArrayList<>();
        for(TimeValues value : TimeValues.values()){
            intervalNames.add(value.name());
        }
        return intervalNames;
    }

    List<String> getUnitNames(){
        List<String> unitNames= new ArrayList<>();
        for(UnitValues value : UnitValues.values()){
            unitNames.add(value.name());
        }
        return unitNames;
    }

    private void setDefaultSpinner(){
        if(manager.getTimeInterval() == 5000){
            refresh.setSelection(0);
        }else if(manager.getTimeInterval() == 10000){
            refresh.setSelection(1);
        }else if(manager.getTimeInterval() == 30000){
            refresh.setSelection(2);
        }else if(manager.getTimeInterval() == 1000*60){
            refresh.setSelection(3);
        }else if(manager.getTimeInterval() == 15*1000*60){
            refresh.setSelection(4);
        }
    }


    private void setDefaultUnitSpinner(){
        if(YahooWeatherService.getTemperatureUnit().equals("C")){
            unitSpinner.setSelection(0);
        }else if(YahooWeatherService.getTemperatureUnit().equals("F")){
            unitSpinner.setSelection(1);
        }
    }


    private void initSpinnerUnit(){
        unitSpinner = (Spinner)findViewById(R.id.unitSpinner);
        ArrayAdapter adapter = new ArrayAdapter(this,R.layout.simple_spinner_item,getUnitNames());
        unitSpinner.setAdapter(adapter);
        setDefaultUnitSpinner();
        unitSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                switch((int) l){
                    case 0:
                        YahooWeatherService.setTemperatureUnit("C");
                        break;
                    case 1:
                        YahooWeatherService.setTemperatureUnit("F");
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }




    private void initSpinner(){
        refresh = (Spinner)findViewById(R.id.refresh);

        ArrayAdapter adapter = new ArrayAdapter(this,R.layout.simple_spinner_item,getIntervalNames());
        refresh.setAdapter(adapter);
        setDefaultSpinner();
        refresh.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                switch ((int) l) {
                    case 0:
                        manager.setTimeInterval(5000);
                        break;
                    case 1:
                        manager.setTimeInterval(10000);
                        break;
                    case 2:
                        manager.setTimeInterval(30000);
                        break;
                    case 3:
                        manager.setTimeInterval(1000*60);
                        break;
                    case 4:
                        manager.setTimeInterval(15*1000*60);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }


    void initSpinnerLocation(){

        refreshDataFromDatabase();
        adapter = new LocationSpinnerAdapter(this,android.R.layout.simple_spinner_item,listLocations);
        locationSpinner.setAdapter(adapter);
        locationSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                FavouriteLocation favouriteLocation = adapter.getItem(i);
                location.setText(favouriteLocation.getLocation());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    void refreshDataFromDatabase(){
        RealmQuery query = realm.where(FavouriteLocation.class);
        RealmResults results = query.findAll();
        listLocations = new ArrayList<>();
        for(int i=0;i<results.size();i++){
            FavouriteLocation item = (FavouriteLocation) results.get(i);
            listLocations.add(item);
        }
    }

    private void initValues(){


        newLatitude = (EditText)findViewById(R.id.newLatitude);

        newLongitude = (EditText)findViewById(R.id.newLogitude);
        newLatitude.setText(String.valueOf(manager.getLocation().getLatitude()));
        newLongitude.setText(String.valueOf(manager.getLocation().getLongitude()));
        location = (EditText)findViewById(R.id.newLocation);
        location.setText(String.valueOf(YahooWeatherService.getLocation()));
        locationSpinner = (Spinner)findViewById(R.id.citySpinner);

    }


    public void saveSettings(View view){
        try{

            double nLatitude = Double.parseDouble(newLatitude.getText().toString());
            double nLongitude = Double.parseDouble(newLongitude.getText().toString());

            if((nLatitude > 90 || nLatitude < -90) || (nLongitude < 0 || nLatitude > 180)){
                throw new Exception();
            }


            YahooWeatherService.setLocation(location.getText().toString());
            manager.setLocation(new AstroCalculator.Location(nLatitude,nLongitude));
            manager.changeCoordinates();
            Toast.makeText(SettingsActivity.this,"Settings changed",Toast.LENGTH_SHORT).show();


        }catch (Exception ParseException){
            Toast.makeText(SettingsActivity.this,"Incorrect Data",Toast.LENGTH_SHORT).show();
        }
    }

    public void addLocation(View view){
        final FavouriteLocation favouriteLocation = new FavouriteLocation(location.getText().toString());

        if(adapter.checkIfExists(favouriteLocation.getLocation())){
            Toast.makeText(this,"This location is in database",Toast.LENGTH_SHORT).show();
        }else {

                            realm.beginTransaction();
                            FavouriteLocation itemInDb = realm.createObject(FavouriteLocation.class);
                            itemInDb.setLocation(favouriteLocation.getLocation());
                            realm.commitTransaction();
                            showToastAdded(favouriteLocation);

                            initSpinnerLocation();


        }
    }

    void showToastAdded(FavouriteLocation favouriteLocation){
        Toast.makeText(this, "Added Location " + favouriteLocation.getLocation(), Toast.LENGTH_SHORT).show();
    }
}
