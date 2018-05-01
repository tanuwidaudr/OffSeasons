package com.example.tanuwid_audr.offseasons;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
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

public class SearchByName extends Activity implements AdapterView.OnItemClickListener {
    //Variables to import from bundle
    private ArrayList<Restaurant> searchResults = new ArrayList<Restaurant>();
    private ArrayList<String> names = new ArrayList<String>();

    private TextView categoryTitle;
    private ListView filteredList;
    private ArrayAdapter<String> adapt = null;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_actvity);

        //Import from bundle
        Bundle bundleObject = getIntent().getExtras();
        searchResults = (ArrayList<Restaurant>) bundleObject.getSerializable("searchResults");

        filteredList = (ListView) findViewById(R.id.searchResults);

        //Set OnItemClickListener
        filteredList.setOnItemClickListener(this);

        //get names
        for(int i =0; i < searchResults.size(); i++) {
            names.add(searchResults.get(i).getName());
        }

        adapt = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, names) {
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
        Restaurant restaurant = searchResults.get(i);
        Intent loadIndividualView = new Intent(SearchByName.this, IndividualView.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("restaurant", restaurant);
        loadIndividualView.putExtras(bundle);
        startActivity(loadIndividualView);

    }

}
