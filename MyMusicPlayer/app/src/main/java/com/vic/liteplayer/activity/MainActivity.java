package com.vic.liteplayer.activity;

import android.app.Service;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.audiofx.Visualizer;
import android.os.Handler;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.vic.liteplayer.R;
import com.vic.liteplayer.method.LrcContent;
import com.vic.liteplayer.method.Mp3Info;
import com.vic.liteplayer.utils.MediaUtil;
import com.vic.liteplayer.view.LrcProcess;
import com.vic.liteplayer.view.LrcView;
import com.vic.liteplayer.view.MyVisualizer;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private MediaPlayer mediaPlayer;
    private Visualizer visualizer;
    private TextView showTitle;

    UpdateSeekBarThread mUpdateSeekBarThread;
    Handler mHandler = new Handler();
    Handler handler = new Handler();

    private LrcProcess mLrcProcess;	//歌词处理
    private List<LrcContent> lrcList = new ArrayList<LrcContent>(); //存放歌词列表对象
    private LrcView lrcView;
    private int index = 0;			//歌词检索值

    Bundle bundle;
    int currentVolume,status,currentSong;

    AudioManager audioManager;
    Vibrator vibrator;
    ToggleButton mute;
    SeekBar progress_seekbar,volume_adjust;
    TextView music_time;

    ImageButton play_mode;
    ImageButton play_pause,previousSong,nextSong;

    List<Mp3Info> mp3Infos;

    String title,artist,url;
    int duration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mp3Infos = MediaUtil.getMp3Infos(this);
        mediaPlayer = new MediaPlayer();

        status = 1;
        Toast.makeText(this,"单曲循环",Toast.LENGTH_LONG).show();

        findView();
        setListener();
        setVolumeControlStream(AudioManager.STREAM_MUSIC);
        setupVisualizer();
    }

    @Override
    protected void onStart() {
        super.onStart();
        System.out.println("PlayerActivity has started");
    }
    @Override
    protected void onStop() {
        super.onStop();
        System.out.println("PlayerActivity has stoped");
    }
    @Override
    protected void onPause() {
        super.onPause();
        System.out.println("PlayerActivity has paused");
    }

    @Override
    protected void onResume() {
        super.onResume();
        System.out.println("PlayerActivity has onResume");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }


    private void findView()
    {
        progress_seekbar = (SeekBar)findViewById(R.id.progress);
        music_time = (TextView)findViewById(R.id.music_time);
        showTitle =(TextView)findViewById(R.id.text);
        mute = (ToggleButton)findViewById(R.id.mute);
        volume_adjust = (SeekBar)findViewById(R.id.volume);

        play_mode = (ImageButton) findViewById(R.id.play_mode);
        play_pause = (ImageButton)findViewById(R.id.play_music);
        previousSong = (ImageButton)findViewById(R.id.previous_music);
        nextSong = (ImageButton)findViewById(R.id.next_music);
        lrcView = (LrcView)findViewById(R.id.lrc);

        vibrator =(Vibrator)getSystemService(Service.VIBRATOR_SERVICE);
        audioManager =(AudioManager) getSystemService(Service.AUDIO_SERVICE);

        int maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        volume_adjust.setMax(maxVolume);
        volume_adjust.setProgress(audioManager.getStreamVolume(AudioManager.STREAM_MUSIC));
    }
    public void setListener()
    {
        play_mode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                status++;
                if(status>3) status=1;
                switch (status){
                    case 1:
                        Toast.makeText(MainActivity.this,"单曲循环",Toast.LENGTH_SHORT).show();
                        play_mode.setImageResource(R.drawable.repeat_current);
                        break;
                    case 2:
                        Toast.makeText(MainActivity.this,"列表循环",Toast.LENGTH_SHORT).show();
                        play_mode.setImageResource(R.drawable.repeat_all);
                        break;
                    case 3:
                        Toast.makeText(MainActivity.this,"随机播放",Toast.LENGTH_SHORT).show();
                        play_mode.setImageResource(R.drawable.shuffle_selector);
                        break;
//                    case 4:
//                        Toast.makeText(MainActivity.this,"随机播放",Toast.LENGTH_SHORT).show();
//                        break;
                }
            }
        });
        play_pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(title == null)
                {
                    currentSong = (int) (Math.random() * (mp3Infos.size() - 1));
                    setUpPlayer();
                }
                else{
                    if(!mediaPlayer.isPlaying())
                    {
                        mediaPlayer.start();
                        showTitle.setText(artist+"-"+title);
                        updateSeekBar();
                        play_pause.setImageResource(R.drawable.pause_selector);
                    }
                    else
                    {
                        mediaPlayer.pause();
                        showTitle.setText(artist+"-"+title);
                        play_pause.setImageResource(R.drawable.play_selector);
                    }
                }
            }
        });
        previousSong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentSong-=1;
                getNextSongBaseOnPlayMode();
                setUpPlayer();
            }
        });
        nextSong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentSong+=1;
                getNextSongBaseOnPlayMode();
                setUpPlayer();
            }
        });


        progress_seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            boolean flag = false;
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(flag)
                {
                    mediaPlayer.seekTo(progress);
                    music_time.setText(mediaPlayer.getCurrentPosition()/1000/60 + ":" +
                            mediaPlayer.getCurrentPosition()/1000%60 + "/" +
                            mediaPlayer.getDuration()/1000/60 + ":" +
                            mediaPlayer.getDuration()/1000%60);
                }

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                if(mediaPlayer.isPlaying())
                {
                    mediaPlayer.pause();
                    flag = true;
                }
                else flag = false;
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                if(flag)
                {
                    mediaPlayer.start();
                    updateSeekBar();
                    flag = false;
                }
            }
        });
        volume_adjust.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, progress, 0);
                //2016/4/23/1：30 当拖动音量条到0时，ToggleButton自动显示为静音
                if(progress == 0) {
                    mute.setChecked(true);
                }
                else mute.setChecked(false);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                if(volume_adjust.getProgress() == 0){   //2016/4/23/1：28 加这一段代码是为了解决这样的bug：在音量非0时，点静音，
                    currentVolume = 0;                  //currentVolume记录的是此时音量；之后再拖到音量0时，ToggleButton自动变为
                }                                       //静音状态，此时再点该按钮会恢复到currentVolume记录的音量

            }
        });
        //2016/4/23/1：30 多增加一个点击事件是为了实现：
        //当点击静音后，seekBar能够显示为音量0，再点击，能够恢复到静音前的音量
        //由于静音实际上音量不为零，而setProgress（0）后，由于setOnSeekBarChangeListener监听器
        //与seekBar的Progress相关联，实际上也就是把音量真的变为0了，故需要多一个变量currentVolume来记录
        //解决的bug：
        //如果没有多一个点击事件，而是把这段代码写在setOnCheckedChangeListener中
        //当拖动音量条到0后，要拖回来就拖不动了。因为拖动到0，ToggleButton自动变成checked（静音）
        // currentVolume记录为0，要拖回来，ToggleButton自动变成非静音，
        //此时执行 volume_adjust.setProgress(currentVolume);会恢复0，也就是拖不动
        mute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mute.isChecked()){
                    currentVolume = volume_adjust.getProgress();
                    volume_adjust.setProgress(0);
                }
                else volume_adjust.setProgress(currentVolume);
            }
        });
        mute.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    vibrator.vibrate(400);
                }
                audioManager.setStreamMute(AudioManager.STREAM_MUSIC,isChecked);
            }
        });
    }
    public void updateSeekBar(){
        mUpdateSeekBarThread = new UpdateSeekBarThread();
        mUpdateSeekBarThread.start();

    }
    private class UpdateSeekBarThread extends Thread{
        @Override
        public void run() {
            while(mediaPlayer.isPlaying()){

                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        music_time.setText(mediaPlayer.getCurrentPosition()/1000/60 + ":" +
                                mediaPlayer.getCurrentPosition()/1000%60 + "/" +
                                mediaPlayer.getDuration()/1000/60 + ":" +
                                mediaPlayer.getDuration()/1000%60);
                        progress_seekbar.setProgress(mediaPlayer.getCurrentPosition()); //获得当前播放的进度值
                    }
                });
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

    }
    public void setupVisualizer()
    {
        final MyVisualizer myVisualizer = (MyVisualizer)findViewById(R.id.visualizer);
        visualizer = new Visualizer(mediaPlayer.getAudioSessionId());
        visualizer.setCaptureSize(Visualizer.getCaptureSizeRange()[1]);
        visualizer.setDataCaptureListener(
                new Visualizer.OnDataCaptureListener()
                {
                    @Override
                    public void onFftDataCapture(Visualizer visualizer, byte[] fft, int samplingRate)
                    {
                    }
                    @Override
                    public void onWaveFormDataCapture(Visualizer visualizer,
                                                      byte[] waveform, int samplingRate)
                    {
                        myVisualizer.updateVisualizer(waveform);
                    }
                },Visualizer.getMaxCaptureRate()/2, true,false);
        visualizer.setEnabled(true);
    }

    private void setUpPlayer()
    {
        if(mp3Infos != null)
        {
            try{
                url = mp3Infos.get(currentSong).getUrl();
                if(mediaPlayer == null){
                    Toast.makeText(MainActivity.this,"No Song Prepared~!!",Toast.LENGTH_LONG).show();
                    mediaPlayer = new MediaPlayer();
                } else{
                    mediaPlayer.reset();
                }
                mediaPlayer.setDataSource(url);
                mediaPlayer.prepare();
                mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mp) {
                        mediaPlayer.start();
                        initLrc();
                        updateSeekBar();
                        artist = mp3Infos.get(currentSong).getArtist();
                        title = mp3Infos.get(currentSong).getTitle();
                        showTitle.setText(artist+"-"+title);
                        play_pause.setImageResource(R.drawable.pause);

                        progress_seekbar.setMax(mediaPlayer.getDuration());
                    }
                });
                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        if(status!=1) currentSong+=1;
                        getNextSongBaseOnPlayMode();
                        setUpPlayer();
                    }
                });
            }catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    private void getNextSongBaseOnPlayMode()
    {
        if (status == 1) { // 单曲循环
            mediaPlayer.seekTo(0);
        } else if (status == 2) { // 全部循环
            if(currentSong > mp3Infos.size() - 1) {	//变为第一首的位置继续播放
                currentSong = 0;
            }
            else if(currentSong <0){
                currentSong = mp3Infos.size() - 1;
            }
        }
//        else if (status == 3) { // 顺序播放
//            if (currentSong > mp3Infos.size() - 1){
//                currentSong = mp3Infos.size() - 1;
//                mediaPlayer.pause();
//                mediaPlayer.seekTo(0);
//                Toast.makeText(this,"已经到达最后一首",Toast.LENGTH_LONG).show();
//            }
//            else if(currentSong<0){
//                currentSong=0;
//            }
//        }
        else if(status == 3) {	//随机播放
            currentSong = (int) (Math.random() * (mp3Infos.size() - 1));
        }

    }

    public void btn_song_list(View view)
    {
        Intent intent = new Intent(MainActivity.this,SongListActivity.class);
        startActivityForResult(intent,0);
    }



    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 0 && resultCode==1){
            bundle = data.getExtras();
            if(bundle!= null){
                currentSong = bundle.getInt("listPosition");
                setUpPlayer();
            }
        }
    }

    /**
     * 初始化歌词配置
     */
    public void initLrc(){
        mLrcProcess = new LrcProcess();
        //读取歌词文件
        mLrcProcess.readLRC(mp3Infos.get(currentSong).getUrl());
        //传回处理后的歌词文件
        lrcList = mLrcProcess.getLrcList();
        lrcView.setmLrcList(lrcList);
        //切换带动画显示歌词
        lrcView.setAnimation(AnimationUtils.loadAnimation(MainActivity.this,R.anim.alpha_z));
        handler.post(mRunnable);
    }
    Runnable mRunnable = new Runnable() {

        @Override
        public void run() {
            lrcView.setIndex(lrcIndex());
            lrcView.invalidate();
            handler.postDelayed(mRunnable, 100);
        }
    };

    /**
     * 根据时间获取歌词显示的索引值
     * @return
     */
    public int lrcIndex() {
        int currentTime;
        if(mediaPlayer.isPlaying()) {
            currentTime = mediaPlayer.getCurrentPosition();
            duration = mediaPlayer.getDuration();

            if(currentTime < duration) {
                for (int i = 0; i < lrcList.size(); i++) {
                    if (i < lrcList.size() - 1) {
                        if (currentTime < lrcList.get(i).getLrcTime() && i == 0) {
                            index = i;
                        }
                        if (currentTime > lrcList.get(i).getLrcTime()
                                && currentTime < lrcList.get(i + 1).getLrcTime()) {
                            index = i;
                        }
                    }
                    if (i == lrcList.size() - 1
                            && currentTime > lrcList.get(i).getLrcTime()) {
                        index = i;
                    }
                }
            }

        }

        return index;
    }

}
