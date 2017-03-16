package com.example.android.sunshine;

import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.wearable.DataEvent;
import com.google.android.gms.wearable.DataEventBuffer;
import com.google.android.gms.wearable.DataMap;
import com.google.android.gms.wearable.DataMapItem;
import com.google.android.gms.wearable.Node;
import com.google.android.gms.wearable.WearableListenerService;

public class DataListenerService extends WearableListenerService {


    private static final String TAG = DataListenerService.class.getSimpleName();
    private Handler mHandler;

    public static final String EXTRA_RESULT = TAG + ".RESULT";
    public static final String ACTION_NEW_DATA = TAG + ".ACTION_NEW_DATA";
    private final String CONTENT_KEY = "myWeather";

    @Override
    public void onCreate() {
        super.onCreate();
        mHandler = new Handler();
        //sendToast("DataListenerService.onCreate");
        Log.d(TAG, "DataListenerService.onCreate called");
    }


    @Override
    public void onPeerConnected(Node node) {
        super.onPeerConnected(node);
        //sendToast("DataListenerService.onPeerConnected" + node.getDisplayName());
    }

    @Override
    public boolean bindService(Intent service, ServiceConnection conn, int flags) {

        //sendToast("DataListenerService.bindService");
        return super.bindService(service, conn, flags);
    }

    @Override
    public void onDataChanged(DataEventBuffer dataEventBuffer) {

        //Toast.makeText(getApplicationContext(),"DataListenerService.onDataChanged",Toast.LENGTH_LONG).show();
        for (final DataEvent event : dataEventBuffer) {
            if (event.getType() == DataEvent.TYPE_CHANGED) {

                DataMap dataMap = DataMapItem.fromDataItem(event.getDataItem()).getDataMap();
                final String content = dataMap.getString(CONTENT_KEY);

                //sendToast("content: " + content);

                Intent localIntent = new Intent(ACTION_NEW_DATA);
                localIntent.putExtra(EXTRA_RESULT, content);

                Log.d(TAG, "onDataChanged: " + content);

                LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(localIntent);
            }
        }

    }

//    private void sendToast(final String msg){
//        mHandler.post(new Runnable() {
//            @Override
//            public void run() {
//                Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_LONG).show();
//            }
//        });
//    }

}
