package com.example.jan.kassenzettel_scan.data;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ReceiptData {

    private String user, storeName, currency;
    private String date;
    private int numberArticles;
    private double totalAmount;

    DateFormat df = new SimpleDateFormat("dd.MM.yyyy", Locale.GERMANY);


    public ReceiptData(String user, String storeName, String date, int numberArticles,
                       double totalAmount, String currency) {
        this.user = user;
        this.storeName = storeName;
        this.date = date;
        this.numberArticles = numberArticles;
        this.totalAmount = totalAmount;
        this.currency = currency;
    }

    //Setter
    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setNumberArticles(int numberArticles) {
        this.numberArticles = numberArticles;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    //Getter
    public String getUser() {
        return user;
    }

    public String getStoreName() {
        return storeName;
    }

    public String getDate() {
        return date;
    }

    public int getNumberArticles() {
        return numberArticles;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public String getCurrency() {
        return currency;
    }
}
