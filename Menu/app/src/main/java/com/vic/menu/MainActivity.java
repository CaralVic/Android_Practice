package com.vic.menu;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    final int FONT_10 = 0x011;
    final int FONT_12 = 0x012;
    final int PLAIN_ITEM = 0x013;
    final int FONT_RED = 0x014;
    final int FONT_BLUE = 0x015;
    final int ACT_ITEM = 0x016;

    final int MENU1 = 0x101;
    final int MENU2 = 0x102;
    final int MENU3 = 0x103;

    EditText editText;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView =(TextView)findViewById(R.id.textView);
        registerForContextMenu(textView);
        editText = (EditText)findViewById(R.id.editText);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        SubMenu fontMenu = menu.addSubMenu("Font Size");
        fontMenu.setGroupCheckable(0, true, true);
        fontMenu.setIcon(R.mipmap.ic_launcher);
        fontMenu.setHeaderIcon(R.mipmap.ic_launcher);
        fontMenu.setHeaderTitle("Select Font Size:");
        fontMenu.add(0, FONT_10, 0, "10 Font");
        fontMenu.add(0, FONT_12, 0, "12 Font");

        SubMenu colorMenu = menu.addSubMenu("Font Color");
        colorMenu.setIcon(R.mipmap.ic_launcher);
        colorMenu.setHeaderIcon(R.mipmap.ic_launcher);
        colorMenu.setHeaderTitle("Select Font Color:");
        colorMenu.add(0, FONT_BLUE, 0, "Blue Font");
        colorMenu.add(0, FONT_RED, 0, "Red Font");

        menu.add(0, PLAIN_ITEM, 0, "Normal");
        menu.add(0, ACT_ITEM, 0, "Second Activity");

        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem)
    {
        switch (menuItem.getItemId())
        {
            case FONT_10:
                editText.setTextSize(10*2);
                break;
            case FONT_12:
                editText.setTextSize(12 * 2);
                break;
            case FONT_BLUE:
                editText.setTextColor(Color.BLUE);
                break;
            case FONT_RED:
                editText.setTextColor(Color.RED);
                break;
            case PLAIN_ITEM:
                Toast toast = Toast.makeText(MainActivity.this,
                        "You clicked the Normal Menu Item", Toast.LENGTH_SHORT);
                toast.show();
                Intent intent1 =new Intent(MainActivity.this,SecondActivity.class);
                startActivity(intent1);
                break;
            case ACT_ITEM:
                Intent intent =new Intent(MainActivity.this,SecondActivity.class);
                startActivity(intent);
                break;
        }
        return true;
    }
    @Override
    public void onCreateContextMenu(ContextMenu menu, View source,ContextMenu.ContextMenuInfo menuInfo){
        menu.add(0,MENU1,0,"Red");
        menu.add(0,MENU2,0,"Blue");
        menu.add(0,MENU3,0,"Green");
        menu.setGroupCheckable(0, true, true);
        menu.setHeaderIcon(R.mipmap.ic_launcher);
        menu.setHeaderTitle("Choose Background Color:");
    }
    @Override
    public boolean onContextItemSelected(MenuItem menuItem)
    {
        switch (menuItem.getItemId())
        {
            case MENU1:
                menuItem.setChecked(true);
                textView.setBackgroundColor(Color.RED);
                break;
            case MENU2:
                menuItem.setChecked(true);
                textView.setBackgroundColor(Color.BLUE);
                break;
            case MENU3:
                menuItem.setChecked(true);
                textView.setBackgroundColor(Color.BLACK);
                break;
        }
        return true;
    }
    public void goToPopup(View v)
    {
        Intent intent = new Intent(this,PopUpMenu.class);
        startActivity(intent);
    }
}
