package com.example.jan.kassenzettel_scan.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;


import com.example.jan.kassenzettel_scan.R;
import com.example.jan.kassenzettel_scan.adapter.ReceiptAdapter;
import com.example.jan.kassenzettel_scan.data.ReceiptData;
import com.example.jan.kassenzettel_scan.tasks.AsyncGetGroupReceipts;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


public class ReceiptList_Group extends Fragment {


    String user1 = "Jan";
    String user2 = "Armin";
    String user3 = "Sebastian";

    ReceiptData receiptData1 = new ReceiptData(
            user1,"Kaufland","10.06.2018", 5, 19.99, "€");
    ReceiptData receiptData2 = new ReceiptData(
            user2,"Rewe","21.06.2018", 7, 24.99, "€");
    ReceiptData receiptData3 = new ReceiptData(
            user3,"Rewe","18.06.2018", 4, 8.50, "€");
    ReceiptData receiptData4 = new ReceiptData(
            user1,"Nahkauf","30.06.2018", 2, 2.30, "€");
    ReceiptData receiptData5 = new ReceiptData(
            user2,"Aldi","18.05.2018", 20, 101, "€");
    ReceiptData receiptData6 = new ReceiptData(
            user3,"Aldi","02.07.2018", 11, 30.4, "€");
    ReceiptData receiptData7 = new ReceiptData(
            user1,"Kaufland","18.06.2018", 1, 2, "€");


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_group_receipt_list,container,false);

       /* String currentUser = "Jan";

        List<ReceiptData> dataGroup = new ArrayList<>();
        List<ReceiptData> dataUser = new ArrayList<>();
        dataGroup.add(receiptData1);
        dataGroup.add(receiptData2);
        dataGroup.add(receiptData3);
        dataGroup.add(receiptData4);
        dataGroup.add(receiptData5);
        dataGroup.add(receiptData6);
        dataGroup.add(receiptData7);

        for (int i = 0; i <= dataGroup.size(); i++) {
            if (dataGroup.get(i).getUser().equals(currentUser)) {
                dataUser.add(dataGroup.get(i));
                dataGroup.remove(i);
            }
        }

        //Sort in descending order
        //TODO FIX BUG: only sorts by day not month, implement more complex sorting (date and name)
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

        RecyclerView recyclerViewGroup = rootView.findViewById(R.id.rv_group_receipt_list);
        RecyclerView recyclerViewUser = rootView.findViewById(R.id.rv_group_receipt_list_user);

        ReceiptAdapter adapterUser = new ReceiptAdapter(this.getContext(), dataUser);
        ReceiptAdapter adapterGroup = new ReceiptAdapter(this.getContext(), dataGroup);
        recyclerViewUser.setAdapter(adapterUser);
        recyclerViewGroup.setAdapter(adapterGroup);
        recyclerViewUser.setLayoutManager(new LinearLayoutManager(this.getContext()));
        recyclerViewGroup.setLayoutManager(new LinearLayoutManager(this.getContext()));*/
       new AsyncGetGroupReceipts(this, "sfefsefse23").execute();

        return rootView;
    }

    public void showProgressBar() {
        LinearLayout resultView = (LinearLayout) getActivity().findViewById(R.id.receipt_group_recyclerlayout);
        resultView.setVisibility(View.GONE);
        ProgressBar progress = (ProgressBar)getActivity().findViewById(R.id.receipt_group_pb);
        progress.setVisibility(View.VISIBLE);
        progress.setIndeterminate(true);
    }

    public void dismissProgressBar() {
        LinearLayout resultView = (LinearLayout) getActivity().findViewById(R.id.receipt_group_recyclerlayout);
        resultView.setVisibility(View.VISIBLE);
        ProgressBar progress = (ProgressBar)getActivity().findViewById(R.id.receipt_group_pb);
        progress.setVisibility(View.GONE);
    }
}
