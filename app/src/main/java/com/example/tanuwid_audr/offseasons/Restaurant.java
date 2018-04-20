package com.example.tanuwid_audr.offseasons;

/**
 * Created by TANUWID_AUDR on 4/19/2018.
 */

public class Restaurant {

    private String name;
    private String address;
    private String city;
    private String state;
    private String zip;
    private String phone;
    private String website;

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getcity() {
        return city;
    }
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

    public Restaurant(String name, String address, String city, String state, String zip, String phone, String website) {
        super();
        this.name = name;
        this.address = address;
        this.city = city;
        this.state = state;
        this.zip = zip;
        this.phone = phone;
        this.website = website;
    }

    @Override
    public String toString() {
        return "Restaurant: " + name + ", " + address + ", " +
                city + ", " + state + ", " + zip + ", " +
                phone + ", " + website;
    }

}
