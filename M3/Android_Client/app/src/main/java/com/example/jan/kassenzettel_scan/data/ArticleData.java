package com.example.jan.kassenzettel_scan.data;

import java.io.Serializable;

public class ArticleData implements Serializable{

    private static final String TAG = "ArticleData";

    private String id, name, currency;
    private double priceTotal, priceSingle;
    private int amount, participants;

    public ArticleData(String id, String name, String currency,  double priceTotal, double priceSingle, int amount,
                       int participants) {
        this.id = id;
        this.name = name;
        this.currency = currency;
        this.priceTotal = priceTotal;
        this.priceSingle = priceSingle;
        this.amount = amount;
        this.participants = participants;
    }

    public ArticleData (String currency, int participants) {
        this.currency = currency;
        this.participants = participants;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNumberArticles(int amount) {
        this.amount = amount;
    }

    public void setPriceTotal(double priceTotal) {
        this.priceTotal = priceTotal;
    }

    public void setPriceSingle(double priceSingle) {
        this.priceSingle = priceSingle;
    }

    public void setNumberParticipants(int participants) {
        this.participants = participants;
    }

    public int getNumberArticles() {
        return amount;
    }

    public String getId() {
        return id;
    }

    public String getArticleName() {
        return name;
    }

    public double getPriceTotal() {
        return priceTotal;
    }

    public double getPriceSingle() {
        return priceSingle;
    }

    public int getParticipants() {
        return participants;
    }

    public String getCurrency() {
        return currency;
    }
}
