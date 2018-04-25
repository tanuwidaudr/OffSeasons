package com.example.tanuwid_audr.offseasons;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by JERVEY_SAMU on 4/24/2018.
 */

public class CategoryView extends Activity implements AdapterView.OnItemClickListener, View.OnClickListener {
    ArrayList<Category> imports = new ArrayList<Category>();
    ArrayList<Restaurant> restaurantlist = new ArrayList<Restaurant>();
    ArrayList<String> categories = new ArrayList<String>();
    private ArrayAdapter<String> adapt = null;

    ListView categoryList;
    Button name;
    Button category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.category_layout);

        Bundle bundleObject = getIntent().getExtras();
        imports = (ArrayList<Category>) bundleObject.getSerializable("categoryList");
        restaurantlist = (ArrayList<Restaurant>) bundleObject.getSerializable("restaurantlist");

        for(int i=0; i < imports.size(); i++) {
            categories.add(imports.get(i).getCategoryName());

        }

        categoryList = (ListView) findViewById(R.id.categoryList) ;
        categoryList.setOnItemClickListener(this);

        name = (Button) findViewById(R.id.namebutton2);
        name.setOnClickListener(this);

        category = (Button) findViewById(R.id.categorybutton2);
        category.setOnClickListener(this);

        adapt = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, categories) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                TextView item = (TextView) super.getView(position, convertView, parent);
                item.setTextColor(Color.parseColor("#334EFF"));
                return item;
            }
        };

        categoryList.setAdapter(adapt);
        adapt.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
    // Retrieves selected restaurant, sends it to new activity
    Category selectedCategory = imports.get(i);
    Intent startFilterView = new Intent(CategoryView.this, FilterByCategory.class);
    Bundle bundle = new Bundle();
    bundle.putSerializable("selectedCategory", selectedCategory);
    startFilterView.putExtras(bundle);
    startActivity(startFilterView);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.categorybutton2:
                Toast.makeText(this, "Currently using activity", Toast.LENGTH_LONG).show();
                break;
            case R.id.namebutton2:
                Toast.makeText(this, "Loading Restaurants", Toast.LENGTH_LONG).show();
                Intent newIntent = new Intent(CategoryView.this, UseData.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("restaurantlist",restaurantlist);
                newIntent.putExtras(bundle);
                startActivity(newIntent);
                break;
        }
    }
}

