package com.example.jan.kassenzettel_scan.data;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;

public class ReceiptData {

    private String user, storeName, currency;
    private String date;
    private int numberArticles;
    private double totalAmount;

    private SimpleDateFormat df = new SimpleDateFormat("dd.MM.yyyy");


    public ReceiptData(String user, String storeName, String date, int numberArticles,
                       double totalAmount, String currency) {
        this.user = user;
        this.storeName = storeName;
        Log.d("DATE", date);
        setDate(date);
        this.numberArticles = numberArticles;
        this.totalAmount = totalAmount;
        this.currency = currency;
    }

    //Setter
    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public void setDate(String date) {
        Date tmpDate= null;
        try {
            tmpDate = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.S'Z'",Locale.GERMANY).parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        this.date = new SimpleDateFormat("dd.MM.yyyy", Locale.GERMANY).format(tmpDate);
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
        return user.split("\\s+")[0];
    }

    public String getStoreName() {
        return storeName;
    }

    public String getDate() {
        return date.split("T")[0];
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
