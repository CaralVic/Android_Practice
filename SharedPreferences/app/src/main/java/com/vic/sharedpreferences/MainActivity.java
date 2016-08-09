package com.vic.sharedpreferences;

import android.content.SharedPreferences;
import android.support.annotation.BoolRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private Button save,restore,clear;
    private TextView show;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        save = (Button)findViewById(R.id.sava_data);
        restore = (Button)findViewById(R.id.restore_data);
        clear = (Button)findViewById(R.id.clear_data);

        show = (TextView)findViewById(R.id.show_message);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = getSharedPreferences("vic",MODE_PRIVATE).edit();
                editor.putString("name","Vic");
                editor.putInt("age",21);
                editor.putBoolean("married",false);
                editor.commit();
                show.setText("Save data successfully\n");
            }
        });
        restore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences preferences = getSharedPreferences("vic",MODE_PRIVATE);
                String name = preferences.getString("name","null");
                int age = preferences.getInt("age",0);
                Boolean married = preferences.getBoolean("married",false);
                show.append("Name:"+name+";\n"+"Age:"+age+";\n"+"Married:"+married);

            }
        });
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences preferences = getSharedPreferences("vic",MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.clear();
                editor.commit();
                show.setText("Data was erased!\n");
                String name = preferences.getString("name","null");
                int age = preferences.getInt("age",0);
                Boolean married = preferences.getBoolean("married",false);
                show.append("Name:"+name+";\n"+"Age:"+age+";\n"+"Married:"+married);
            }
        });

    }
}
