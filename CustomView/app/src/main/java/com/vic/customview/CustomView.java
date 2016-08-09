package com.vic.customview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by Vic on 2016/4/9.
 * 自定义View组件
 */
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
        @Override
        public void onDraw(Canvas canvas)
        {
            super.onDraw(canvas);
            p.setColor(Color.RED);
            canvas.drawCircle(currentX, currentY, 55, p);
        }
        @Override
        public boolean onTouchEvent(MotionEvent event)
        {
            currentX = event.getX();
            currentY = event.getY();
            invalidate();
            return  true;
        }
}
