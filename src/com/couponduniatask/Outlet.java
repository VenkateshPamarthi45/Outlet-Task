package com.couponduniatask;

import java.util.ArrayList;

public class Outlet {
	
	private String title, thumbnailUrl,location;
	private int offers;
	private double latitudenum, longitudenum, distancenum;
	private ArrayList<String> categories;

public Outlet() {
}

public Outlet(String name, String thumbnailUrl, int offers, String location,
        ArrayList<String> categories) {
    this.title = name;
    this.thumbnailUrl = thumbnailUrl;
    this.offers = offers;
    this.location = location;
    this.categories = categories;
}

public String getTitle() {
    return title;
}

public void setTitle(String name) {
    this.title = name;
}

public String getThumbnailUrl() {
    return thumbnailUrl;
}

public void setThumbnailUrl(String thumbnailUrl) {
    this.thumbnailUrl = thumbnailUrl;
}

public int getOffers() {
	return offers;
}

public void setOffers(int offers) {
    this.offers = offers;
}

public double getLatitude() {
	return latitudenum;
}

public void setLatitude(double latitudenum) {
	this.latitudenum = latitudenum;
}

public void setLongitude(double longitudenum) {
	this.longitudenum = longitudenum;
}

public double getLongitude() {
	return longitudenum;
}

public double getDistance() {
	return distancenum;
}

public void setDistance(double distancenum)
{
	this.distancenum = distancenum;
}

public String getLocation() {
    return location;
}

public void setLocation(String location) {
    this.location = location;
}

public ArrayList<String> getCategories() {
    return categories;
}

public void setGenre(ArrayList<String> categories) {
    this.categories = categories;
}

}
