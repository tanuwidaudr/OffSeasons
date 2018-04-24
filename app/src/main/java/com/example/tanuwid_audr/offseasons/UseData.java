package com.example.tanuwid_audr.offseasons;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class UseData extends Activity implements View.OnClickListener, Serializable, AdapterView.OnItemClickListener {

	private ArrayList<String> restaurantnames = new ArrayList<String>();
	private ArrayList<Restaurant> restaurants = new ArrayList<Restaurant>();
	private ListView listView;
	private EditText searchbox;

	private ArrayAdapter<String> adapt = null;

	private Button nameButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       listView = (ListView) findViewById(R.id.hello);
       listView.setOnItemClickListener(this);

        nameButton = (Button) findViewById(R.id.namebutton);
        nameButton.setOnClickListener(this);

        Bundle bundleObject = getIntent().getExtras();
        restaurants = (ArrayList<Restaurant>) bundleObject.getSerializable("restaurantlist");
        for (int i = 0; i < restaurants.size(); i++) {
            String name = restaurants.get(i).getName();

            restaurantnames.add(name);
        }

        adapt = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, restaurantnames) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                TextView item = (TextView) super.getView(position, convertView, parent);
                item.setTextColor(Color.parseColor("#334EFF"));
                return item;
            }
        };

        listView.setAdapter(adapt);
        adapt.notifyDataSetChanged();

        searchbox = (EditText) findViewById(R.id.searchbox);


    }

	public void onClick(View v) {
		Toast.makeText(UseData.this, "We're in use data activity.", Toast.LENGTH_LONG).show();
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
        Restaurant restaurant = restaurants.get(position);
        Log.e("JDBC", restaurant + " ");
        searchbox.setText(restaurant.getName());
	    Intent individualView = new Intent(UseData.this, IndividualView.class);
	    Bundle bundle = new Bundle();
	    bundle.putSerializable("restaurant", restaurant);
	    individualView.putExtras(bundle);
	    startActivity(individualView);

    }

}
