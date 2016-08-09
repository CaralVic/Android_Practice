package com.vic.tabtest;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.LinearLayout;

public class Activity2 extends FragmentActivity implements View.OnClickListener {

    private LinearLayout tab_weixin,tab_friend,tab_contact,tab_setting;
    private ImageButton img_weixin,img_friend,img_contact,img_setting;
    private Fragment f_weixin,f_friend,f_contact,f_setting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_2);

        initView();
        initListener();

        setSelectedFragment(1);
    }

    private void initListener() {
        tab_weixin.setOnClickListener(this);
        tab_friend.setOnClickListener(this);
        tab_contact.setOnClickListener(this);
        tab_setting.setOnClickListener(this);
    }

    private void initView() {
        tab_weixin = (LinearLayout) findViewById(R.id.tab_weixin);
        tab_friend = (LinearLayout) findViewById(R.id.tab_friend);
        tab_contact = (LinearLayout) findViewById(R.id.tab_contact);
        tab_setting = (LinearLayout) findViewById(R.id.tab_setting);

        img_weixin = (ImageButton)findViewById(R.id.tab_weixin_img);
        img_friend = (ImageButton)findViewById(R.id.tab_friend_img);
        img_contact = (ImageButton)findViewById(R.id.tab_contact_img);
        img_setting = (ImageButton)findViewById(R.id.tab_setting_img);
    }

    private void hideFragment(FragmentTransaction transaction) {
        if(f_weixin == null){
            f_weixin = new Fragment_1();
            transaction.add(R.id.fragment_container,f_weixin);
        }
        if(f_weixin != null){
            transaction.hide(f_weixin);
        }
        if(f_friend == null){
            f_friend = new Fragment_2();
            transaction.add(R.id.fragment_container,f_friend);
        }
        if(f_friend != null){
            transaction.hide(f_friend);
        }
        if(f_contact == null){
            f_contact = new Fragment_3();
            transaction.add(R.id.fragment_container,f_contact);
        }
        if(f_contact != null){
            transaction.hide(f_contact);
        }
        if(f_setting == null){
            f_setting = new Fragment_4();
            transaction.add(R.id.fragment_container,f_setting);
        }
        if(f_setting != null){
            transaction.hide(f_setting);
        }
    }


    public void onClick(View v){
        resetImg();
        switch (v.getId()){
            case R.id.tab_weixin:
                setSelectedFragment(1);
                break;
            case R.id.tab_friend:
                setSelectedFragment(2);
                break;
            case R.id.tab_contact:
                setSelectedFragment(3);
                break;
            case R.id.tab_setting:
                setSelectedFragment(4);
                break;
        }

    }

    private void setSelectedFragment(int i) {

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        hideFragment(transaction);
        switch (i){
            case 1:
                transaction.show(f_weixin);
                img_weixin.setImageResource(R.drawable.tab_weixin_pressed);
                break;
            case 2:
                transaction.show(f_friend);
                img_friend.setImageResource(R.drawable.tab_find_frd_pressed);
                break;
            case 3:
                transaction.show(f_contact);
                img_contact.setImageResource(R.drawable.tab_address_pressed);
                break;
            case 4:
                transaction.show(f_setting);
                img_setting.setImageResource(R.drawable.tab_settings_pressed);
                break;
        }
        transaction.commit();
    }

    private void resetImg() {
        img_contact.setImageResource(R.drawable.tab_address_normal);
        img_setting.setImageResource(R.drawable.tab_settings_normal);
        img_friend.setImageResource(R.drawable.tab_find_frd_normal);
        img_weixin.setImageResource(R.drawable.tab_weixin_normal);
    }
}
