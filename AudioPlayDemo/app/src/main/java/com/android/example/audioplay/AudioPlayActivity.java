package com.android.example.audioplay;

import android.app.Activity;
import android.content.Context;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class AudioPlayActivity extends Activity {
    private Context context;
    MediaPlayer mp;
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        context = getApplicationContext();
        mp = MediaPlayer.create(context, R.raw.test);
        Button play = (Button)findViewById(R.id.play);
        play.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
		        if(!mp.isPlaying()){
		        	mp.start();
		        }
			}
		});
        Button pause = (Button)findViewById(R.id.pause);
        pause.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
		        mp.pause();
			}
		});
    }
}