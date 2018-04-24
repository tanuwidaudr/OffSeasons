package com.example.tanuwid_audr.offseasons;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;
import java.sql.*;
import java.util.List;

public class MainActivity extends Activity implements View.OnClickListener, Serializable {

    private Thread t = null;
    private ArrayList<Restaurant> restaurantlist = new ArrayList<Restaurant>();

    //restaurant variables
    private int id;
    private String name;
    private String address;
    private String city;
    private String state;
    private String zip;
    private String phone;
    private String website;
    private double latitude;
    private double longitude;



    private Button nameButton;
    private Button catButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.landing_page);

        nameButton = (Button)findViewById(R.id.landingPageName);
        nameButton.setOnClickListener(this);

        catButton = (Button)findViewById(R.id.landingPageCategory);
        catButton.setOnClickListener(this);

        //Start Thread
        t = new Thread(background);
        t.start();

    }

    public void onClick(View v) {
        switch(v.getId()){
            case R.id.landingPageName:
                //Toast.makeText(MainActivity.this, "We're in main activity.", Toast.LENGTH_LONG).show();
                Intent intent1 = new Intent(MainActivity.this, UseData.class);
                //intent1.putStringArrayListExtra("list", list);
                Bundle bundle = new Bundle();
                bundle.putSerializable("restaurantlist", restaurantlist);
                intent1.putExtras(bundle);
                startActivity(intent1);
                break;
            case R.id.landingPageCategory:
                //setContentView(R.layout.individual_layout);
                break;

        }

    }

    private Runnable background = new Runnable() {
        public void run() {
            String URL = "jdbc:mysql://frodo.bentley.edu:3306/restaurants";
            String username = "CS280";
            String password = "CS280";

            //load driver into VM memory
            try {
                Class.forName("com.mysql.jdbc.Driver");
            } catch (ClassNotFoundException e) {
                Log.e("JDBC", "Did not load driver");
                //Toast.makeText(MainActivity.this, "Did not load driver", Toast.LENGTH_SHORT).show();
            }

            Statement stmt = null;
            //Statement stmt2 = null;
            Connection con = null;

            //create connection and statement objects
            try {
                con = DriverManager.getConnection(URL, username, password);
                stmt = con.createStatement();
                ResultSet result = stmt.executeQuery("select * from restaurants order by RestaurantName;");

                PreparedStatement restCats = null;
                String SQL = "SELECT CategoryName FROM Category INNER JOIN RestaurantCategory ON Category.CategoryID = RestaurantCategory.Category INNER JOIN Restaurants ON RestaurantCategory.Restaurant = Restaurants.RestaurantID WHERE Restaurants.RestaurantID=? ORDER BY CategoryName";
                restCats = con.prepareStatement(SQL);

                PreparedStatement getLocation = null;
                String SQL2 = "SELECT Latitude, Longitude FROM Location WHERE Restaurant=?";
                getLocation = con.prepareStatement(SQL2);

                //read result set, write data to ArrayList and Log
                while (result.next()) {
                   id = result.getInt("RestaurantID");
                   name = result.getString("RestaurantName");
                   address = result.getString("StreetAddress");
                   city = result.getString("City");
                   state = result.getString("State");
                   zip = result.getString("ZIP");
                   phone = result.getString("Phone");
                   website = result.getString("Website");

                    Restaurant restaurant = new Restaurant(id, name, address, city, state, zip, phone, website);
                    restaurantlist.add(restaurant);

                   Log.e("JDBC", name + " added." );
                }


                //Set RestaurantID into prepared statement
                for (int i = 0; i < restaurantlist.size(); i++) {
                    int currentID = restaurantlist.get(i).getId();
                    restCats.setInt(1, currentID);
                    ResultSet getCategories = restCats.executeQuery();
                    List<String> categories = new ArrayList<>();

                    while(getCategories.next()) {
                        String category = getCategories.getString("CategoryName");
                        categories.add(category);
                    }
                    restaurantlist.get(i).setCategories(categories);

                    Log.e("JDBC", categories + " added." );
                }

                for (int a = 0; a < restaurantlist.size(); a++) {
                    int currentID = restaurantlist.get(a).getId();
                    getLocation.setInt(1, currentID);
                    ResultSet getLatAndLong = getLocation.executeQuery();

                    while(getLatAndLong.next()) {
                        latitude = getLatAndLong.getDouble("Latitude");
                        longitude = getLatAndLong.getDouble("Longitude");
                    }

                    restaurantlist.get(a).setLatitude(latitude);
                    restaurantlist.get(a).setLongitude(longitude);
                }



                //create intent, place ArrayList on intent object,
                //request another activity to be started to use data
                //Intent intent = new Intent(MainActivity.this, UseData.class);
                //intent.putStringArrayListExtra("list", list);
                //startActivity(intent);

                //clean up
                t = null;

            } catch (SQLException e) {
                Log.e("JDBC","problems with SQL sent to "+URL+
                        ": "+e.getMessage());
                //Toast.makeText(MainActivity.this, "Problems with SQL sent to URL", Toast.LENGTH_SHORT).show();
            }

            finally {
                try {
                    if (con != null)
                        con.close();
                } catch (SQLException e) {
                    Log.e("JDBC", "close connection failed");
                    //Toast.makeText(MainActivity.this, "Close connection failed", Toast.LENGTH_SHORT).show();
                }
            }

        }
    };

}
