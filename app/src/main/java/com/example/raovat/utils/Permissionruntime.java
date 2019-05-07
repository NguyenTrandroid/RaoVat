package com.example.raovat.Utils;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;
import android.util.Log;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.util.List;

import androidx.appcompat.app.AlertDialog;

public class Permissionruntime {
    Activity activity;
    Activity activity1;

    public Permissionruntime(Activity activity) {
        this.activity = activity;
    }

    public void requestPermissionCallPhone(final Class aClass) {
        Dexter.withActivity(activity)
                .withPermissions(
                        Manifest.permission.CALL_PHONE)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        if (report.areAllPermissionsGranted()) {
                            Log.d("AAA", "1");
                            if (aClass != null) {
                                activity.startActivity(new Intent(activity, aClass));

                            } else {

                            }

                        }

                        if (report.isAnyPermissionPermanentlyDenied()) {
                            showDialogRequest();
                            Log.d("AAA", "2");
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                        Log.d("AAA", "3");

                    }
                }).onSameThread()
                .check();
    }

    public void requestPermissionCameraStrorge(final Class aClass) {
        Dexter.withActivity(activity)
                .withPermissions(
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.CAMERA)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        if (report.areAllPermissionsGranted()) {
                            Log.d("AAA", "1");
                            if (aClass != null) {
                                activity.startActivity(new Intent(activity, aClass));

                            } else {

                            }

                        }

                        if (report.isAnyPermissionPermanentlyDenied()) {
                            showDialogRequest();
                            Log.d("AAA", "2");
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                        Log.d("AAA", "3");

                    }
                }).onSameThread()
                .check();
    }


    private void showDialogRequest() {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle("Need Permissions");
        builder.setMessage("This app needs permission to use this feature. You can grant them in app settings.");
        builder.setPositiveButton("GOTO SETTINGS", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                openSettings();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();

    }

    private void openSettings() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", activity.getPackageName(), null);
        intent.setData(uri);
        activity.startActivityForResult(intent, 101);
    }


}
