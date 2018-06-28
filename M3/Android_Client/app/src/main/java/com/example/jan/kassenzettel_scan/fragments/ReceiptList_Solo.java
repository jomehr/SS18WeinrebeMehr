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


    @Nullable
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

            View rootView = inflater.inflate(R.layout.fragment_solo_receipt_list,container,false);


            return rootView;
    }
}
