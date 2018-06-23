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
import android.widget.Toast;


import com.example.jan.kassenzettel_scan.R;
import com.example.jan.kassenzettel_scan.activities.MainActivity;
import com.example.jan.kassenzettel_scan.adapter.ReceiptAdapter;
import com.example.jan.kassenzettel_scan.data.ReceiptData;
import com.example.jan.kassenzettel_scan.network.AsyncGetGroupReceipts;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.List;


public class ReceiptList_Group extends Fragment {

    private Context mContext;
    private LinearLayout resultView, linearLayoutGroup, linearLayoutUser;
    private RecyclerView recyclerViewUser, recyclerViewGroup;
    private ProgressBar progress;
    private SwipeRefreshLayout refreshLayout;
    public MenuItem pushIcon;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_group_receipt_list, container, false);

        mContext = getActivity();

        resultView = (LinearLayout) rootView.findViewById(R.id.receipt_group_recyclerlayout);
        progress = (ProgressBar) rootView.findViewById(R.id.receipt_group_pb);
        linearLayoutGroup = (LinearLayout) rootView.findViewById(R.id.ll_group_receipt_list);
        linearLayoutUser = (LinearLayout) rootView.findViewById(R.id.ll_group_receipt_list_user);
        recyclerViewGroup = (RecyclerView) rootView.findViewById(R.id.rv_group_receipt_list);
        recyclerViewUser = (RecyclerView) rootView.findViewById(R.id.rv_group_receipt_list_user);
        refreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.rv_refresh);

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new AsyncGetGroupReceipts(ReceiptList_Group.this).execute();
                refreshLayout.setRefreshing(false);
            }
        });

        if (getActivity().getIntent().getExtras() != null) {
            changePushIcon();
            for (String key : getActivity().getIntent().getExtras().keySet()) {
                Object value = getActivity().getIntent().getExtras().get(key);
                Log.d("FCM_INTENT", "Key: " + key + " Value: " + value);
            }
        }

        Log.d("LIFECYCLE", "ONCREATE");

        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d("LIFECYCLE", "ONSTART");
    }

    @Override
    public void onResume() {
        super.onResume();
        new AsyncGetGroupReceipts(this).execute();
        Log.d("LIFECYCLE", "ONRESUME");
    }

    public void showProgressBar() {
        progress.setVisibility(View.VISIBLE);
    }

    public void dismissProgressBar() {
        resultView.setVisibility(View.VISIBLE);
        progress.setVisibility(View.GONE);
    }

    public void updateContent(List<ReceiptData> dataGroup, List<ReceiptData> dataUser) {

        if (dataUser.isEmpty()) {
            linearLayoutUser.setVisibility(View.GONE);
        } else {
            ReceiptAdapter adapterUser = new ReceiptAdapter(mContext, dataUser);
            recyclerViewUser.setAdapter(adapterUser);
            recyclerViewUser.setLayoutManager(new LinearLayoutManager(mContext));
        }

        if (dataGroup.isEmpty()) {
            linearLayoutGroup.setVisibility(View.GONE);
        } else {
            ReceiptAdapter adapterGroup = new ReceiptAdapter(mContext, dataGroup);
            recyclerViewGroup.setAdapter(adapterGroup);
            recyclerViewGroup.setLayoutManager(new LinearLayoutManager(mContext));
        }
    }

    public void subscribeTopic() {
        FirebaseMessaging.getInstance().subscribeToTopic("group_receipts");
    }

    public void changePushIcon () {
        pushIcon.setIcon(R.drawable.ic_newnotification_black);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
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
                new AsyncGetGroupReceipts(ReceiptList_Group.this).execute();
                return true;
            default:
                break;
        }
        return false;
    }
}
