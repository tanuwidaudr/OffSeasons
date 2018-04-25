package com.example.tanuwid_audr.offseasons;

import android.annotation.SuppressLint;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.ActionBar;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by JERVEY_SAMU on 4/21/2018.
 */

public class IndividualView extends AppCompatActivity implements Serializable, TextToSpeech.OnInitListener, View.OnClickListener {
    private Restaurant imports;
    private Restaurant restaurant;

    private TextView namefield;
    private TextView categoriesfield;
    private TextView addressfield1;
    private TextView addressfield2;
    private TextView phonefield;

    private ImageButton dial;
    private ImageButton web;
    private ImageButton map;

    private String phone;
    private double latitude;
    private double longitude;

    private TextToSpeech speaker;
    private static final String tag = "Widgets";
    private String name;
    private String website;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.individual_layout);

        Bundle bundleObject = getIntent().getExtras();
        imports = (Restaurant) bundleObject.getSerializable("restaurant");

        //Initialize Text to Speech engine (context, listener object)
        speaker = new TextToSpeech(this, this);


        // Extract Data from bundle
        int id = imports.getId();
        name = imports.getName();
        String address = imports.getAddress();

        String city = imports.getCity();
        String state = imports.getState();
        String zip = imports.getZip();

        String address2 = city + ", " + state + ", " + zip;

        phone = imports.getPhone();
        String formattedPhone = "(" + phone.substring(0,3) + ") " + phone.substring(3,7) + "-" + phone.substring(7);

        website = imports.getWebsite();
        latitude = imports.getLatitude();
        longitude = imports.getLongitude();

        //Move categories from List to String
        List<String> categories = imports.getCategories();
        String catString = null;
        for (int a = 0; a < categories.size(); a++) {
            if (a == 0) {
                catString = categories.get(a);
            } else {
                catString = catString + ", " + categories.get(a);
            }

        speak("Loading " + name + "'s profile");
        // Extract fields from XML
        namefield = (TextView)  findViewById(R.id.RestaurantName);
        addressfield1 = (TextView) findViewById(R.id.Address);
        addressfield2 = (TextView) findViewById(R.id.Address2);
        phonefield = (TextView) findViewById(R.id.Phone);
        categoriesfield = (TextView) findViewById(R.id.Category);

        //Display data
        namefield.setText(name);
        addressfield1.setText(address);
        addressfield2.setText(address2);
        phonefield.setText(formattedPhone);
        categoriesfield.setText(catString);
        }

        // Set Listeners on Buttons
        dial = (ImageButton) findViewById(R.id.PhoneButton);
        dial.setOnClickListener(this);

        web = (ImageButton) findViewById(R.id.WebButton);
        web.setOnClickListener(this);

        map = (ImageButton) findViewById(R.id.MapButton);
        map.setOnClickListener(this);

        //hide title and icon in action bar
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayUseLogoEnabled(false);
    }

    //speaks the contents of output
    public void speak(String output){
        speaker.speak(output, TextToSpeech.QUEUE_FLUSH, null);  //for APIs before 21
        // speaker.speak(output, TextToSpeech.QUEUE_FLUSH, null, "Id 0");
    }

    // Implements TextToSpeech.OnInitListener.
    public void onInit(int status) {
        // status can be either TextToSpeech.SUCCESS or TextToSpeech.ERROR.
        if (status == TextToSpeech.SUCCESS) {
            // Set preferred language to US english.
            // If a language is not be available, the result will indicate it.
            int result = speaker.setLanguage(Locale.US);

            //int result = speaker.setLanguage(Locale.FRANCE);
            if (result == TextToSpeech.LANG_MISSING_DATA ||
                    result == TextToSpeech.LANG_NOT_SUPPORTED) {
                // Language data is missing or the language is not supported.
                Log.e(tag, "Language is not available.");
            } else {
                // The TTS engine has been successfully initialized
                Log.i(tag, "TTS Initialization successful.");
            }
        } else {
            // Initialization failed.
            Log.e(tag, "Could not initialize TextToSpeech.");
        }
    }

    // When buttons are clicked
    public void onClick (View v) throws SecurityException {
        switch(v.getId()) {
            case R.id.PhoneButton:
                speak("Calling " + name);
                new Handler().postDelayed(new Runnable() {
                    @SuppressLint("MissingPermission")
                    public void run() {
                        Uri uri1 = Uri.parse("tel:" + phone);
                        Intent call = new Intent(Intent.ACTION_CALL, uri1);
                        startActivity(call);
                    }
                }, 3000);
                break;
            case R.id.MapButton:
                Uri uri2 = Uri.parse("geo:"+latitude+","+longitude+"?q="+latitude+","+longitude+"("+name+")?z=18");
                Intent map = new Intent(Intent.ACTION_VIEW,uri2);
                if (map.resolveActivity(getPackageManager()) != null) {startActivity(map);}
                speak("Finding " + name + " on Google maps.");

                break;
            case R.id.WebButton:
                Intent web = new Intent(this, WebLookUp.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("website", website);
                web.putExtras(bundle);
                startActivity(web);
                speak("Finding " + name + " online");
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {

            case R.id.home:

                return true;

            case R.id.exit:
                finishAffinity();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    }
