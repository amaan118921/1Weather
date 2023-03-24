package com.example.a1weather.helper;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.util.Log;

import androidx.core.app.ActivityCompat;


public class PermissionUtils {
    IPermission iPermission;
    public PermissionUtils(IPermission iPermission) {
        this.iPermission = iPermission;
    }
    public boolean isPermissionGranted(String[] permissionList, Context context) {
        for(String permission: permissionList) {
            if(ActivityCompat.checkSelfPermission(context, permission)!= PackageManager.PERMISSION_GRANTED) return false;
        }
        return true;
    }

    public void processPermissions(int requestCode, String[] permissions, int[] grantResults) {
        if(requestCode==Constants.LOCATION_CODE) {
            Log.d("debug", "debug");
            if((grantResults.length ==1))  {
                if(grantResults[0]==PackageManager.PERMISSION_GRANTED) iPermission.onPermissionGranted();
                else iPermission.onPermissionDeniedPermanently();
            }
            else if(grantResults.length>1) {
                if(grantResults[0]==PackageManager.PERMISSION_GRANTED && grantResults[1]==PackageManager.PERMISSION_GRANTED) iPermission.onPermissionGranted();
                else if(grantResults[1]==PackageManager.PERMISSION_GRANTED && grantResults[0]!=PackageManager.PERMISSION_DENIED) iPermission.onPermissionDenied();
                else iPermission.onPermissionDeniedPermanently();
            }
        }
    }

    public interface IPermission {
        void onPermissionGranted();
        void onPermissionDenied();
        void onPermissionDeniedPermanently();
    }

}
