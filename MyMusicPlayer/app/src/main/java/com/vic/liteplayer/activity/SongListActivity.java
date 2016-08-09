package com.vic.liteplayer.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.vic.liteplayer.R;
import com.vic.liteplayer.method.Mp3Info;
import com.vic.liteplayer.utils.MediaUtil;

import java.util.HashMap;
import java.util.List;

public class SongListActivity extends AppCompatActivity {
    ListView list;
    List<Mp3Info> mp3Infos;

//    private HomeReceiver homeReceiver; // 自定义的广播接收器
    // 一系列动作
    public static final String UPDATE_ACTION = "com.wwj.action.UPDATE_ACTION"; // 更新动作
    public static final String MUSIC_CURRENT = "com.wwj.action.MUSIC_CURRENT"; // 当前音乐改变动作
    public static final String MUSIC_DURATION = "com.wwj.action.MUSIC_DURATION"; // 音乐时长改变动作
    public static final String REPEAT_ACTION = "com.wwj.action.REPEAT_ACTION"; // 音乐重复改变动作
    public static final String SHUFFLE_ACTION = "com.wwj.action.SHUFFLE_ACTION"; // 音乐随机播放动作

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song_list);

        findView();
        Scan();
        setListener();

    }
    private void findView()
    {
        list = (ListView)findViewById(R.id.songList);
    }
    private void setListener()
    {
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (mp3Infos != null) {
                    Mp3Info mp3Info = mp3Infos.get(position);

                    Intent intent = new Intent(SongListActivity.this, MainActivity.class); // 定义Intent对象，跳转到PlayerActivity
                    intent.putExtra("listPosition", position);
                    setResult(1, intent);
                    finish();
                }
            }
        });
    }

    public void Scan()
    {
        mp3Infos = MediaUtil.getMp3Infos(SongListActivity.this);
        List<HashMap<String, String>> queues = MediaUtil.getMusicMaps(mp3Infos);
        SimpleAdapter adapter = new SimpleAdapter(this, queues,
                R.layout.song_list_item,
                new String[]{"title","artist","duration"},
                new int[]{R.id.names,R.id.artist,R.id.duration});
        list.setAdapter(adapter);
    }
    // 自定义的BroadcastReceiver，负责监听从Service传回来的广播
//    public class HomeReceiver extends BroadcastReceiver {
//
//        @Override
//        public void onReceive(Context context, Intent intent) {
//            String action = intent.getAction();
//            if (action.equals(MUSIC_CURRENT)) {
//                // currentTime代表当前播放的时间
//                currentTime = intent.getIntExtra("currentTime", -1);
//                musicDuration.setText(MediaUtil.formatTime(currentTime));
//            } else if (action.equals(MUSIC_DURATION)) {
//                duration = intent.getIntExtra("duration", -1);
//            } else if (action.equals(UPDATE_ACTION)) {
//                // 获取Intent中的current消息，current代表当前正在播放的歌曲
//                listPosition = intent.getIntExtra("current", -1);
//                if (listPosition >= 0) {
//                    musicTitle.setText(mp3Infos.get(listPosition).getTitle());
//                }
//            } else if (action.equals(REPEAT_ACTION)) {
//                repeatState = intent.getIntExtra("repeatState", -1);
//                switch (repeatState) {
//                    case isCurrentRepeat: // 单曲循环
//                        repeatBtn
//                                .setBackgroundResource(R.drawable.repeat_current_selector);
//                        shuffleBtn.setClickable(false);
//                        break;
//                    case isAllRepeat: // 全部循环
//                        repeatBtn
//                                .setBackgroundResource(R.drawable.repeat_all_selector);
//                        shuffleBtn.setClickable(false);
//                        break;
//                    case isNoneRepeat: // 无重复
//                        repeatBtn
//                                .setBackgroundResource(R.drawable.repeat_none_selector);
//                        shuffleBtn.setClickable(true);
//                        break;
//                }
//            } else if (action.equals(SHUFFLE_ACTION)) {
//                isShuffle = intent.getBooleanExtra("shuffleState", false);
//                if (isShuffle) {
//                    isNoneShuffle = false;
//                    shuffleBtn
//                            .setBackgroundResource(R.drawable.shuffle_selector);
//                    repeatBtn.setClickable(false);
//                } else {
//                    isNoneShuffle = true;
//                    shuffleBtn
//                            .setBackgroundResource(R.drawable.shuffle_none_selector);
//                    repeatBtn.setClickable(true);
//                }
//            }
//        }
//
//    }
}