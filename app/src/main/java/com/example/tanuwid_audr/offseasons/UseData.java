package com.example.tanuwid_audr.offseasons;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class UseData extends Activity implements View.OnClickListener, Serializable {
	
	//private TextView texted = null;
	private ListView texted = null;
	private ArrayList<String> restaurantnames = new ArrayList<String>();
	private ArrayList<Restaurant> restaurants = new ArrayList<Restaurant>();

	private ArrayAdapter<String> adapt = null;

	private Button nameButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		nameButton = (Button)findViewById(R.id.namebutton);
		nameButton.setOnClickListener(this);

		//texted = (TextView)findViewById(R.id.hello);
		texted = (ListView)findViewById(R.id.hello);
		
		//get ArrayList from intent object
		//restaurantnames = getIntent().getStringArrayListExtra("list");

		Bundle bundleObject = getIntent().getExtras();
		restaurants = (ArrayList<Restaurant>) bundleObject.getSerializable("restaurantlist");
		for (int i=0; i<restaurants.size(); i++) {
			String name = restaurants.get(i).getName();
			restaurantnames.add(name);
		}
		
		//write data to UI
		/*for (int i=0; i<restaurants.size(); i++) {
			//texted.append(restaurants.get(i) + "\n");
			texted.append(restaurants.get(i) + "\n");
		}*/
		adapt = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,restaurantnames);
		//adapt = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,restaurants);
		texted.setAdapter(adapt);
		adapt.notifyDataSetChanged();
	}

	public void onClick(View v) {
		Toast.makeText(UseData.this, "We're in use data activity.", Toast.LENGTH_LONG).show();
	}

}
