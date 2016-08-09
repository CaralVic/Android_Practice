package com.vic.practiceoflayout;


import java.lang.ref.WeakReference;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Locale;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.TextView;


public class MainActivity extends Activity{  	
	static final int REFRESH = 0;
	static TextView textView_time;
	static String str;
	static Date time;
	static SimpleDateFormat sdf;
	
	mHandler myHandler = new mHandler(this);
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        TextView secret = (TextView) findViewById(R.id.secrets);       
        textView_time =(TextView)findViewById(R.id.time);
        
        String word= "I Miss You, Caral";
        char[] word_array = word.toCharArray();
        for(int i=0;i<word.length();i++){            
        	secret.setText((secret.getText())+" "+(int)word_array[i]);          
        }
        
        sdf = new SimpleDateFormat(" yyyy年MM月dd日   HH:mm:ss ",Locale.CHINA);
		time =new Date(System.currentTimeMillis());
        new Thread(new timeThread()).start();       //开启线程
    }
	
    /*
     *Android界面两种刷新方法：
     *Android的invalidate与postInvalidate都是用来刷新界面的;
     *网址：http://www.cnblogs.com/devinzhang/archive/2012/01/28/2330468.html
     * 2015/5/2/1:57
     * 直接在线程中刷新界面会崩溃；
     * 必须用Handler处理；
     * 而Handler类应该为static类型，否则有可能造成泄露。
     * 如果Handler是个内部类，那么它也会保持它所在的外部类的引用。
     * 为了避免泄露这个外部类，应该将Handler声明为static嵌套类，并且使用对外部类的弱引用。
     */
	static class mHandler extends Handler{				//将Handler声明为static嵌套类
		WeakReference<MainActivity> mActivity;			//并且使用对外部类的弱引用
		
		public mHandler(MainActivity activity) {
			mActivity = new WeakReference<MainActivity>(activity);
		}
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case MainActivity.REFRESH:
				time.setTime(System.currentTimeMillis());
				str = sdf.format(time); 			//Handler中处理消息，刷新时间，刷新显示；
				textView_time.setText(str);
				textView_time.invalidate();
				break;
			default:
				break;
			}
		
		super.handleMessage(msg);
		}
	}
	
	
	class timeThread implements Runnable {
    	public void run(){    			   		
    		while(!Thread.currentThread().isInterrupted()){
    			Message message = new Message();
    			message.what=MainActivity.REFRESH;   			
    			try {
    				Thread.sleep(1000);
				} catch (InterruptedException e) {
					Thread.currentThread().interrupt();
				}
    			MainActivity.this.myHandler.sendMessage(message);//在线程中发送消息
    		}
    	}
    }
  }


