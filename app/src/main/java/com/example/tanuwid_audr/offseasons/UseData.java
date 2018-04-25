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

	//filter variables
    private String searchfilter;
	private ArrayList<Integer> hiddenrestaurants = new ArrayList<>();
    private Button nameButton, goButton, clearButton;

	private ArrayAdapter<String> adapt = null;



	@Override
	protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       listView = (ListView) findViewById(R.id.hello);
       listView.setOnItemClickListener(this);

        nameButton = (Button) findViewById(R.id.namebutton);
        nameButton.setOnClickListener(this);

        searchbox = (EditText) findViewById(R.id.searchbox);

        goButton = (Button) findViewById(R.id.gobutton);
        goButton.setOnClickListener(this);

        clearButton = (Button) findViewById(R.id.clearbutton);
        clearButton.setOnClickListener(this);

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
		switch (v.getId()) {
            case R.id.namebutton:
                Toast.makeText(UseData.this, "We're in use data activity.", Toast.LENGTH_LONG).show();
                break;
            case R.id.gobutton:
                //Toast.makeText(UseData.this, "Made it to go button.", Toast.LENGTH_LONG).show();
                searchfilter = searchbox.getText().toString();
                if (searchfilter.matches("")) {
                    Toast.makeText(UseData.this, "Searchbox is empty", Toast.LENGTH_LONG).show();
                }
                else {
                    //for (int i=0; i < restaurantnames.size(); i++) {
                        Toast.makeText(UseData.this, "Searchbox is filled", Toast.LENGTH_LONG).show();
                        //test
                    //}
                }
                break;
            case R.id.clearbutton:
                Toast.makeText(UseData.this, "Made it to clear button.", Toast.LENGTH_LONG).show();
                break;
        }
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
