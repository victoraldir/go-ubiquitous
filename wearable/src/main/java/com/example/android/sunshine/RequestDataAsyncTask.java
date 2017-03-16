package com.example.android.sunshine;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wearable.MessageApi;
import com.google.android.gms.wearable.Node;
import com.google.android.gms.wearable.NodeApi;
import com.google.android.gms.wearable.Wearable;

/**
 * Created by victoraldir on 16/03/2017.
 */

public class RequestDataAsyncTask extends AsyncTask<GoogleApiClient,Void,Void> {

    public static final String TAG = RequestDataAsyncTask.class.getSimpleName();
    public static final String CONFIG_START = "config/start";

    private Context mContext;

    public RequestDataAsyncTask(Context mContext){
        this.mContext = mContext;
    }


    @Override
    protected Void doInBackground(GoogleApiClient... params) {

        GoogleApiClient googleApiClient = params[0];

        NodeApi.GetConnectedNodesResult nodes = Wearable.NodeApi.getConnectedNodes(params[0]).await();
        for (Node node : nodes.getNodes()) {
            MessageApi.SendMessageResult result = Wearable.MessageApi.sendMessage(
                    googleApiClient, node.getId(), CONFIG_START, "".getBytes()).await();
            if (result.getStatus().isSuccess()) {

                //sendToast("run: message successfully sent to");
                Log.d(TAG, "run: message successfully sent to " + node.getDisplayName());

            } else {
                //sendToast("run: error sending message to node");
                Log.e(TAG, "run: error sending message to node " + node.getDisplayName());
            }
        }

        return null;
    }

//    public void sendToast(final String content){
//        new Handler().post(new Runnable() {
//            @Override
//            public void run() {
//                Toast.makeText(mContext,"RequestDataAsyncTask running " + content,Toast.LENGTH_LONG).show();
//            }
//        });
//    }
}
