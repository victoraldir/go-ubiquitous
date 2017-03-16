package com.example.android.sunshine.utilities;

import android.content.Context;
import android.database.Cursor;
import android.widget.Toast;

import com.example.android.sunshine.MainActivity;
import com.example.android.sunshine.data.WeatherContract;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by victoraldir on 16/03/2017.
 */

public class WearableWeatherUtils {

    public static String createWeatherJsonString(Context context, Cursor data){
        JSONObject obj = new JSONObject();
        data.moveToFirst();
        try {

            int lowInCelsius = data.getInt(MainActivity.INDEX_WEATHER_MIN_TEMP);
            int highInCelsius = data.getInt(MainActivity.INDEX_WEATHER_MAX_TEMP);
            int weatherId = data.getInt(MainActivity.INDEX_WEATHER_CONDITION_ID);

            String highString = SunshineWeatherUtils.formatTemperature(context, highInCelsius);
            String lowString = SunshineWeatherUtils.formatTemperature(context, lowInCelsius);


            obj.put(WeatherContract.WeatherEntry.COLUMN_MAX_TEMP,highString);
            obj.put(WeatherContract.WeatherEntry.COLUMN_MIN_TEMP,lowString);

            obj.put("eta",System.currentTimeMillis()); //JUST FOR DEBUGGING
            obj.put(WeatherContract.WeatherEntry.COLUMN_WEATHER_ID,weatherId);

        } catch (JSONException e) {
            Toast.makeText(context,e.getMessage(),Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }

        return obj != null ? obj.toString() : "";
    }

}
