package com.example.tanuwid_audr.offseasons;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.ArrayList;

public class UseData extends Activity {
	
	private TextView texted = null;
	private ArrayList<String> restaurants = new ArrayList<String>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		texted = (TextView)findViewById(R.id.hello);
		
		//get ArrayList from intent object
		restaurants = getIntent().getStringArrayListExtra("list");
		
		//write data to UI
		for (int i=0; i<restaurants.size(); i++) {
			texted.append(restaurants.get(i) + "\n");
		}
		
	}

}
