package com.vic.sms_test;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;

/**
 * Created by Vic on 8/2/2016.
 */
public class smsReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent){
        Bundle bundle  = intent.getExtras();
        Object[] pdus = (Object[])bundle.get("pdus");
        SmsMessage[] messages = new SmsMessage[pdus.length];
        for(int i=0;i<messages.length;i++){
            messages[i] = SmsMessage.createFromPdu((byte[])pdus[i]);
            String address = messages[0].getOriginatingAddress();
            String smsContent = "";
            for(SmsMessage message:messages){
                smsContent +=message.getMessageBody();
            }
            Intent smsIntent =new Intent(context,MainActivity.class);
            intent.putExtra("address",address);
            intent.putExtra("content",smsContent);
            context.startActivity(smsIntent);
        }
    }
}
