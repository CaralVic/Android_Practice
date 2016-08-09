package com.vic.tabtest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity implements View.OnClickListener {

    private Button btn1,btn2,btn3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        initListener();
    }

    private void initListener() {
        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
    }

    private void initView() {
        btn1 = (Button)findViewById(R.id.btn1);
        btn2 = (Button)findViewById(R.id.btn2);
        btn3 = (Button)findViewById(R.id.btn3);
    }


    public void onClick(View v){
        Intent intent ;
        switch (v.getId()){
            case R.id.btn1:
                intent = new Intent(this,Activity1.class);
                startActivity(intent);
                break;
            case R.id.btn2:
                intent = new Intent(this,Activity2.class);
                startActivity(intent);
                break;
            case R.id.btn3:
                intent = new Intent(this,Activity3.class);
                startActivity(intent);
                break;

        }
    }
}
