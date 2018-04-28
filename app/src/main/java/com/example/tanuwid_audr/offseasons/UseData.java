package com.example.tanuwid_audr.offseasons;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class UseData extends Activity implements View.OnClickListener, Serializable, AdapterView.OnItemClickListener {

	private ArrayList<String> restaurantnames = new ArrayList<String>();
	private ArrayList<Restaurant> restaurantlist = new ArrayList<Restaurant>();
	private ArrayList<Category> categoryList = new ArrayList<Category>();

	private ListView listView;
	private EditText searchbox;

	//filter variables
    private Button nameButton, categoryButton;

	private ArrayAdapter<String> adapt = null;
	private Delay delay = new Delay();



	@Override
	protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       listView = (ListView) findViewById(R.id.hello);
       listView.setOnItemClickListener(this);

        nameButton = (Button) findViewById(R.id.namebutton);
        nameButton.setOnClickListener(this);

        categoryButton = (Button) findViewById(R.id.categorybutton);
        categoryButton.setOnClickListener(this);

        searchbox = (EditText) findViewById(R.id.searchbox);

        Bundle bundleObject = getIntent().getExtras();
        restaurantlist = (ArrayList<Restaurant>) bundleObject.getSerializable("restaurantlist");
        for (int i = 0; i < restaurantlist.size(); i++) {
            String name = restaurantlist.get(i).getName();

            restaurantnames.add(name);
        }

        categoryList = (ArrayList<Category>) bundleObject.getSerializable("categoryList");

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
        searchbox.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                (UseData.this).adapt.getFilter().filter(s);
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });


    }

	public void onClick(View v) {
		switch (v.getId()) {
            case R.id.namebutton:
                Toast.makeText(UseData.this, "Currently using this activity.", Toast.LENGTH_SHORT).show();
                break;
            case R.id.categorybutton:
                Toast.makeText(UseData.this, "Loading Categories", Toast.LENGTH_SHORT).show();
                Timer timer = new Timer();
                timer.schedule(delay, 3000);
                break;
        }
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
        Restaurant restaurant = restaurantlist.get(position);
        Log.e("JDBC", restaurant + " ");
        //searchbox.setText(restaurant.getName());
	    Intent individualView = new Intent(UseData.this, IndividualView.class);
	    Bundle bundle = new Bundle();
	    bundle.putSerializable("restaurant", restaurant);
	    individualView.putExtras(bundle);
	    startActivity(individualView);

    }

   class Delay extends TimerTask {
	    @Override
        public void run() {
            Intent loadCategories = new Intent(UseData.this, CategoryView.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("categoryList", categoryList);
            loadCategories.putExtras(bundle);
            Bundle bundle2 = new Bundle();
            bundle2.putSerializable("restaurantlist", restaurantlist);
            loadCategories.putExtras(bundle2);
            startActivity(loadCategories);

        }

   }



}
