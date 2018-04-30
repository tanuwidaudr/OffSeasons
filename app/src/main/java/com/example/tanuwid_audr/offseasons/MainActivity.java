package com.example.tanuwid_audr.offseasons;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.os.Bundle;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;
import java.sql.*;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends Activity implements View.OnClickListener, Serializable {

    private Thread t = null;
    private ArrayList<Restaurant> restaurantlist = new ArrayList<Restaurant>();
    private ArrayList<Category> categoryList = new ArrayList<Category>();

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
    private String category;


    private Button nameButton;
    private Button catButton;
    private ImageView imageView;


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
                Toast.makeText(MainActivity.this, "Loading Restaurants", Toast.LENGTH_LONG).show();
                Intent intent1 = new Intent(MainActivity.this, UseData.class);
                //intent1.putStringArrayListExtra("list", list);
                Bundle bundle = new Bundle();
                bundle.putSerializable("restaurantlist", restaurantlist);
                Bundle bundle4 = new Bundle();
                bundle4.putSerializable("categoryList", categoryList);
                intent1.putExtras(bundle);
                intent1.putExtras(bundle4);
                startActivity(intent1);
                break;
            case R.id.landingPageCategory:
                Toast.makeText(MainActivity.this, "Loading Categories", Toast.LENGTH_LONG).show();
                Delay delay = new Delay();
                Timer timer = new Timer();
                Log.e("Timer", "Timer has been created");
                timer.schedule(delay, 2000);

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

                // Retrieve all restaurants
                stmt = con.createStatement();
                ResultSet result = stmt.executeQuery("select * from restaurants order by RestaurantName;");

                //Retrieve categories from specified restaurant
                PreparedStatement restCats = null;
                String SQL = "SELECT CategoryName FROM Category INNER JOIN RestaurantCategory ON Category.CategoryID = RestaurantCategory.Category INNER JOIN Restaurants ON RestaurantCategory.Restaurant = Restaurants.RestaurantID WHERE Restaurants.RestaurantID=? ORDER BY CategoryName";
                restCats = con.prepareStatement(SQL);


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

                // Retrieve all categories
                stmt = con.createStatement();
                ResultSet allCategories = stmt.executeQuery("SELECT * FROM Category ORDER BY CategoryName;");

                while(allCategories.next()) {
                    category = allCategories.getString("CategoryName");
                    Category newCategory = new Category(category);
                    categoryList.add(newCategory);
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

                // SQL Query for Retrieving restaurant locations
                PreparedStatement getLocation = null;
                String SQL2 = "SELECT Latitude, Longitude FROM Location WHERE Restaurant=?";
                getLocation = con.prepareStatement(SQL2);

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

    class Delay extends TimerTask {
        @Override
        public void run() {
            Log.e("Timer", "Timer code has been accessed");

            Intent intent2 = new Intent (MainActivity.this, CategoryView.class);
            Bundle bundle2 = new Bundle();
            bundle2.putSerializable("categoryList", categoryList);
            Bundle bundle3 = new Bundle();
            bundle3.putSerializable("restaurantlist", restaurantlist);
            intent2.putExtras(bundle2);
            intent2.putExtras(bundle3);
            startActivity(intent2);
                }
        }

    }

