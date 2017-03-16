package com.example.android.sunshine.sync;

/**
 * Created by victoraldir on 16/03/2017.
 */

import android.content.ContentProvider;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.example.android.sunshine.MainActivity;
import com.example.android.sunshine.data.WeatherContract;
import com.example.android.sunshine.utilities.WearableWeatherUtils;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.wearable.DataApi;
import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.PutDataMapRequest;
import com.google.android.gms.wearable.PutDataRequest;
import com.google.android.gms.wearable.Wearable;
import com.google.android.gms.wearable.WearableListenerService;

/**
 * Listen to wear devices update requests.
 */

public class SunshineWearableService extends WearableListenerService implements GoogleApiClient.OnConnectionFailedListener, GoogleApiClient.ConnectionCallbacks {

    public static final String TAG = SunshineWearableService.class.getSimpleName();

    public static final String CONFIG_START = "config/start";
    private GoogleApiClient mGoogleApiClient;
    public static final String[] MAIN_FORECAST_PROJECTION = {
            WeatherContract.WeatherEntry.COLUMN_MAX_TEMP,
            WeatherContract.WeatherEntry.COLUMN_MIN_TEMP,
            WeatherContract.WeatherEntry.COLUMN_WEATHER_ID,
    };


    @Override
    public void onCreate() {
        super.onCreate();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(Wearable.API)
                .addOnConnectionFailedListener(this)
                .addConnectionCallbacks(this)
                .build();
    }

    @Override
    public void onMessageReceived(MessageEvent messageEvent) {
        super.onMessageReceived(messageEvent);

        if(messageEvent.getPath().equals(CONFIG_START)){

            Cursor cursor = null;

            try {
                cursor = getContentResolver().query(WeatherContract.WeatherEntry.CONTENT_TODAY_URI, MAIN_FORECAST_PROJECTION, null, null, null);

                cursor.moveToFirst();

                String jsonContent = WearableWeatherUtils.createWeatherJsonString(getApplication(), cursor);

                sendWeatherWearable(jsonContent);

            }finally {
                if (cursor != null) cursor.close();
            }


        }
    }

    public void sendWeatherWearable(String weatherJson) {

        PutDataMapRequest putDataMapRequest = PutDataMapRequest.create(MainActivity.MY_DATA_PATH);

        putDataMapRequest.getDataMap().putString(MainActivity.CONTENT_KEY,weatherJson);

        PutDataRequest request = putDataMapRequest.asPutDataRequest();

        Wearable.DataApi.putDataItem(mGoogleApiClient,request).setResultCallback(new ResultCallback<DataApi.DataItemResult>() {
            @Override
            public void onResult(@NonNull DataApi.DataItemResult dataItemResult) {
                if(dataItemResult.getStatus().isSuccess()){
                    Toast.makeText(getApplication(),"Message has been delivered to the API successfully",Toast.LENGTH_LONG).show();
                    Log.d(TAG,"Message has been delivered to the API successfully");
                }else{
                    Toast.makeText(getApplication(),"Problem delivering message to the API. Is it connected?",Toast.LENGTH_LONG).show();;
                    Log.d(TAG,"Problem delivering message to the API. Is it connected?");
                }
            }
        });
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }
}
