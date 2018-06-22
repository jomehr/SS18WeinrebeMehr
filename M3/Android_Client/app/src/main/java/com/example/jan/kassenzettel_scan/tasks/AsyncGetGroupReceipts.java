package com.example.jan.kassenzettel_scan.tasks;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import java.util.List;

public class AsyncGetGroupReceipts extends AsyncTask<String, String, String> {

    // CONNECTION_TIMEOUT and READ_TIMEOUT are in milliseconds
    private static final int CONNECTION_TIMEOUT = 10000;
    private static final int READ_TIMEOUT = 15000;
    private ReceiptList_Group receiptListGroup;
    private String id;

    public AsyncGetGroupReceipts (ReceiptList_Group receiptListGroup, String id) {
        this.receiptListGroup = receiptListGroup;
        this.id = id;
    }

    @Override
    protected String doInBackground(String... params) {

        URL url;
        HttpURLConnection conn;
        try {
            url = new URL("http://127.0.0.1:8081/group/"+ id);
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

            // setDoOutput to true as we recieve data from json file
            conn.setDoOutput(true);
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
            return e1.toString();
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
                        json_data.getString("owner"),
                        json_data.getString("store"),
                        json_data.getString("date"),
                        json_data.getJSONArray("receipts").length(),
                        json_data.getDouble("total"),
                        json_data.getString("currency")
                );

                dataGroup.add(receiptData);
            }

            for (int i = 0; i <= dataGroup.size(); i++) {
                if (dataGroup.get(i).getUser().equals(currentUser)) {
                    dataUser.add(dataGroup.get(i));
                    dataGroup.remove(i);
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

            ReceiptAdapter adapterUser = new ReceiptAdapter(receiptListGroup.getContext(), dataUser);
            ReceiptAdapter adapterGroup = new ReceiptAdapter(receiptListGroup.getContext(), dataGroup);

            recyclerViewUser.setAdapter(adapterUser);
            recyclerViewGroup.setAdapter(adapterGroup);

            recyclerViewUser.setLayoutManager(new LinearLayoutManager(receiptListGroup.getContext()));
            recyclerViewGroup.setLayoutManager(new LinearLayoutManager(receiptListGroup.getContext()));

        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(receiptListGroup.getActivity(), e.toString(), Toast.LENGTH_LONG).show();
        }
    }

}
