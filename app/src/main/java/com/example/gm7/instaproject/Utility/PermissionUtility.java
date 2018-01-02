package com.example.gm7.instaproject.Utility;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

import com.example.gm7.instaproject.R;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by emad on 10/26/17.
 */

public class PermissionUtility {

    public static final int EXTERNAL_STORAGE_PERMISSION_CONSTANT = 100;
    public static final int MULTIPLE_PERMISSION_CONSTANT = 104;
    public static final int LOCATION_PERMISSION_CONSTANT = 105;
    public static final int CAMERA_PERMISSION_CONSTANT = 101;
    public static final int CONTACTS_PERMISSION_CONSTANT = 102;
    public static final int REQUEST_PERMISSION_SETTING = 103;
    public String[] permissionsRequired = new String[]{Manifest.permission.CAMERA,
            Manifest.permission.READ_EXTERNAL_STORAGE};

    public String[] locationPermissionsRequired = new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION};
    private boolean sentToSettings = false;
    private SharedPreferences permissionStatus;
    private Context mContext;

    public PermissionUtility(Context mContext) {
        this.mContext = mContext;
        permissionStatus = mContext.getSharedPreferences("permissionStatus",MODE_PRIVATE);
    }


    public void permissionInfo() {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle(mContext.getString(R.string.storage_permission_title))
                .setMessage(mContext.getString(R.string.storage_permission_mesg))
                .setPositiveButton(mContext.getString(R.string.grant_text), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                        ActivityCompat.requestPermissions((Activity) mContext,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},EXTERNAL_STORAGE_PERMISSION_CONSTANT);
                    }
                })
                .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
        builder.show();
    }

    public void askForPermissionInfoAgain() {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle(mContext.getString(R.string.storage_permission_title))
                .setMessage(mContext.getString(R.string.storage_permission_mesg))
                .setPositiveButton(mContext.getString(R.string.grant_text), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                        sentToSettings = true;
                        Intent settingIntent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        Uri uri = Uri.fromParts("package",mContext.getPackageName(),null);
                        settingIntent.setData(uri);
                        ((Activity)mContext).startActivityForResult(settingIntent,REQUEST_PERMISSION_SETTING);
                        Toast.makeText(mContext,"Go to permissions to Grant Storage",Toast.LENGTH_LONG).show();
                    }
                })
                .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
        builder.show();
    }


    //Check for the Storage Permissions
    public boolean checkExtraStoragePermission(){
        if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.READ_EXTERNAL_STORAGE) !=
                PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) mContext,Manifest.permission.READ_EXTERNAL_STORAGE)) {
                //show Information about why you need the permission
                permissionInfo();
            } else if (permissionStatus.getBoolean(Manifest.permission.READ_EXTERNAL_STORAGE,false)) {
                //Previously Permission Request was cancelled with 'Don't Ask Again',
                // Redirect to Settings after showing Information about why you need the permission
              askForPermissionInfoAgain();
            }else {
                //just request the permission
                ActivityCompat.requestPermissions((Activity) mContext,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},EXTERNAL_STORAGE_PERMISSION_CONSTANT);
            }

            SharedPreferences.Editor editor = permissionStatus.edit();
            editor.putBoolean(Manifest.permission.WRITE_EXTERNAL_STORAGE,true);
            editor.apply();

            return false;

        } else {
            //you already have the permission, just go a head
            return true;
        }
    }

    //Check for the Camera Permissions
    public boolean checkCameraPermission(){
        if (ActivityCompat.checkSelfPermission(mContext,Manifest.permission.CAMERA) !=
                PackageManager.PERMISSION_GRANTED) {
            if(ActivityCompat.shouldShowRequestPermissionRationale((Activity) mContext,Manifest.permission.CAMERA)){
                //Show Information about Why You need This Permission
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setTitle("Need Storage Permission")
                        .setMessage("This app need Storage Permission.")
                        .setPositiveButton("Grant", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                                ActivityCompat.requestPermissions((Activity) mContext,new String[]{Manifest.permission.CAMERA},CAMERA_PERMISSION_CONSTANT);
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        });
                builder.show();
            } else if (permissionStatus.getBoolean(Manifest.permission.CAMERA,false)){
                //Previously Permission Request was cancelled with " Don't Ask Again" ,
                //Redirect to Settings after showing Information about why you need the permission
                askForPermissionInfoAgain();
            } else {
                //just request the permission
                ActivityCompat.requestPermissions((Activity) mContext,new String[]{Manifest.permission.CAMERA},CAMERA_PERMISSION_CONSTANT);
            }
            SharedPreferences.Editor editor = permissionStatus.edit();
            editor.putBoolean(Manifest.permission.CAMERA,true);
            editor.apply();
            return false;
        } else {
            //you already have the permission, just go ahead
            return true;
        }

    }

    //Check for The Contacts Permissions
    public boolean checkContactsPermission(){
        if (ActivityCompat.checkSelfPermission(mContext,Manifest.permission.READ_CONTACTS) !=
                PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) mContext,Manifest.permission.READ_CONTACTS)) {
                //show Information about why need the Permission
            } else if (permissionStatus.getBoolean(Manifest.permission.READ_CONTACTS,false)) {
                //Previously Permission Request was cancelled with "Don't Ask Again",
                //Redirect to Settings after showing Information about why you need the permission

            } else {
                ActivityCompat.requestPermissions((Activity) mContext,new String[]{Manifest.permission.READ_CONTACTS},CONTACTS_PERMISSION_CONSTANT);
            }
            SharedPreferences.Editor editor = permissionStatus.edit();
            editor.putBoolean(Manifest.permission.READ_CONTACTS,true);
            editor.apply();
            return false;
        } else {
            //you already have the permission, just go ahead
            return true;
        }
    }

    //Multiple permission request for Fine Location ,Coarse location
    /*public boolean checkMultiplePermissionLocation() {
        if (ActivityCompat.checkSelfPermission(mContext, locationPermissionsRequired[0]) != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(mContext, locationPermissionsRequired[1]) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) mContext, locationPermissionsRequired[0])
                    || ActivityCompat.shouldShowRequestPermissionRationale((Activity) mContext, locationPermissionsRequired[1])) {
                //show information about why you need the permissions
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setTitle(mContext.getString(R.string.required_permission_title));
                builder.setMessage("This app needs Camera and Storage permissions.");
                builder.setPositiveButton(mContext.getString(R.string.grant_btn_text), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        ActivityCompat.requestPermissions((Activity) mContext,locationPermissionsRequired,LOCATION_PERMISSION_CONSTANT);
                    }
                });
                builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.show();

            } else if (permissionStatus.getBoolean(locationPermissionsRequired[0], false)) {
                //Previously Permission Request was cancelled with "Don't Ask Again",
                //Redirect to Settings after showing Information about why you need the permission
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setTitle(mContext.getString(R.string.required_permission_title));
                builder.setMessage("This app needs Camera and Location permissions.");
                builder.setPositiveButton(mContext.getString(R.string.grant_btn_text), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        sentToSettings = true;
                        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        Uri uri = Uri.fromParts("package", mContext.getPackageName(), null);
                        intent.setData(uri);
                        ((Activity) mContext).startActivityForResult(intent, REQUEST_PERMISSION_SETTING);
                        Toast.makeText(mContext, "Go to Permissions to Grant  Camera and Location", Toast.LENGTH_LONG).show();
                    }
                });
                builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.show();

            } else {
                //just request the permission
                ActivityCompat.requestPermissions((Activity) mContext, locationPermissionsRequired, MULTIPLE_PERMISSION_CONSTANT);
            }
            SharedPreferences.Editor editor = permissionStatus.edit();
            editor.putBoolean(locationPermissionsRequired[0], true);
            editor.apply();
            return false;

        } else {
            //you already have the permission, just go ahead
            return true;
        }
    }*/

    //multiple permission request for storage and camera
    //in case user choose to take photo from camera and
    // donna give permission to save the pic to storage

    public boolean checkMultiplePermissionSC() {
        if (ActivityCompat.checkSelfPermission(mContext, permissionsRequired[0]) != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(mContext, permissionsRequired[1]) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) mContext, permissionsRequired[0])
                    || ActivityCompat.shouldShowRequestPermissionRationale((Activity) mContext, permissionsRequired[1])) {
                //show information about why you need the permissions
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setTitle("Need Multiple Permissions");
                builder.setMessage("This app needs Camera and Storage permissions.");
                builder.setPositiveButton("Grant", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        ActivityCompat.requestPermissions((Activity) mContext,permissionsRequired,MULTIPLE_PERMISSION_CONSTANT);
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.show();

            } else if (permissionStatus.getBoolean(permissionsRequired[0], false)) {
                //Previously Permission Request was cancelled with "Don't Ask Again",
                //Redirect to Settings after showing Information about why you need the permission
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setTitle("Need Multiple Permissions");
                builder.setMessage("This app needs Camera and Location permissions.");
                builder.setPositiveButton("Grant", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        sentToSettings = true;
                        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        Uri uri = Uri.fromParts("package", mContext.getPackageName(), null);
                        intent.setData(uri);
                        ((Activity) mContext).startActivityForResult(intent, REQUEST_PERMISSION_SETTING);
                        Toast.makeText(mContext, "Go to Permissions to Grant  Camera and Location", Toast.LENGTH_LONG).show();
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.show();

            } else {
                //just request the permission
                ActivityCompat.requestPermissions((Activity) mContext, permissionsRequired, MULTIPLE_PERMISSION_CONSTANT);
            }
            SharedPreferences.Editor editor = permissionStatus.edit();
            editor.putBoolean(permissionsRequired[0], true);
            editor.apply();
            return false;

        } else {
            //you already have the permission, just go ahead
            return true;
        }
    }


    public boolean askForPermission(String permission,Integer requestCode) {
        if (ContextCompat.checkSelfPermission(mContext,permission)!= PackageManager.PERMISSION_GRANTED) {
            // show dialog to inform user that we need that permission
            if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) mContext,permission)){

                ActivityCompat.requestPermissions((Activity) mContext, new String[]{permission}, requestCode);
            } else {
                ActivityCompat.requestPermissions((Activity) mContext, new String[]{permission}, requestCode);
            }
        }

        return true;

    }
}
