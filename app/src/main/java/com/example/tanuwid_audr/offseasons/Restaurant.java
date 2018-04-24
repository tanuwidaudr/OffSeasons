package com.example.tanuwid_audr.offseasons;

import java.io.Serializable;
import java.util.List;

/**
 * Created by TANUWID_AUDR on 4/19/2018.
 */

public class Restaurant implements Serializable {

    private int id;
    private String name;
    private String address;
    private String city;
    private String state;
    private String zip;
    private String phone;
    private String website;
    private List<String> categories;
    private double latitude;
    private double longitude;

    public int getId() { return id;}
    public void setId(int id) {this.id = id;}

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getCity() { return city; }
    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }
    public void setState(String state) {
        this.state = state;
    }

    public String getZip() { return zip; }
    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getWebsite() {
        return website;
    }
    public void setWebsite(String website) {
        this.website = website;
    }

    public List<String> getCategories() {return categories;}
    public void setCategories(List<String> categories) {this.categories = categories;}

    public double getLatitude() {return latitude;}
    public void setLatitude(double latitude) {this.latitude = latitude;}

    public double getLongitude() {return longitude;}
    public void setLongitude(double longitude) {this.longitude = longitude;}

    public Restaurant(int id, String name, String address, String city, String state, String zip, String phone, String website) {
        super();
        this.id = id;
        this.name = name;
        this.address = address;
        this.city = city;
        this.state = state;
        this.zip = zip;
        this.phone = phone;
        this.website = website;
        this.categories = categories;
    }

    @Override
    public String toString() {
        return "Restaurant: " + name + ", " + address + ", " +
                city + ", " + state + ", " + zip + ", " +
                phone + ", " + website;
    }

}
