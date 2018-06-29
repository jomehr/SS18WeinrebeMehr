package com.example.jan.kassenzettel_scan.data;

import android.util.Log;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class ReceiptData implements Serializable{

    private static final String TAG = "ReceiptData";

    private String id, owner, storeName, currency;
    private String date;
    private Date realDate;
    private int numberArticles;
    private double totalAmount, paidAmount, changeAmount;
    private ArrayList<String> articleIds;

    public ReceiptData(String id, String owner, String storeName, String date, int numberArticles, ArrayList<String> articleIds,
                       double totalAmount, double paidAmount, double changeAmount, String currency) {

        this.id = id;
        this.owner = owner;
        this.storeName = storeName;
        setDate(date);
        this.numberArticles = numberArticles;
        this.articleIds = articleIds;
        this.totalAmount = totalAmount;
        this.paidAmount = paidAmount;
        this.changeAmount = changeAmount;
        this.currency = currency;
    }

    //Setter
    public void setDate(String date) {
        try {
            this.realDate = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.S'Z'",Locale.GERMANY).parse(date);
            Log.d(TAG, "RealDate: "+realDate.toString());
            this.date = new SimpleDateFormat("dd.MM.yyyy", Locale.GERMANY).format(realDate);
            Log.d(TAG, "StringDate: "+ date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

/*
    public void setStoreName(String storeName) {
        this.storeName = storeName;
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
*/

    //Getter
    public String getId () {
        return id;
    }

    public String getUserFirstName() {
        return owner.split("\\s+")[0];
    }

    public String getUserFullName() {
        return owner;
    }

    public String getStoreName() {
        return storeName;
    }

    public String getDateString() {
        return date.split("T")[0];
    }

    public Date getRealDate() {
        return realDate;
    }

    public int getNumberArticles() {
        return numberArticles;
    }

    public ArrayList<String> getArticleIds() {
        return articleIds;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public double getPaidAmount() {
        return paidAmount;
    }

    public double getChangeAmount() {
        return changeAmount;
    }

    public String getCurrency() {
        return currency;
    }
}
