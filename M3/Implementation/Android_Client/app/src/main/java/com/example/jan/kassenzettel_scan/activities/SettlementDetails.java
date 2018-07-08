package com.example.jan.kassenzettel_scan.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jan.kassenzettel_scan.R;
import com.example.jan.kassenzettel_scan.data.SettlementData;
import com.loopj.android.http.RequestParams;

import java.util.ArrayList;

public class SettlementDetails extends AppCompatActivity {

    private static final String TAG = "SettlementDetails";

    private Context mContext;
    private SettlementData settlementData;
    private ProgressBar progress;
    private SwipeRefreshLayout swipeRefresh_content;
    private RecyclerView recyclerViewSettlement;
    private TextView dateStart, dateEnd, failureText;
    private RequestParams params;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settlement_detail);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setTitle(getString(R.string.details));

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        mContext = this;

        dateStart = findViewById(R.id.detail_settlement_dateStart);
        dateEnd = findViewById(R.id.detail_settlement_dateEnd);
        progress = findViewById(R.id.pb_ReceiptDetail);
        swipeRefresh_content = findViewById(R.id.swipeRefresh_ReceiptDetail);
        recyclerViewSettlement = findViewById(R.id.rv_ReceiptDetail_articleList);
        failureText = findViewById(R.id.noDataText_ReceiptDetails);

        if (getIntent().getExtras() != null) {
            Log.d(TAG, getIntent().getExtras().toString());

            if (getIntent().getExtras().containsKey("dataId")) {
                Log.d(TAG, "Coming from notification");
                getSettlementData(getIntent().getExtras().getString("dataId"));

            } else {
                Log.d(TAG, "Coming from list-activity");
                Bundle bundle = getIntent().getExtras();
                settlementData = (SettlementData) bundle.getSerializable("receiptData");

                if (settlementData != null) {
                    updateSettlementContent(settlementData);
                } else {
                    failureText.setVisibility(View.VISIBLE);
                }
            }
        }
    }

    private void getSettlementData(String id) {

    }

    private void updateSettlementContent(SettlementData settlementData) {

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
            Toast.makeText(SettlementDetails.this, R.string.no_new_alerts, Toast.LENGTH_LONG).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
