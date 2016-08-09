package com.vic.customview_movingball;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



    }

}

public class CustomView extends View
{
    public float currentX=0;
    public float currentY=0;
    Paint p = new Paint();
    public CustomView(Context context)
    {
        super(context);
    }
    public CustomView(Context context,AttributeSet set)
    {
        super(context,set);
    }
    @override
    public void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);
        p.setColor(Color.RED);
        canvas.drawCircle(currentX, currentY, 15, p);
    }
    @override
    public boolean onTouchEvent(MotionEvent event)
    {
        currentX = event.getX();
        currentY = event.getY();
        invalidate();
        return  true;
    }
}
