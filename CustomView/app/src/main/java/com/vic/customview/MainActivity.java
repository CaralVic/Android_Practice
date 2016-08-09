package com.vic.customview;

import android.app.Activity;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        RelativeLayout root = (Relative) findViewById(R.id.root);
//        final CustomView ball = new CustomView(this);
//        root.addView(ball);
    }
}
