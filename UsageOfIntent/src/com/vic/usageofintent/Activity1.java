package com.vic.usageofintent;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class Activity1 extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity1);
		
		final EditText name_in=(EditText)findViewById(R.id.name_input);
		final EditText sex_in =(EditText)findViewById(R.id.sex_input);
		final EditText age_in = (EditText) findViewById(R.id.age_input);
		Button button =(Button) findViewById(R.id.Button_confirm);

		
		button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				
				 String name = name_in.getText().toString();
				 String sex = sex_in.getText().toString();
				 String age =age_in.getText().toString();
				
				Intent i=new Intent();
				Bundle bundle=new Bundle();
				bundle.putString("name", name);
				bundle.putString("age", age);
				bundle.putString("sex", sex);
				i.putExtras(bundle);
				setResult(RESULT_OK,i);
				finish();							
			}
		});
		
	}
	

}
