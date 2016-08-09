package com.vic.usageofintent;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;


public class MainActivity extends Activity {
    static final int REQUEST_CODE = 0;
    TextView massage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        massage = (TextView) findViewById(R.id.textView1);
        Button button = (Button) findViewById(R.id.Button1);

        button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getBaseContext(), Activity1.class);
                startActivityForResult(i, REQUEST_CODE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Bundle b = data.getExtras();
                String name = b.getString("name");
                String sex = b.getString("sex");
                String age = b.getString("age");
                massage.setText("You massage:\nName:" + name + "\nSex:" + sex + "\nAge:" + age);

            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


}
