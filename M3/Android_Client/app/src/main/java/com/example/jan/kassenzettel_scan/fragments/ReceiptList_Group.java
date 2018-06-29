package com.example.jan.kassenzettel_scan.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jan.kassenzettel_scan.R;
import com.example.jan.kassenzettel_scan.adapter.ReceiptAdapter;
import com.example.jan.kassenzettel_scan.data.ReceiptData;
import com.example.jan.kassenzettel_scan.network.RestClient;
import com.google.firebase.messaging.FirebaseMessaging;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import cz.msebera.android.httpclient.Header;


public class ReceiptList_Group extends Fragment {

    private static final String TAG = "ReceiptList_Group";

    private static final String endpoint = "receipt";
    private static final String currentUser = "Jan";

    private Context mContext;
    private TextView failureText;
    private LinearLayout resultView, linearLayoutGroup, linearLayoutUser;
    private RecyclerView recyclerViewUser, recyclerViewGroup;
    private ProgressBar progress;
    private SwipeRefreshLayout refreshLayout;
    private MenuItem pushIcon;
    private RequestParams params;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        mContext = getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_group_receipt_list, container, false);

        resultView = rootView.findViewById(R.id.ll_ReceiptListGroupContainer);
        failureText = rootView.findViewById(R.id.noDataText_ReceiptListGroup);
        progress = rootView.findViewById(R.id.pb_ReceiptListGroup);
        linearLayoutGroup = rootView.findViewById(R.id.ll_ReceiptListGroup);
        linearLayoutUser = rootView.findViewById(R.id.ll_ReceiptListGroup_User);
        recyclerViewGroup = rootView.findViewById(R.id.rv_ReceiptListGroup);
        recyclerViewUser = rootView.findViewById(R.id.rv_ReceiptListGroup_User);
        refreshLayout = rootView.findViewById(R.id.swipeRefresh_ReceiptListGroup);
        pushIcon = rootView.findViewById(R.id.toolbar_push_messages);

        resultView.setVisibility(View.INVISIBLE);

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getData();
                refreshLayout.setRefreshing(false);
            }
        });

        Log.d(TAG, "FragmentLifeCycle: ONCREATEVIEW");

        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "FragmentLifeCycle: ONSTART");
    }

    @Override
    public void onResume() {
        super.onResume();
        getData();
        Log.d(TAG, "FragmentLifeCycle: ONRESUME");
    }

    private void showProgressBar() {
        progress.setVisibility(View.VISIBLE);
    }

    private void dismissProgressBar() {
        progress.setVisibility(View.GONE);
    }

    private void getData() {
        showProgressBar();
        RestClient.get(endpoint, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                Log.d(TAG, "Success! Data loaded");
                loadData(response);
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

                resultView.setVisibility(View.GONE);
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

                resultView.setVisibility(View.GONE);
                dismissProgressBar();
            }
        });
    }

    private void loadData(JSONArray response) {
        Log.d(TAG, "Data is being loaded");
        if (resultView.getVisibility() == View.INVISIBLE) resultView.setVisibility(View.VISIBLE);

        ArrayList<ReceiptData> dataGroup = new ArrayList<>();
        ArrayList<ReceiptData> dataUser = new ArrayList<>();
        ArrayList<String> receiptIds = new ArrayList<>();

        try {
            for(int i=0; i<response.length(); i++){
                JSONObject json_data = response.getJSONObject(i);
                JSONArray articles = json_data.getJSONArray("articles");
                for (int j=0; j<articles.length(); j++) {
                    receiptIds.add(articles.get(j).toString());
                }

                ReceiptData receiptData = new ReceiptData(
                        json_data.getString("_id"),
                        json_data.getJSONObject("owner").getString("name"),
                        json_data.getString("store"),
                        json_data.getString("date"),
                        articles.length(),
                        receiptIds,
                        json_data.getDouble("total"),
                        json_data.getDouble("paid"),
                        json_data.getDouble("change"),
                        json_data.getString("currency")
                );
                Log.d(TAG, receiptData.getUserFirstName());
                dataGroup.add(receiptData);
            }

            Iterator<ReceiptData> i = dataGroup.iterator();
            while (i.hasNext()) {
                ReceiptData receiptData = i.next();
                if (receiptData.getUserFirstName().equals(currentUser)) {
                    dataUser.add(receiptData);
                    i.remove();
                }
            }

            Collections.sort(dataGroup, new Comparator<ReceiptData>() {
                @Override
                public int compare(ReceiptData o1, ReceiptData o2) {
                    return o1.getRealDate().compareTo(o2.getRealDate());
                }
            });
            Collections.sort(dataUser, new Comparator<ReceiptData>() {
                @Override
                public int compare(ReceiptData o1, ReceiptData o2) {
                    return o1.getRealDate().compareTo(o2.getRealDate());
                }
            });

            updateContent(dataGroup, dataUser);
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(mContext, "JSON konnte nicht formatiert werden", Toast.LENGTH_LONG).show();
        }
    }

    private void updateContent(ArrayList<ReceiptData> dataGroup, ArrayList<ReceiptData> dataUser) {
        Log.d(TAG, "View is being updated");
        if (dataUser.isEmpty()) {
            linearLayoutUser.setVisibility(View.GONE);
        } else {
            linearLayoutUser.setVisibility(View.VISIBLE);
            ReceiptAdapter adapterUser = new ReceiptAdapter(mContext, dataUser);
            recyclerViewUser.setAdapter(adapterUser);
            recyclerViewUser.setLayoutManager(new LinearLayoutManager(mContext));
        }

        if (dataGroup.isEmpty()) {
            linearLayoutGroup.setVisibility(View.GONE);
        } else {
            linearLayoutGroup.setVisibility(View.VISIBLE);
            ReceiptAdapter adapterGroup = new ReceiptAdapter(mContext, dataGroup);
            recyclerViewGroup.setAdapter(adapterGroup);
            recyclerViewGroup.setLayoutManager(new LinearLayoutManager(mContext));
        }
    }

    private void subscribeTopic() {
        FirebaseMessaging.getInstance().subscribeToTopic("group_receipts");
    }

    private void unsubscribeTopic() {
        FirebaseMessaging.getInstance().subscribeToTopic("group_receipts");
    }

    public void changePushIcon () {
        pushIcon.setIcon(R.drawable.ic_newnotification_black);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.toolbar_menu_group_receipts, menu);
        pushIcon = menu.findItem(R.id.toolbar_push_messages);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here
        switch (item.getItemId()) {
            case R.id.toolbar_push_messages:
                Toast.makeText(this.getActivity(), R.string.no_new_alerts, Toast.LENGTH_LONG).show();
                return true;
            case R.id.toolbar_subscribe:
                subscribeTopic();
                return true;
            case R.id.toolbar_refresh:
                getData();
                return true;
            default:
                break;
        }
        return false;
    }
}
