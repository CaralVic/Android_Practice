package com.vic.servicetest;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Color;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import service.MyService;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Button startService,stopservice,play,pause;
    private Button sendNotification;
//    private TextView progress;
    private MyService.MusicBinder musicBinder;
//    private Handler handler = new Handler(){
//        @Override
//        public void handleMessage(Message msg){
//            switch (msg.what){
//                case 1:
//                    progress.setText(msg.arg1+"");
//                    break;
//                default:
//                    break;
//            }
//        }
//    };
    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            musicBinder = (MyService.MusicBinder)service;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        play = (Button)findViewById(R.id.play);
        pause = (Button)findViewById(R.id.pause);
//        progress = (TextView)findViewById(R.id.music_progress);
        startService = (Button)findViewById(R.id.start_service);
        stopservice = (Button)findViewById(R.id.stop_service);
        sendNotification = (Button)findViewById(R.id.notification);

        play.setOnClickListener(this);
        pause.setOnClickListener(this);
        startService.setOnClickListener(this);
        stopservice.setOnClickListener(this);
        sendNotification.setOnClickListener(this);

    }

    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.start_service:
                Intent bindIntent = new Intent(this, MyService.class);
                bindService(bindIntent,connection,BIND_AUTO_CREATE);
                break;
            case R.id.stop_service:
                unbindService(connection);
                break;
            case R.id.play:
                if(musicBinder!=null){
                    musicBinder.startMusic();
                }

//                new Thread(new Runnable() {
//                    @Override
//                    public void run() {
//                        while(true)
//                    }
//                })
                break;
            case R.id.pause:
                if(musicBinder!=null){
                    musicBinder.stopMusic();
                }
                break;
            case R.id.notification:
                NotificationManager manager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
//                Notification notification = new Notification(R.mipmap.ic_launcher,"New Message",System.currentTimeMillis());
                Notification.Builder notification = new Notification.Builder(this)
                        .setTicker("New Message Arrived")
                        .setSmallIcon(R.mipmap.ic_launcher);
                Intent intent = new Intent(this,NotificationActivity.class)
                        .setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                PendingIntent pendingIntent = PendingIntent.getActivity(this,0,intent,PendingIntent.FLAG_CANCEL_CURRENT);
                notification.setContentIntent(pendingIntent)
                        .setContentTitle("Message!")
                        .setContentText("Typhoon is coming");
                notification.setVibrate(new long[]{0,1000,1000,1000})
                        .setLights(Color.GREEN,1000,1000);
                Notification m = notification.build();
                m.flags=Notification.FLAG_SHOW_LIGHTS;
                manager.notify(1,m);

                break;
            default:
                break;
        }
    }
}
