package com.example.bartek.astroweather.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.bartek.astroweather.data.FavouriteLocation;

import java.util.List;

/**
 * Created by Bartek on 2018-06-01.
 */

public class LocationSpinnerAdapter extends ArrayAdapter<FavouriteLocation> {
    private Context context;
    private FavouriteLocation[] values;

    public LocationSpinnerAdapter(Context context, int textViewResourceId, List<FavouriteLocation> values){
        super(context,textViewResourceId,values);

        this.context = context;
        this.values = values.toArray(new FavouriteLocation[values.size()]);

    }

    @Override
    public int getCount(){
        return values.length;
    }


    @Override
    public FavouriteLocation getItem(int position){
        return values[position];
    }

    @Override
    public long getItemId(int position){
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        TextView label = (TextView)super.getView(position,convertView,parent);
        label.setTextColor(Color.BLACK);

        label.setText(values[position].getLocation());

        return label;
    }

    @Override
    public View getDropDownView(int position,View convertView,ViewGroup parent){
        TextView label =(TextView)super.getDropDownView(position,convertView,parent);
        label.setTextColor(Color.BLACK);
        label.setText(values[position].getLocation());

        return label;
    }

    public boolean checkIfExists(String text){
        for(FavouriteLocation val : values){
            if(val.getLocation().toUpperCase().equals(text.toUpperCase())){
                return true;
            }
        }
        return false;
    }


}
