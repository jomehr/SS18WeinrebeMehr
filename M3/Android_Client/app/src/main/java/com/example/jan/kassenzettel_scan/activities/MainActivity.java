package com.example.jan.kassenzettel_scan.activities;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.jan.kassenzettel_scan.R;
import com.example.jan.kassenzettel_scan.fragments.MemberList;
import com.example.jan.kassenzettel_scan.fragments.ReceiptList_Group;
import com.example.jan.kassenzettel_scan.fragments.ReceiptList_Solo;
import com.example.jan.kassenzettel_scan.fragments.Scan;
import com.example.jan.kassenzettel_scan.fragments.SettlementList;
import com.example.jan.kassenzettel_scan.fragments.ShoppingList;
import com.example.jan.kassenzettel_scan.fragments.Statistic;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = "MainActivity";

    private BottomNavigationView bottomNavigationView;

    private Fragment fragment = null;
    private Class fragmentClass = null;
    private FragmentManager fragmentManager = getSupportFragmentManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_drawer);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                int id = item.getItemId();

                switch (id) {
                    case (R.id.nav_group_receipt_list):
                        fragmentClass = ReceiptList_Group.class;
                        bottomNavigationView.setVisibility(View.VISIBLE);
                        break;
                    case (R.id.nav_group_settlement_list):
                        fragmentClass = SettlementList.class;
                        bottomNavigationView.setVisibility(View.VISIBLE);
                        break;
                    case (R.id.nav_group_shopping_list):
                        fragmentClass = ShoppingList.class;
                        bottomNavigationView.setVisibility(View.VISIBLE);
                        break;
                    default :
                        break;
                }

                try {
                    fragment = (Fragment) fragmentClass.newInstance();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                // Insert the fragment by replacing any existing fragment
                fragmentManager.beginTransaction().replace(R.id.flContent, fragment).commit();

                // Highlight the selected item has been done by NavigationView
                item.setChecked(true);
                // Set action bar title
                getSupportActionBar().setTitle(item.getTitle());
                return true;
            }
        });

        //Homescreen, load ReceiptList_Group if in group, else ReceiptList_Solo
        fragmentClass = ReceiptList_Group.class;

        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

        fragmentManager.beginTransaction().replace(R.id.flContent, fragment).commit();
        bottomNavigationView.setSelectedItemId(R.id.nav_group_receipt_list);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Create channel to show notifications.
            Log.d(TAG, "Setting up notification channel");
            String channelId  = "receiplist_group";
            String channelName = "receiplist_group";
            NotificationManager notificationManager =
                    getSystemService(NotificationManager.class);
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(new NotificationChannel(channelId,
                        channelName, NotificationManager.IMPORTANCE_LOW));
            }
        }

        if (getIntent().getExtras() != null) {
            for (String key : getIntent().getExtras().keySet()) {
                Object value = getIntent().getExtras().get(key);
                Log.d(TAG, "Key: " + key + " Value: " + value);
            }
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
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
            Toast.makeText(MainActivity.this, R.string.no_new_alerts, Toast.LENGTH_LONG).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation bar item clicks here.
        int id = item.getItemId();

        switch (id) {
            case (R.id.nav_scan) :
                fragmentClass = Scan.class;
                bottomNavigationView.setVisibility(View.GONE);
                break;
            case (R.id.nav_receipt_list) :
                fragmentClass =ReceiptList_Solo.class;
                bottomNavigationView.setVisibility(View.GONE);
                break;
            case (R.id.nav_statistic) :
                fragmentClass = Statistic.class;
                bottomNavigationView.setVisibility(View.GONE);
                break;
            //Group fragmentes here
            case (R.id.nav_group_receipt_list) :
                fragmentClass = ReceiptList_Group.class;
                bottomNavigationView.setSelectedItemId(R.id.nav_group_receipt_list);
                bottomNavigationView.setVisibility(View.VISIBLE);
                break;
            case (R.id.nav_group_settlement_list) :
                fragmentClass = SettlementList.class;
                bottomNavigationView.setSelectedItemId(R.id.nav_group_settlement_list);
                bottomNavigationView.setVisibility(View.VISIBLE);
                break;
            case (R.id.nav_group_shopping_list) :
                fragmentClass = ShoppingList.class;
                bottomNavigationView.setSelectedItemId(R.id.nav_group_shopping_list);
                bottomNavigationView.setVisibility(View.VISIBLE);
                break;
            case (R.id.nav_group_member_list) :
                fragmentClass = MemberList.class;
                bottomNavigationView.setVisibility(View.GONE);
                break;
            //Miscellaneous fragments here
            case (R.id.nav_settings) :
                Toast.makeText(MainActivity.this, R.string.later_implementation, Toast.LENGTH_LONG).show();
                break;
            case (R.id.nav_logout) :
                Toast.makeText(MainActivity.this, R.string.later_implementation, Toast.LENGTH_LONG).show();
                break;
            default:
                break;
        }

        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Insert the fragment by replacing any existing fragment
        fragmentManager.beginTransaction().replace(R.id.flContent, fragment).commit();
        
        // Highlight the selected item has been done by NavigationView
        item.setChecked(true);
        // Set action bar title
        getSupportActionBar().setTitle(item.getTitle());

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}