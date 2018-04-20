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

import java.util.ArrayList;
import java.sql.*;

public class MainActivity extends Activity implements View.OnClickListener {

    private Thread t = null;
    private ArrayList<String> list = new ArrayList<String>();

    //restaurant variables
    private String name;
    private String address;
    private String city;
    private String state;
    private String zip;
    private String phone;
    private String website;

    private Button nameButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nameButton = (Button)findViewById(R.id.namebutton);
        nameButton.setOnClickListener(this);

        //Start Thread
        t = new Thread(background);
        t.start();

    }

    public void onClick(View v) {
        Toast.makeText(MainActivity.this, "We're in main activity.", Toast.LENGTH_LONG).show();
        Intent intent1 = new Intent(MainActivity.this, UseData.class);
        intent1.putStringArrayListExtra("list", list);
        startActivity(intent1);
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
            Connection con = null;

            //create connection and statement objects
            try {
                con = DriverManager.getConnection(URL, username, password);
                stmt = con.createStatement();

                ResultSet result = stmt.executeQuery("select * from restaurants order by RestaurantName;");

                //read result set, write data to ArrayList and Log
                while (result.next()) {
                   name = result.getString("RestaurantName");
                   address = result.getString("StreetAddress");
                   city = result.getString("City");
                   state = result.getString("State");
                   zip = result.getString("ZIP");
                   phone = result.getString("Phone");
                   website = result.getString("Website");
                   Restaurant restaurant = new Restaurant(name, address, city, state, zip, phone, website);
                   list.add(restaurant.getName());
                   Log.e("JDBC", name + " added." );
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
                Toast.makeText(MainActivity.this, "Problems with SQL sent to URL", Toast.LENGTH_SHORT).show();
            }

            finally {
                try {
                    if (con != null)
                        con.close();
                } catch (SQLException e) {
                    Log.e("JDBC", "close connection failed");
                    Toast.makeText(MainActivity.this, "Close connection failed", Toast.LENGTH_SHORT).show();
                }
            }

        }
    };

}
