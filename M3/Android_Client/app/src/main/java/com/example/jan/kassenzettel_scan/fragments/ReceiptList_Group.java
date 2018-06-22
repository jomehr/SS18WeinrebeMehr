package com.example.jan.kassenzettel_scan.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;


import com.example.jan.kassenzettel_scan.R;
import com.example.jan.kassenzettel_scan.adapter.ReceiptAdapter;
import com.example.jan.kassenzettel_scan.data.ReceiptData;
import com.example.jan.kassenzettel_scan.tasks.AsyncGetGroupReceipts;

import java.util.List;


public class ReceiptList_Group extends Fragment {

    LinearLayout resultView;
    ProgressBar progress;
    RecyclerView recyclerViewGroup;
    RecyclerView recyclerViewUser;
    LinearLayoutManager linearLayoutManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_group_receipt_list,container,false);

        resultView = (LinearLayout) rootView.findViewById(R.id.receipt_group_recyclerlayout);
        progress = (ProgressBar) rootView.findViewById(R.id.receipt_group_pb);
        recyclerViewGroup = rootView.findViewById(R.id.rv_group_receipt_list);
        recyclerViewUser = rootView.findViewById(R.id.rv_group_receipt_list_user);

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        new AsyncGetGroupReceipts(this, "5b2d2000cefeea75c0def8e1").execute();
    }

    public void showProgressBar() {
        resultView.setVisibility(View.GONE);
        progress.setVisibility(View.VISIBLE);
        progress.setIndeterminate(true);
    }

    public void dismissProgressBar() {
        resultView.setVisibility(View.VISIBLE);
        progress.setVisibility(View.GONE);
    }

}
