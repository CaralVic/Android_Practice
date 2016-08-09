package com.vic.servicetest;

import android.app.Activity;
import android.app.NotificationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

/**
 * Created by Vic on 8/1/2016.
 */
public class NotificationActivity extends Activity implements View.OnClickListener{
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        NotificationManager manager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        manager.cancel(1);
        LinearLayout linearLayout = (LinearLayout)findViewById(R.id.linearLayout);
        linearLayout.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.linearLayout:
                finish();
                break;
            default:
                break;
        }
    }
}
