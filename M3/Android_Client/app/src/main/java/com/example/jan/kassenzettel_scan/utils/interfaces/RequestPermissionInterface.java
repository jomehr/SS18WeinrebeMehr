package com.example.jan.kassenzettel_scan.utils.interfaces;

import android.app.Activity;
import android.content.Context;

public interface RequestPermissionInterface {
    void requestPermissions(Activity context, String[] permissions);

    boolean isPermissionsGranted(Context context, String[] permissions);

    void onPermissionDenied();
}
