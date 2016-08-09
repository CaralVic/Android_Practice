package com.vic.userinterface;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ProgressBar pBar;
    private Button showAlertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        showAlertDialog=(Button)findViewById(R.id.btn);
        pBar=(ProgressBar)findViewById(R.id.progressBar);

        showAlertDialog.setOnClickListener(this);
        pBar.setOnClickListener(this);
    }
    @Override
    public void onClick(View v){
        switch(v.getId()){
            case R.id.progressBar:
                int p=pBar.getProgress();
                p+=10;
                pBar.setProgress(p);
                break;
            case R.id.btn:
                AlertDialog.Builder dialog=new AlertDialog.Builder(MainActivity.this);
                dialog.setTitle("Warning!")
                        .setMessage("Attention Please!")
                        .setCancelable(true)
                        .setPositiveButton("Confirm",new DialogInterface.OnClickListener(){
                           @Override
                            public void onClick(DialogInterface dialog, int which){
                               int p=pBar.getProgress();
                               if(p<=90) p+=10;
                               pBar.setProgress(p);
                           }
                        })
                        .setNegativeButton("Cancel",new DialogInterface.OnClickListener(){
                            @Override
                            public void onClick(DialogInterface dialog,int whick){
                                int p=pBar.getProgress();
                                if(p>=10) p-=10;
                                pBar.setProgress(p);
                            }
                        });
                dialog.show();

                break;
            default:
                break;
        }
    }
}
