package com.example.jan.kassenzettel_scan.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.jan.kassenzettel_scan.R;
import com.google.firebase.messaging.FirebaseMessaging;

public class SettlementList extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_settlement_list,container,false);
    }

    private void subscribeTopic() {
        FirebaseMessaging.getInstance().subscribeToTopic("settlement");
    }

    //TODO implement shared preferences to save subscription status. if already subscribes change menu entry to this
    private void unsubscribeTopic() {
        FirebaseMessaging.getInstance().unsubscribeFromTopic("settlement");
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.toolbar_menu_group_receipts, menu);
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
                //getData();
                return true;
            default:
                break;
        }
        return false;
    }
}

