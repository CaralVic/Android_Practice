package com.vic.actionbar;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.Window;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        showOverflowButton();
//        getActionBar().setDisplayShowHomeEnabled(false);

//        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

    }
//
//    private void showOverflowButton() {
//        try {
//            ViewConfiguration configuration = ViewConfiguration.get(this);
//            Field menukey = ViewConfiguration.class.getDeclaredField("sHasPermanentMenuKey");
//            menukey.setAccessible(true);
//            menukey.setBoolean(configuration,false);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main,menu);
        return true;
    }

    @Override
    public boolean onMenuOpened(int featureId, Menu menu) {
        Log.e("Msg",featureId+";");
        if(featureId == Window.FEATURE_ACTION_BAR && menu != null){
            if(menu.getClass().getSimpleName().equals("MenuBuilder")){
                try {
                    Method m = menu.getClass().getDeclaredMethod(
                            "setOptionalIconsVisible",Boolean.TYPE);
                    m.setAccessible(true);
                    m.invoke(menu,true);
                    Log.e("Msg","change setting successfully");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        return super.onMenuOpened(featureId, menu);
    }

//    @Override
//    protected boolean onPrepareOptionsPanel(View view, Menu menu) {
//        if(menu != null){
//            if(menu.getClass().getSimpleName().equals("MenuBuilder")){
//                try {
//                    Method m = menu.getClass().getDeclaredMethod(
//                            "setOptionalIconsVisible",Boolean.TYPE);
//                    m.setAccessible(true);
//                    m.invoke(menu,true);
//                    Log.e("Msg","change setting successfully");
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//        return super.onPrepareOptionsPanel(view, menu);
//    }

}
