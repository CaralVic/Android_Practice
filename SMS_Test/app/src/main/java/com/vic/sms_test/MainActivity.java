package com.vic.sms_test;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    private TextView sender;
    private TextView content;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sender = (TextView)findViewById(R.id.sender);
        content = (TextView)findViewById(R.id.content);

        Intent intent = getIntent();
        if(intent!=null){
            String address = intent.getStringExtra("address");
            String SMScontent = intent.getStringExtra("content");
            sender.setText(address);
            content.setText(SMScontent);
        }


    }
}
