package com.example.android.sunshine;

import android.util.Log;

/**
 * Created by victoraldir on 16/03/2017.
 */

public class SunshineWeatherUtils {

    private static final String TAG = SunshineWeatherUtils.class.getSimpleName();

    public static int getSmallArtResourceIdForWeatherCondition(int weatherId) {

        /*
         * Based on weather code data for Open Weather Map.
         */
        if (weatherId >= 200 && weatherId <= 232) {
            return R.mipmap.ic_storm;
        } else if (weatherId >= 300 && weatherId <= 321) {
            return R.mipmap.ic_light_rain;
        } else if (weatherId >= 500 && weatherId <= 504) {
            return R.mipmap.ic_rain;
        } else if (weatherId == 511) {
            return R.mipmap.ic_snow;
        } else if (weatherId >= 520 && weatherId <= 531) {
            return R.mipmap.ic_rain;
        } else if (weatherId >= 600 && weatherId <= 622) {
            return R.mipmap.ic_snow;
        } else if (weatherId >= 701 && weatherId <= 761) {
            return R.mipmap.ic_fog;
        } else if (weatherId == 761 || weatherId == 771 || weatherId == 781) {
            return R.mipmap.ic_storm;
        } else if (weatherId == 800) {
            return R.mipmap.ic_clear;
        } else if (weatherId == 801) {
            return R.mipmap.ic_light_clouds;
        } else if (weatherId >= 802 && weatherId <= 804) {
            return R.mipmap.ic_cloudy;
        } else if (weatherId >= 900 && weatherId <= 906) {
            return R.mipmap.ic_storm;
        } else if (weatherId >= 958 && weatherId <= 962) {
            return R.mipmap.ic_storm;
        } else if (weatherId >= 951 && weatherId <= 957) {
            return R.mipmap.ic_clear;
        }

        Log.e(TAG, "Unknown Weather: " + weatherId);
        return R.mipmap.ic_storm;
    }
}
