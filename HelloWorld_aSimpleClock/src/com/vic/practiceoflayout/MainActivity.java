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
        
        sdf = new SimpleDateFormat(" yyyy��MM��dd��   HH:mm:ss ",Locale.CHINA);
		time =new Date(System.currentTimeMillis());
        new Thread(new timeThread()).start();       //�����߳�
    }
	
    /*
     *Android��������ˢ�·�����
     *Android��invalidate��postInvalidate��������ˢ�½����;
     *��ַ��http://www.cnblogs.com/devinzhang/archive/2012/01/28/2330468.html
     * 2015/5/2/1:57
     * ֱ�����߳���ˢ�½���������
     * ������Handler����
     * ��Handler��Ӧ��Ϊstatic���ͣ������п������й¶��
     * ���Handler�Ǹ��ڲ��࣬��ô��Ҳ�ᱣ�������ڵ��ⲿ������á�
     * Ϊ�˱���й¶����ⲿ�࣬Ӧ�ý�Handler����ΪstaticǶ���࣬����ʹ�ö��ⲿ��������á�
     */
	static class mHandler extends Handler{				//��Handler����ΪstaticǶ����
		WeakReference<MainActivity> mActivity;			//����ʹ�ö��ⲿ���������
		
		public mHandler(MainActivity activity) {
			mActivity = new WeakReference<MainActivity>(activity);
		}
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case MainActivity.REFRESH:
				time.setTime(System.currentTimeMillis());
				str = sdf.format(time); 			//Handler�д�����Ϣ��ˢ��ʱ�䣬ˢ����ʾ��
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
    			MainActivity.this.myHandler.sendMessage(message);//���߳��з�����Ϣ
    		}
    	}
    }
  }


