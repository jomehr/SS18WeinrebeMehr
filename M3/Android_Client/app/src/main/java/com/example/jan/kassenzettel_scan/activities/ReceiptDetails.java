package com.example.jan.kassenzettel_scan.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jan.kassenzettel_scan.R;
import com.example.jan.kassenzettel_scan.adapter.ArticleAdapter;
import com.example.jan.kassenzettel_scan.data.ArticleData;
import com.example.jan.kassenzettel_scan.data.ReceiptData;
import com.example.jan.kassenzettel_scan.network.RestClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Objects;

import cz.msebera.android.httpclient.Header;

public class ReceiptDetails extends AppCompatActivity {

    private static final String TAG = "ReceiptDetails";

    private static final String receiptEndpoint = "receipt/";
    private static final String articleEndpoint = "/articles";

    private Context mContext;
    private RelativeLayout top, bottom;
    private ReceiptData receiptData;
    private ProgressBar progress;
    private SwipeRefreshLayout swipeRefresh_content;
    private RecyclerView recyclerViewArticle;
    private TextView failureText, storename, date, location, receipttotal, paid, change;
    private RequestParams params;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receipt_details);

        Log.d(TAG, "ActivityLifeCycle: ONCREATE");

        mContext = this;

        progress = findViewById(R.id.pb_ReceiptDetail);
        top = findViewById(R.id.rl_ReceiptDetail_top);
        storename = findViewById(R.id.detail_storename);
        date = findViewById(R.id.detail_date);
        location = findViewById(R.id.detail_location);
        bottom = findViewById(R.id.rl_ReceiptDetail_bottom);
        receipttotal = findViewById(R.id.detail_total);
        paid = findViewById(R.id.detail_paid);
        change = findViewById(R.id.detail_change);
        swipeRefresh_content = findViewById(R.id.swipeRefresh_ReceiptDetail);
        recyclerViewArticle = findViewById(R.id.rv_ReceiptDetail_articleList);
        failureText = findViewById(R.id.noDataText_ReceiptDetails);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setTitle(getString(R.string.details));

        if (getIntent().getExtras() != null) {
            Log.d(TAG, getIntent().getExtras().toString());

            if (getIntent().getExtras().containsKey("dataId")) {
                Log.d(TAG, "Coming from notification");
                getReceiptData(getIntent().getExtras().get("dataId").toString());
                
            } else {
                Log.d(TAG, "Coming from list-activity");
                Bundle bundle = getIntent().getExtras();
                receiptData = (ReceiptData) bundle.getSerializable("receiptData");

                if (receiptData != null) {
                    updateReceiptContent(receiptData);
                } else {
                    top.setVisibility(View.GONE);
                    bottom.setVisibility(View.GONE);
                }
            }
        }

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        swipeRefresh_content.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getArticleData();
                swipeRefresh_content.setRefreshing(false);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "ActivityLifeCycle: ONRESUME");

        if (receiptData != null) {
            getArticleData();
        }
    }

    private void showProgressBar() {
        progress.setVisibility(View.VISIBLE);
    }

    private void dismissProgressBar() {
        progress.setVisibility(View.GONE);
    }

    private void getArticleData() {
        //TODO update receiptData in case it changed too
        showProgressBar();
        String receiptId = receiptData.getReceiptId();
        Log.d(TAG, receiptId);
        String endpointURL = receiptEndpoint + receiptId + articleEndpoint;
        Log.d(TAG, endpointURL);
        RestClient.get(endpointURL, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                Log.d(TAG, "Success! Data loaded");
                loadArticleData(response);
                dismissProgressBar();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Log.d(TAG, "Failure1! Data not loaded. Errorcode: " + statusCode);
                failureText.setVisibility(View.VISIBLE);

                if (statusCode == 404) {
                    failureText.setText(R.string.noRessource_statusCodeText);
                } else {
                    failureText.setText(R.string.serverSide_statusCodeText);
                }

                swipeRefresh_content.setVisibility(View.INVISIBLE);
                dismissProgressBar();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.d(TAG, "Failure2! Data not loaded. Errorcode: " + statusCode);
                failureText.setVisibility(View.VISIBLE);

                if (statusCode == 404) {
                    failureText.setText(R.string.noRessource_statusCodeText);
                } else {
                    failureText.setText(R.string.serverSide_statusCodeText);
                }

                swipeRefresh_content.setVisibility(View.INVISIBLE);
                dismissProgressBar();
            }
        });
    }

    private void getReceiptData(String id) {
        showProgressBar();
        String endpoint  = receiptEndpoint + id;
        RestClient.get(endpoint, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Log.d(TAG, "Success! Data loaded");
                loadReceiptData(response);
                dismissProgressBar();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Log.d(TAG, "Failure1! Data not loaded. Errorcode: "+statusCode);
                failureText.setVisibility(View.VISIBLE);

                if (statusCode == 404) {
                    failureText.setText(R.string.noRessource_statusCodeText);
                } else {
                    failureText.setText(R.string.serverSide_statusCodeText);
                }

                top.setVisibility(View.GONE);
                bottom.setVisibility(View.GONE);
                dismissProgressBar();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.d(TAG, "Failure2! Data not loaded. Errorcode: "+statusCode);
                failureText.setVisibility(View.VISIBLE);

                if (statusCode == 404) {
                    failureText.setText(R.string.noRessource_statusCodeText);
                } else {
                    failureText.setText(R.string.serverSide_statusCodeText);
                }

                top.setVisibility(View.GONE);
                bottom.setVisibility(View.GONE);
                dismissProgressBar();
            }
        });
    }

    private void loadArticleData(JSONArray response) {
        Log.d(TAG, "Data is being loaded");

        ArrayList<ArticleData> dataArticle = new ArrayList<>();
        ArrayList<String> participationIds = new ArrayList<>();

        try {
            for (int i = 0; i < response.length(); i++) {
                JSONObject json_data = response.getJSONObject(i);
                JSONArray participations = json_data.getJSONArray("participation");
                for (int j=0; j<participations.length(); j++) {
                    participationIds.add(participations.get(j).toString());
                }

                ArticleData articleData = new ArticleData(
                        json_data.getString("_id"),
                        json_data.getString("name"),
                        receiptData.getCurrency(),
                        json_data.getDouble("priceTotal"),
                        json_data.getDouble("priceSingle"),
                        json_data.getInt("amount"),
                        participations.length()
                );
                dataArticle.add(articleData);
            }

            updateArticleContent(dataArticle);
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(mContext, "JSON konnte nicht formatiert werden", Toast.LENGTH_LONG).show();
        }
    }

    private void loadReceiptData(JSONObject response) {

        try {
            receiptData = new ReceiptData(
                    response.getString("_id"),
                    response.getJSONObject("owner").getString("_id"),
                    response.getString("store"),
                    response.getString("date"),
                    0,
                    response.getDouble("total"),
                    response.getDouble("paid"),
                    response.getDouble("change"),
                    response.getString("currency")
            );
            updateReceiptContent(receiptData);
            getArticleData();
        }catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(mContext, "JSON konnte nicht formatiert werden", Toast.LENGTH_LONG).show();
        }
    }

    private void updateArticleContent(ArrayList<ArticleData> dataArticle) {
        Log.d(TAG, "Updating UI with data");
        if (dataArticle.isEmpty()) {
            Log.d(TAG, "Article data is empty");
            recyclerViewArticle.setVisibility(View.GONE);
        } else {
            recyclerViewArticle.setVisibility(View.VISIBLE);
            ArticleAdapter articleAdapter = new ArticleAdapter(mContext, dataArticle);
            recyclerViewArticle.setAdapter(articleAdapter);
            recyclerViewArticle.setLayoutManager(new LinearLayoutManager(mContext));
        }
    }

    private  void updateReceiptContent(ReceiptData receiptData) {
        Log.d(TAG, receiptData.getReceiptId());
        storename.setText(receiptData.getStoreName());
        date.setText(receiptData.getDateString());
        location.setText("Feature folgt");
        receipttotal.setText(String.valueOf(receiptData.getTotalAmount()));
        paid.setText(String.valueOf(receiptData.getPaidAmount()));
        change.setText(String.valueOf(receiptData.getChangeAmount()));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here
        int id = item.getItemId();

        if (id == R.id.toolbar_push_messages) {
            Toast.makeText(ReceiptDetails.this, R.string.no_new_alerts, Toast.LENGTH_LONG).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
