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
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by JERVEY_SAMU on 4/24/2018.
 */

public class FilterByCategory extends Activity implements AdapterView.OnItemClickListener {
    //Variables to import from bundle
    private Category importedCategory;
    private String categoryName;
    private ArrayList<Restaurant> restaurantList = new ArrayList<Restaurant>();


    private ArrayList<Restaurant> matches = new ArrayList<Restaurant>();
    private ArrayList<String> matchNames = new ArrayList<String>();

    private TextView categoryTitle;
    private ListView filteredList;
    private ArrayAdapter<String> adapt = null;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.filtered_list_actvity);

        //Import from bundle
        Bundle bundleObject = getIntent().getExtras();
        importedCategory = (Category) bundleObject.getSerializable("selectedCategory");
        restaurantList = (ArrayList<Restaurant>) bundleObject.getSerializable("restaurantlist");
        categoryName = importedCategory.getCategoryName();

        // Establish variables from XML
        categoryTitle = (TextView) findViewById(R.id.categoryTitle);
        categoryTitle.setText(categoryName);
        filteredList = (ListView) findViewById(R.id.filteredList);

        //Set OnItemClickListener
        filteredList.setOnItemClickListener(this);


        for (int i = 0; i < restaurantList.size(); i++) {
            if(restaurantList.get(i).getCategories().contains(categoryName)) {
                matches.add(restaurantList.get(i));
                Log.e("JDBC",restaurantList.get(i).getName() + " added");
            }
        }

        for (int a = 0; a < matches.size(); a++) {
            matchNames.add(matches.get(a).getName());
        }

        adapt = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, matchNames) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                TextView item = (TextView) super.getView(position, convertView, parent);
                item.setTextColor(Color.parseColor("#334EFF"));
                return item;
            }
        };

        filteredList.setAdapter(adapt);
        adapt.notifyDataSetChanged();


    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        // Retrieves selected restaurant, sends it to new activity
        Restaurant restaurant = matches.get(i);
        Intent loadIndividualView = new Intent(FilterByCategory.this, IndividualView.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("restaurant", restaurant);
        loadIndividualView.putExtras(bundle);
        startActivity(loadIndividualView);
        Log.e("JDBC", restaurant + " sent");

    }

}
