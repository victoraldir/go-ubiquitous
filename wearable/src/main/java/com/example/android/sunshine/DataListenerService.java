package com.example.android.sunshine;

import android.content.Intent;
import android.os.Handler;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.wearable.DataEvent;
import com.google.android.gms.wearable.DataEventBuffer;
import com.google.android.gms.wearable.DataMapItem;
import com.google.android.gms.wearable.WearableListenerService;

import org.json.JSONObject;

public class DataListenerService extends WearableListenerService {


    private static final String TAG = DataListenerService.class.getSimpleName();
    private Handler mHandler;

    public static final String EXTRA_RESULT = TAG + ".RESULT";
    public static final String ACTION_NEW_DATA = TAG + ".ACTION_NEW_DATA";
    private final String CONTENT_KEY = "myKey";

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate called");
        mHandler = new Handler();
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getApplicationContext(),"onCreate called",Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onDataChanged(DataEventBuffer dataEventBuffer) {
        for (final DataEvent event : dataEventBuffer) {
            if (event.getType() == DataEvent.TYPE_CHANGED) {

                DataMapItem dataMapItem = DataMapItem.fromDataItem(event.getDataItem());
                final String content = dataMapItem.getDataMap().getString(CONTENT_KEY);

                Intent localIntent = new Intent(ACTION_NEW_DATA);
                localIntent.putExtra(EXTRA_RESULT, content);

                Log.d(TAG, "onDataChanged: " + content);

                LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(localIntent);
            }
        }

    }


}
