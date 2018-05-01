package com.example.tanuwid_audr.offseasons;

/**
 * Created by TANUWID_AUDR on 4/30/2018.
 */


import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.view.View.OnClickListener;

public class UseDataAdapter extends BaseAdapter {

    Context mContext;
    LayoutInflater inflater;
    private List<Restaurant> newrestaurantlist = null;
    private ArrayList<Restaurant> arraylist;

    public void ListViewAdapter(Context context, List<Restaurant> newrestaurantlist) {
        mContext = context;
        inflater = LayoutInflater.from(mContext);
        this.arraylist = new ArrayList<Restaurant>();
        this.arraylist.addAll(newrestaurantlist);
    }

    public class ViewHolder {
        TextView RestaurantName;
        TextView Category;
        TextView Address;
        TextView Address2;
        TextView Phone;
    }

    @Override
    public int getCount() {
        return newrestaurantlist.size();
    }

    @Override
    public Restaurant getItem(int position) {
        return newrestaurantlist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View view, ViewGroup parent) {
        final ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();
            view = inflater.inflate(R.layout.individual_layout, null);
            // Locate the TextViews in individual_layout.xml
            holder.RestaurantName = (TextView) view.findViewById(R.id.RestaurantName);
            holder.Category = (TextView) view.findViewById(R.id.Category);
            holder.Address = (TextView) view.findViewById(R.id.Address);
            holder.Address2 = (TextView) view.findViewById(R.id.Address2);
            holder.Phone = (TextView) view.findViewById(R.id.Phone);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        // Set the results into TextViews
        String city = newrestaurantlist.get(position).getCity();
        String state = newrestaurantlist.get(position).getState();
        String zip = newrestaurantlist.get(position).getZip();
        String address2 = city + ", " + state + ", " + zip;

        List<String> categories = newrestaurantlist.get(position).getCategories();
        String catString = null;
        for (int a = 0; a < categories.size(); a++) {
            if (a == 0) {
                catString = categories.get(a);
            } else {
                catString = catString + ", " + categories.get(a);
            }
        }

        holder.RestaurantName.setText(newrestaurantlist.get(position).getName());
        holder.Category.setText(catString);
        holder.Address.setText(newrestaurantlist.get(position).getAddress());
        holder.Address2.setText(address2);
        holder.Phone.setText(newrestaurantlist.get(position).getPhone());

        // Listen for ListView Item Click
        view.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // Send single item click data to SingleItemView Class
                Intent intent = new Intent(mContext, IndividualView.class);
                // Pass all data Name
                intent.putExtra("name",(newrestaurantlist.get(position).getName()));
                // Pass all data Category
                List<String> categories = newrestaurantlist.get(position).getCategories();
                String catString = null;
                for (int a = 0; a < categories.size(); a++) {
                    if (a == 0) {
                        catString = categories.get(a);
                    } else {
                        catString = catString + ", " + categories.get(a);
                    }
                }
                intent.putExtra("category",catString);
                // Pass all data Address
                intent.putExtra("address",(newrestaurantlist.get(position).getAddress()));
                // Pass all data Address2
                String city = newrestaurantlist.get(position).getCity();
                String state = newrestaurantlist.get(position).getState();
                String zip = newrestaurantlist.get(position).getZip();
                String address2 = city + ", " + state + ", " + zip;
                intent.putExtra("address2", address2);
                // Pass all data Phone
                intent.putExtra("phone",(newrestaurantlist.get(position).getPhone()));
                // Pass all data flag
                // Start SingleItemView Class
                mContext.startActivity(intent);
            }
        });

        return view;
    }

    // Filter Class
    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        newrestaurantlist.clear();
        if (charText.length() == 0) {
            newrestaurantlist.addAll(arraylist);
        }
        else
        {
            for (Restaurant restaurant : arraylist)
            {
                if (restaurant.getName().toLowerCase(Locale.getDefault()).contains(charText))
                {
                    newrestaurantlist.add(restaurant);
                }
            }
        }
        notifyDataSetChanged();
    }

}
