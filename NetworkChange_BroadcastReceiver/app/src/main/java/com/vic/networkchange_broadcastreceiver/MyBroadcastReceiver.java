package com.vic.networkchange_broadcastreceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by Vic on 7/28/2016.
 */
public class MyBroadcastReceiver extends BroadcastReceiver{
    @Override
    public void onReceive(Context context, Intent intent){
        Toast.makeText(context,"Broadcast was Received successfully",Toast.LENGTH_SHORT)
                .show();
    }

}
