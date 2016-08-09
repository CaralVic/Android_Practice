package com.vic.notification;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {

    static final int ID = 0x233;
    NotificationManager nm;
    Button send,cancel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        send = (Button)findViewById(R.id.send);
        cancel = (Button)findViewById(R.id.cancel);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendNotify();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelNotify();
            }
        });
        nm = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
    }

    public void sendNotify()
    {
        Intent i = new Intent(MainActivity.this,Other.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(MainActivity.this, 0, i, 0);
        Notification notification = new Notification.Builder(this)
                .setAutoCancel(true)
                .setTicker("新消息！！！")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("加薪通知")
                .setContentText("23333333333，恭喜你加薪20%~！")
                .setDefaults(Notification.DEFAULT_SOUND| Notification.DEFAULT_LIGHTS)
                .setContentIntent(pendingIntent)
                .setWhen(System.currentTimeMillis())
                .build();
        nm.notify(ID,notification);
    }
    public void cancelNotify()
    {
        nm.cancel(ID);
    }
}
