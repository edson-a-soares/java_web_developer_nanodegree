package com.udacity.vehicles.infrastructure.client.maps;

/**
 * Declares a class to store an address, city, state and zip code.
 */
public class Address {

    private String zip;
    private String city;
    private String state;
    private String address;

    public Address() {}

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
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

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

}
