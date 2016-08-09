package com.vic.tabtest;



import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

public class Activity3 extends FragmentActivity implements View.OnClickListener {

    private LinearLayout tab_weixin,tab_friend,tab_contact,tab_setting;
    private ImageButton img_weixin,img_friend,img_contact,img_setting;
    private Fragment f_weixin,f_friend,f_contact,f_setting;

    private List<Fragment> fragments= new ArrayList<>();
    private ViewPager viewPager;
    private FragmentPagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_3);

        initView();
        initListener();
    }

    private void initListener() {
        tab_weixin.setOnClickListener(this);
        tab_friend.setOnClickListener(this);
        tab_contact.setOnClickListener(this);
        tab_setting.setOnClickListener(this);
    }

    private void initView() {
        f_weixin = new Fragment_1();
        f_friend = new Fragment_2();
        f_contact = new Fragment_3();
        f_setting = new Fragment_4();
        fragments.add(f_weixin);
        fragments.add(f_friend);
        fragments.add(f_contact);
        fragments.add(f_setting);

        viewPager = (ViewPager)findViewById(R.id.viewPager);
        adapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return fragments.get(position);
            }

            @Override
            public int getCount() {
                return fragments.size();
            }
        };
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                setSelected(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        tab_weixin = (LinearLayout) findViewById(R.id.tab_weixin);
        tab_friend = (LinearLayout) findViewById(R.id.tab_friend);
        tab_contact = (LinearLayout) findViewById(R.id.tab_contact);
        tab_setting = (LinearLayout) findViewById(R.id.tab_setting);

        img_weixin = (ImageButton)findViewById(R.id.tab_weixin_img);
        img_friend = (ImageButton)findViewById(R.id.tab_friend_img);
        img_contact = (ImageButton)findViewById(R.id.tab_contact_img);
        img_setting = (ImageButton)findViewById(R.id.tab_setting_img);

        setSelected(0);
    }

    private void setSelected(int i) {
        viewPager.setCurrentItem(i);
        resetImg();
        switch (i){
            case 0:
                img_weixin.setImageResource(R.drawable.tab_weixin_pressed);
                break;
            case 1:
                img_friend.setImageResource(R.drawable.tab_find_frd_pressed);
                break;
            case 2:
                img_contact.setImageResource(R.drawable.tab_address_pressed);
                break;
            case 3:
                img_setting.setImageResource(R.drawable.tab_settings_pressed);
                break;
        }
    }


    public void onClick(View v){
        switch (v.getId()){
            case R.id.tab_weixin:
                setSelected(0);
                break;
            case R.id.tab_friend:
                setSelected(1);
                break;
            case R.id.tab_contact:
                setSelected(2);
                break;
            case R.id.tab_setting:
                setSelected(3);
                break;

        }
    }

    private void resetImg() {
        img_contact.setImageResource(R.drawable.tab_address_normal);
        img_setting.setImageResource(R.drawable.tab_settings_normal);
        img_friend.setImageResource(R.drawable.tab_find_frd_normal);
        img_weixin.setImageResource(R.drawable.tab_weixin_normal);
    }
}
