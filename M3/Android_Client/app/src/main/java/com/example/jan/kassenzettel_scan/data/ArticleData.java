package com.example.jan.kassenzettel_scan.data;

import java.io.Serializable;
import java.util.ArrayList;

public class ArticleData implements Serializable{

    private static final String TAG = "ArticleData";

    private String id, name, currency;
    private double priceTotal, priceSingle;
    private int amount, participants;
    private ArrayList<String> participationIds;

    public ArticleData(String id, String name, String currency,  double priceTotal, double priceSingle, int amount,
                       int participants, ArrayList<String> participationIds) {

        this.id = id;
        this.name = name;
        this.currency = currency;
        this.priceTotal = priceTotal;
        this.priceSingle = priceSingle;
        this.amount = amount;
        this.participants = participants;
        this.participationIds = participationIds;
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

    public ArrayList<String> getParticipationIds() {
        return participationIds;
    }

    public String getCurrency() {
        return currency;
    }
}
