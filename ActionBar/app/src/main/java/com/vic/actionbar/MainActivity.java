package com.vic.actionbar;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.support.v7.app.ActionBar;

public class MainActivity extends AppCompatActivity {

    static final int ID = 0x233;
    NotificationManager nm;
    Button send,cancel;

    ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        actionBar = getSupportActionBar();
//        actionBar = getActionBar();
        actionBar.setLogo(R.mipmap.ic_launcher);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

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
    public void showActionBar(View v)
    {
        actionBar.show();
    }
    public void hideActionBar(View v)
    {
        actionBar.hide();
    }
    public void OtherClick(View v)
    {
        Intent intent = new Intent(this,Other.class);
        startActivity(intent);
    }
    public void ActionViewClick(View v)
    {
        Intent intent = new Intent(this,ActionView.class);
        startActivity(intent);
    }
    public void TabClick(View v)
    {
        Intent intent = new Intent(this,Tab.class);
        startActivity(intent);
    }
}
