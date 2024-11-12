package com.example.firebaseandroidproject;

public class Cafe {
    // Sesuaikan nama variabel dengan data Firestore
    private String id;
    private String nameET;
    private String descET;
    private String picET;
    private String priceET;
    private String locationET;
    private String ratingET;

    public Cafe() {
        // Constructor kosong diperlukan oleh Firestore
    }

    // Getter dan Setter untuk field sesuai dengan data Firestore
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNameET() {
        return nameET;
    }

    public void setNameET(String nameET) {
        this.nameET = nameET;
    }

    public String getDescET() {
        return descET;
    }

    public void setDescET(String descET) {
        this.descET = descET;
    }

    public String getPicET() {
        return picET;
    }

    public void setPicET(String picET) {
        this.picET = picET;
    }

    public String getPriceET() {
        return priceET;
    }

    public void setPriceET(String priceET) {
        this.priceET = priceET;
    }

    public String getLocationET() {
        return locationET;
    }

    public void setLocationET(String locationET) {
        this.locationET = locationET;
    }

    public String getRatingET() {
        return ratingET;
    }

    public void setRatingET(String ratingET) {
        this.ratingET = ratingET;
    }
}
