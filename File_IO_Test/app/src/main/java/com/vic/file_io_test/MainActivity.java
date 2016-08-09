package com.vic.file_io_test;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class MainActivity extends Activity {

    private EditText inputText;
    private Button submit;
    private TextView indicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inputText = (EditText)findViewById(R.id.inputText);
        submit = (Button)findViewById(R.id.submit);
        indicator = (TextView)findViewById(R.id.indicator);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String input = inputText.getText().toString();
                SaveText(input);
            }
        });

        String input = loadText();
        if(!TextUtils.isEmpty(input)){
            inputText.setText(input);
            inputText.setSelection(inputText.length());
            Toast.makeText(this,"Automatic restroed Successfully",Toast.LENGTH_SHORT)
                    .show();
        }

    }
    @Override
    protected void onDestroy(){
        super.onDestroy();
        String inputString =inputText.getText().toString();
        SaveText(inputString);
    }

    private String loadText(){
        FileInputStream in = null;
        BufferedReader reader = null;
        StringBuilder content = new StringBuilder();


        try{
            in = openFileInput("data");
            reader = new BufferedReader(new InputStreamReader(in));
            String line = "";
            while ((line = reader.readLine())!=null){
                content.append(line);
            }
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            if(reader != null){
                try{
                    reader.close();
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        }
        return content.toString();
    }

    protected void SaveText(String inputText){
        FileOutputStream out = null;
        BufferedWriter writer = null;
        try{
            out = openFileOutput("data",Context.MODE_PRIVATE);
            writer = new BufferedWriter(new OutputStreamWriter(out));
            writer.write(inputText);
        }catch (IOException e){
            e.printStackTrace();
            Toast.makeText(MainActivity.this,"Error occur while writing data :( ",Toast.LENGTH_SHORT)
                    .show();
        }finally {
            try{
                if(writer != null){
                    writer.close();
                    Toast.makeText(MainActivity.this,"Submit successfully!",Toast.LENGTH_SHORT)
                    .show();
                    indicator.setText(this.inputText.getText().toString());
                }
            }catch (IOException e){
                e.printStackTrace();
                Toast.makeText(MainActivity.this,"Error occur while closing BufferWriter:( ",Toast.LENGTH_SHORT)
                        .show();
            }
        }
    }
}
