package service;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.widget.Toast;

import com.vic.servicetest.R;

public class MyService extends Service {

//    private String currentMusic;
    private MusicBinder musicBinder = new MusicBinder();
    private MediaPlayer mediaPlayer;

    public class MusicBinder extends Binder {
        public void startMusic(){
            if(mediaPlayer!=null){
                mediaPlayer.start();
                Toast.makeText(MyService.this,"Now playing",Toast.LENGTH_SHORT)
                        .show();
            }

        }
        public void stopMusic(){
            if(mediaPlayer!=null){
                mediaPlayer.pause();
                Toast.makeText(MyService.this,"Music stop",Toast.LENGTH_SHORT)
                        .show();
            }

        }
    }

    public MyService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return musicBinder;
    }
    @Override
    public void onCreate(){
        super.onCreate();
        mediaPlayer = MediaPlayer.create(this,R.raw.song);

    }
    @Override
    public int onStartCommand(Intent intent,int flags,int startId){
        return super.onStartCommand(intent,flags,startId);
    }
    @Override
    public void onDestroy(){
        if(mediaPlayer !=null){
            mediaPlayer.stop();
            mediaPlayer.release();
        }

    }
}
