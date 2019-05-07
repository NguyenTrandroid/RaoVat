package com.example.raovat.Utils;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;

public class CheckPermission {
    Context context;

    public CheckPermission(Context context) {
        this.context = context;
    }

    public boolean checkWriteExternalPermission() {
        String permission = Manifest.permission.WRITE_EXTERNAL_STORAGE;
        int res = context.checkCallingOrSelfPermission(permission);
        return (res == PackageManager.PERMISSION_GRANTED);
    }

    public boolean checkReadExternalPermission() {
        String permission = Manifest.permission.READ_EXTERNAL_STORAGE;
        int res = context.checkCallingOrSelfPermission(permission);
        return (res == PackageManager.PERMISSION_GRANTED);
    }

    public boolean checkCallPhonePermission() {
        String permission = Manifest.permission.CALL_PHONE;
        int res = context.checkCallingOrSelfPermission(permission);
        return (res == PackageManager.PERMISSION_GRANTED);
    }

    public boolean checkCameraPermission() {
        String permission = Manifest.permission.CAMERA;
        int res = context.checkCallingOrSelfPermission(permission);
        return (res == PackageManager.PERMISSION_GRANTED);
    }

}
