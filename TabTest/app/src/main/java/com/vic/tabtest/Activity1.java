package com.vic.tabtest;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

public class Activity1 extends FragmentActivity implements View.OnClickListener {

    private LinearLayout tab_weixin,tab_friend,tab_contact,tab_setting;
    private ImageButton img_weixin,img_friend,img_contact,img_setting;
    private ViewPager viewPager;
    private PagerAdapter pagerAdapter;
    private List<View> tabViews = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_1);

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
        viewPager = (ViewPager)findViewById(R.id.viewPager);

        tab_weixin = (LinearLayout) findViewById(R.id.tab_weixin);
        tab_friend = (LinearLayout) findViewById(R.id.tab_friend);
        tab_contact = (LinearLayout) findViewById(R.id.tab_contact);
        tab_setting = (LinearLayout) findViewById(R.id.tab_setting);

        img_weixin = (ImageButton)findViewById(R.id.tab_weixin_img);
        img_friend = (ImageButton)findViewById(R.id.tab_friend_img);
        img_contact = (ImageButton)findViewById(R.id.tab_contact_img);
        img_setting = (ImageButton)findViewById(R.id.tab_setting_img);

        LayoutInflater inflater = LayoutInflater.from(this);
        View view1 =inflater.inflate(R.layout.tab01,null);
        View view2 =inflater.inflate(R.layout.tab02,null);
        View view3 =inflater.inflate(R.layout.tab03,null);
        View view4 =inflater.inflate(R.layout.tab04,null);
        tabViews.add(view1);
        tabViews.add(view2);
        tabViews.add(view3);
        tabViews.add(view4);



        pagerAdapter = new PagerAdapter() {
            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                View view = tabViews.get(position);
                container.addView(view);
                return view;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView(tabViews.get(position));
            }

            @Override
            public int getCount() {
                return tabViews.size();
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view ==object;
            }
        };
        viewPager.setAdapter(pagerAdapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                resetImg();
                switch (position){
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

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }


    public void onClick(View v){
        resetImg();
        switch (v.getId()){
            case R.id.tab_weixin:
                viewPager.setCurrentItem(0);
                img_weixin.setImageResource(R.drawable.tab_weixin_pressed);
                break;
            case R.id.tab_friend:
                viewPager.setCurrentItem(1);
                img_friend.setImageResource(R.drawable.tab_find_frd_pressed);
                break;
            case R.id.tab_contact:
                viewPager.setCurrentItem(2);
                img_contact.setImageResource(R.drawable.tab_address_pressed);
                break;
            case R.id.tab_setting:
                viewPager.setCurrentItem(3);
                img_setting.setImageResource(R.drawable.tab_settings_pressed);
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
