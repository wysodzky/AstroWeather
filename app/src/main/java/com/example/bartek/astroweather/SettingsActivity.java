package com.example.bartek.astroweather;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.astrocalculator.AstroCalculator;
import com.example.bartek.astroweather.service.YahooWeatherService;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Bartek on 2018-04-24.
 */

public class SettingsActivity extends AppCompatActivity {
    private Spinner refresh;
    private EditText newLongitude;
    private EditText newLatitude;
    private EditText location;
    private Manager manager;



    @Override
    protected void onCreate(Bundle savedInstaneState){
        super.onCreate(savedInstaneState);

        setContentView(R.layout.activity_settings);
        manager = Manager.getInstance();
        initSpinner();
        initValues();

    }


    List<String> getIntervalNames(){
        List<String> intervalNames = new ArrayList<>();
        for(TimeValues value : TimeValues.values()){
            intervalNames.add(value.name());
        }
        return intervalNames;
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
                Toast.makeText(SettingsActivity.this,"Time interval changed",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void initValues(){


        newLatitude = (EditText)findViewById(R.id.newLatitude);
        newLongitude = (EditText)findViewById(R.id.newLogitude);
        newLatitude.setText(String.valueOf(manager.getLocation().getLatitude()));
        newLongitude.setText(String.valueOf(manager.getLocation().getLongitude()));
        location = (EditText)findViewById(R.id.newLocation);
        location.setText(String.valueOf(YahooWeatherService.getLocation()));
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
            Toast.makeText(SettingsActivity.this,"Coordinates changed",Toast.LENGTH_SHORT).show();


        }catch (Exception ParseException){
            Toast.makeText(SettingsActivity.this,"Incorrect Data",Toast.LENGTH_SHORT).show();
        }
    }
}
