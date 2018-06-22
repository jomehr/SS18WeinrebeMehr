package com.example.jan.kassenzettel_scan.tasks;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.jan.kassenzettel_scan.R;
import com.example.jan.kassenzettel_scan.adapter.ReceiptAdapter;
import com.example.jan.kassenzettel_scan.data.ReceiptData;
import com.example.jan.kassenzettel_scan.fragments.ReceiptList_Group;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

public class AsyncGetGroupReceipts extends AsyncTask<String,String, String> {

    // CONNECTION_TIMEOUT and READ_TIMEOUT are in milliseconds
    private static final int CONNECTION_TIMEOUT = 10000;
    private static final int READ_TIMEOUT = 15000;
    private ReceiptList_Group receiptListGroup;

    public AsyncGetGroupReceipts (ReceiptList_Group receiptListGroup, String id) {
        this.receiptListGroup = receiptListGroup;
    }

    @Override
    protected String doInBackground(String... params) {

        URL url;
        HttpURLConnection conn;
        try {
            url = new URL("http://192.168.0.172:8081/receipt/");
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return e.toString();
        }

        try {
            //Setup HttpURLConnection class to receive data
            conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(READ_TIMEOUT);
            conn.setConnectTimeout(CONNECTION_TIMEOUT);
            conn.setRequestMethod("GET");

            // setDoOutput to false as we recieve data from json file
            conn.setDoOutput(false);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return e.toString();
        }

        try {
            int response_code = conn.getResponseCode();
            // Check if successful connection made
            if (response_code == HttpURLConnection.HTTP_OK) {

                // Read data sent from server
                InputStream input = conn.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(input));
                StringBuilder result = new StringBuilder();
                String line;

                while ((line = reader.readLine()) != null) {
                    result.append(line);
                }

                // Pass data to onPostExecute method
                return (result.toString());

            } else {
                return ("unsuccessful");
            }

        } catch (IOException e) {
            e.printStackTrace();
            return e.toString();
        } finally {
            conn.disconnect();
        }
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        receiptListGroup.showProgressBar();
    }

    @Override
    protected void onPostExecute(String result) {
        //this method will be running on UI thread

        receiptListGroup.dismissProgressBar();

        String currentUser = "Jan";


        List<ReceiptData> dataGroup = new ArrayList<>();
        List<ReceiptData> dataUser = new ArrayList<>();

        try {
            JSONArray jArray = new JSONArray(result);

            // Extract data from json and store into ArrayList as class objects
            for(int i=0;i<jArray.length();i++){
                JSONObject json_data = jArray.getJSONObject(i);
                ReceiptData receiptData = new ReceiptData(
                        json_data.getJSONObject("owner").getString("name"),
                        json_data.getString("store"),
                        json_data.getString("date"),
                        json_data.getJSONArray("articles").length(),
                        json_data.getDouble("paid"),
                        json_data.getString("currency")
                );
                Log.d("DATA", receiptData.getUser());
                dataGroup.add(receiptData);
            }

            Iterator<ReceiptData> i = dataGroup.iterator();
            while (i.hasNext()) {
                ReceiptData receiptData = i.next();
                if (receiptData.getUser().equals(currentUser)) {
                    dataUser.add(receiptData);
                    i.remove();
                }
            }

            Collections.sort(dataGroup, new Comparator<ReceiptData>() {
                @Override
                public int compare(ReceiptData o1, ReceiptData o2) {
                    return o1.getUser().compareTo(o2.getUser());
                }
            });
            Collections.sort(dataUser, new Comparator<ReceiptData>() {
                @Override
                public int compare(ReceiptData o1, ReceiptData o2) {
                    return o1.getUser().compareTo(o2.getUser());
                }
            });

            RecyclerView recyclerViewGroup = receiptListGroup.getView().findViewById(R.id.rv_group_receipt_list);
            RecyclerView recyclerViewUser = receiptListGroup.getView().findViewById(R.id.rv_group_receipt_list_user);
            LinearLayout linearLayoutGroup = receiptListGroup.getView().findViewById(R.id.ll_group_receipt_list);
            LinearLayout linearLayoutUser = receiptListGroup.getView().findViewById(R.id.ll_group_receipt_list_user);

            if (dataUser.isEmpty()) {
                linearLayoutUser.setVisibility(View.GONE);
            } else {
                ReceiptAdapter adapterUser = new ReceiptAdapter(receiptListGroup.getContext(), dataUser);
                recyclerViewUser.setAdapter(adapterUser);
                recyclerViewUser.setLayoutManager(new LinearLayoutManager(receiptListGroup.getContext()));
            }

            if (dataGroup.isEmpty()) {
                linearLayoutGroup.setVisibility(View.GONE);
            } else {
                ReceiptAdapter adapterGroup = new ReceiptAdapter(receiptListGroup.getContext(), dataGroup);
                recyclerViewGroup.setAdapter(adapterGroup);
                recyclerViewGroup.setLayoutManager(new LinearLayoutManager(receiptListGroup.getContext()));
            }

        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(receiptListGroup.getContext(), e.toString(), Toast.LENGTH_LONG).show();
        }
    }

}
