package com.vic.networkchange_broadcastreceiver;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

    private IntentFilter intentFilter;
    private NetworkChangeReceiver networkChangeReceiver;
    private TextView status,face;
    private Button sendBroadcast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        status = (TextView)findViewById(R.id.network_status);
        face = (TextView)findViewById(R.id.smileface);
        sendBroadcast = (Button)findViewById(R.id.sendBroadcast);

        intentFilter = new IntentFilter();
        intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        networkChangeReceiver = new NetworkChangeReceiver();
        registerReceiver(networkChangeReceiver,intentFilter);

        sendBroadcast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent("hi");
                sendBroadcast(intent);
            }
        });

    }
    @Override
    protected void onDestroy(){
        super.onDestroy();
        unregisterReceiver(networkChangeReceiver);
    }
    class NetworkChangeReceiver extends BroadcastReceiver{
        @Override
        public void onReceive(Context context,Intent intent){
            ConnectivityManager connectionManager =
                    (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connectionManager.getActiveNetworkInfo();
            if(networkInfo != null && networkInfo.isAvailable()){
                status.setText("Network Status: Available");
                face.setText(":)");
                Toast.makeText(context,"Network is available",Toast.LENGTH_LONG).show();
            }
            else{
                status.setText("Network Status: Unavailable");
                face.setText(":(");
                Toast.makeText(context,"Network is unavailable",Toast.LENGTH_LONG).show();
            }
        }
    }
}
