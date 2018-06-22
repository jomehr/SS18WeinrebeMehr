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
import android.widget.TextView;

import com.example.jan.kassenzettel_scan.R;
import com.example.jan.kassenzettel_scan.adapter.ReceiptAdapter;
import com.example.jan.kassenzettel_scan.data.ReceiptData;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ReceiptList_Solo extends Fragment {

    Date date = new Date();
    DateFormat df = new SimpleDateFormat("dd.MM.yyyy", Locale.GERMANY);

    String user1 = "Jan";

    ReceiptData receiptData1 = new ReceiptData(
            user1,"Kaufland","10.06.2018", 5, 19.99, "€");
    ReceiptData receiptData2 = new ReceiptData(
            user1,"Rewe","21.06.2018", 7, 24.99, "€");
    ReceiptData receiptData3 = new ReceiptData(
            user1,"Rewe","18.06.2018", 4, 8.50, "€");
    ReceiptData receiptData4 = new ReceiptData(
            user1,"Nahkauf","30.06.2018", 2, 2.30, "€");
    ReceiptData receiptData5 = new ReceiptData(
            user1,"Aldi","18.05.2018", 20, 101, "€");
    ReceiptData receiptData6 = new ReceiptData(
            user1,"Aldi","02.07.2018", 11, 30.4, "€");
    ReceiptData receiptData7 = new ReceiptData(
            user1,"Kaufland","18.06.2018", 1, 2, "€");


    @Nullable
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

            View rootView = inflater.inflate(R.layout.fragment_solo_receipt_list,container,false);

            List<ReceiptData> data = new ArrayList<>();
            data.add(receiptData1);
            data.add(receiptData2);
            data.add(receiptData3);
            data.add(receiptData4);
            data.add(receiptData5);
            data.add(receiptData6);
            data.add(receiptData7);

            Collections.sort(data, new Comparator<ReceiptData>() {
                @Override
                public int compare(ReceiptData o1, ReceiptData o2) {
                    return o1.getDate().compareTo(o2.getDate());
                }
            });

            RecyclerView recyclerView = rootView.findViewById(R.id.rv_solo_receipt_list);
            ReceiptAdapter adapter = new ReceiptAdapter(this.getContext(), data);
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));

            if (adapter.getItemCount() != 0) {
                TextView text =  rootView.findViewById(R.id.solo_receipt_list_text);
                text.setVisibility(View.GONE);
            }

            return rootView;
    }
}
