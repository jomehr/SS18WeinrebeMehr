package com.example.jan.kassenzettel_scan.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.example.jan.kassenzettel_scan.R;

public class CreateReceipt extends Fragment {

    private static final String TAG = "CreateReceipt";

    private FragmentTabHost tabHost;
    private float lastX;

    //Mandatory Constructor
    public CreateReceipt() {
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_create_receipt,container, false);

        tabHost = rootView.findViewById(android.R.id.tabhost);
        tabHost.setup(getActivity(), getChildFragmentManager(), android.R.id.tabcontent);

        tabHost.addTab(tabHost.newTabSpec("scan").setIndicator(getString(R.string.tab_scan)),
                TabScan.class, null);
        tabHost.addTab(tabHost.newTabSpec("create").setIndicator(getString(R.string.tab_manual)),
                TabCreate.class, null);

        /*rootView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    // when user first touches the screen to swap
                    case MotionEvent.ACTION_DOWN: {
                        lastX = event.getX();
                        return true;
                    }
                    case MotionEvent.ACTION_UP: {
                        float currentX = event.getX();

                        // if left to right swipe on screen
                        if (lastX < currentX) {
                            switchTabs(false);
                        }

                        // if right to left swipe on screen
                        if (lastX > currentX) {
                            switchTabs(true);
                        }

                        break;
                    }
                }
                return false;
            }
        });*/

        return rootView;
    }

    public void switchTabs(boolean direction) {

        if (direction) // true = move left
        {
            Log.d(TAG, "Switching Tabs left");
            if (tabHost.getCurrentTab() == 0)
                tabHost.setCurrentTab(tabHost.getTabWidget().getTabCount() - 1);
            else
                tabHost.setCurrentTab(tabHost.getCurrentTab() - 1);
        } else
        // move right
        {
            Log.d(TAG, "Switching Tabs right");
            if (tabHost.getCurrentTab() != (tabHost.getTabWidget()
                    .getTabCount() - 1))
                tabHost.setCurrentTab(tabHost.getCurrentTab() + 1);
            else
                tabHost.setCurrentTab(0);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        tabHost = null;
    }

}

