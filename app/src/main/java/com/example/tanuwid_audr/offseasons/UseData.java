package com.example.tanuwid_audr.offseasons;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
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
import java.util.Random;
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

    // Variables for notificationManager
    private NotificationManager notificationManager;
    private Notification notification;
    private Restaurant restaurant;
    private int restaurantForNotification;
    private int notificationId;
    private String contentTitle;
    private String contentText;
    private String tickerText;
    private NotificationCompat.Builder mBuilder = null;
    private int SIMPLE_NOTFICATION_ID = 25;


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

        // Generate random restaurant for recommendation notification
        Random rand = new Random();
        restaurantForNotification = rand.nextInt(40);
        restaurant = restaurantlist.get(restaurantForNotification);

        // Define notification variables
        contentTitle = "New Recommendation";
        contentText = "Try " + restaurant.getName();
        tickerText = "New Recommendation";

        // Generate notification manager, intent, and bundle
        notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        //As of API 26 Notification Channels must be assigned to a channel
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("default",
                    "Channel foobar",
                    NotificationManager.IMPORTANCE_HIGH);
            channel.setDescription("Channel description");
            //channel.setLightColor(Color.GREEN);
            //channel.enableVibration(true);
            //channel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
            notificationManager.createNotificationChannel(channel);
        }
            Intent notifyIntent = new Intent(this, IndividualView.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("restaurant", restaurant);
            notifyIntent.putExtras(bundle);


            //create pending intent to wrap intent so that it
            //will fire when notification selected.
            PendingIntent pendingIntent = PendingIntent.getActivity(
                    this, 0, notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT);

            //build notificationManager object and set parameters
            mBuilder =
                    new NotificationCompat.Builder(this, "default")
                            .setContentIntent(pendingIntent)

                            .setContentTitle(contentTitle)   //set Notification text and icon
                            .setContentText(contentText)
                            .setSmallIcon(R.drawable.offseasons_logo)

                            .setTicker(tickerText)            //set status bar text

                            .setWhen(System.currentTimeMillis())    //timestamp when event occurs

                            .setAutoCancel(true)     //cancel Notification after clicking on it

                            .setPriority(NotificationCompat.PRIORITY_HIGH);

            notificationManager.notify(SIMPLE_NOTFICATION_ID,
                    mBuilder.build());
        }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.namebutton:
                Toast.makeText(UseData.this, "Currently using this activity.", Toast.LENGTH_SHORT).show();
                break;
            case R.id.categorybutton:
                Toast.makeText(UseData.this, "Loading Categories", Toast.LENGTH_SHORT).show();
                Delay delay = new Delay();
                Timer timer = new Timer();
                timer.schedule(delay, 2000);
                break;
        }
    }


    @Override
	public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
        restaurant = restaurantlist.get(position);
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
