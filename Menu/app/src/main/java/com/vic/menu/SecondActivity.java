package com.vic.menu;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class SecondActivity extends AppCompatActivity {

    Button show,hide,back;
    android.support.v7.app.ActionBar actionBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        show =(Button)findViewById(R.id.ShowActionBar);
        hide =(Button)findViewById(R.id.HideActionBar);
        back =(Button)findViewById(R.id.BackBtn);

        show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(actionBar!=null){
                    actionBar.show();
                }
                else {
                    Toast toast = Toast.makeText(SecondActivity.this,
                            "Action Bar Inactivate!", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });
        hide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(actionBar!=null){
                    actionBar.hide();
                }
                else {
                    Toast toast = Toast.makeText(SecondActivity.this,
                            "Action Bar Inactivate!", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(SecondActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });
    }
    public boolean onOptionsItemSelected(MenuItem menuItem)
    {
        if(menuItem.isCheckable())
        {
            menuItem.setChecked(true);
        }
        switch (menuItem.getItemId())
        {
            case android.R.id.home:
                Intent intent = new Intent(this,MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                break;
        }
        return true;
    }
}
