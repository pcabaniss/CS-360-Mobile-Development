package com.zybooks.mainproject;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.CompoundButton;
import android.Manifest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.material.switchmaterial.SwitchMaterial;

// This is a settings screen. The minimum requirements are to enable sms notifications, leaving this here
// will allow for adding more permissions in the future.
public class SettingsActivity extends AppCompatActivity {

    // Key value name for manifest
    public static String PREF = "notification_pref";

    boolean enableNotifications = false;
    SwitchMaterial switchNotification;
    SharedPreferences prefs;
    // Code for SMS request
    private final int REQUEST_SMS = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        switchNotification = findViewById(R.id.notification_toggle);

        // Set the listener for the switch
        switchNotification.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                System.out.println("Toggled the switch!");
                enableNotifications = isChecked;

                // Permission granted
                if (isChecked && permissionEnabled()) {
                    switchNotification.setChecked(true);
                }
                else {
                    // Permission denied
                    switchNotification.setChecked(false);
                    enableNotifications = false;
                }

                savePermissions();
            }
        });

        // Default values
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        enableNotifications = prefs.getBoolean(PREF, false);

        // Sets toggle switch to initial value saved on device
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED
        && enableNotifications) {
            switchNotification.setChecked(true);
        }

    }

    /**
     * Checks if permissions are enabled or have been asked
     * @return
     */
    private boolean permissionEnabled() {
        String sms = Manifest.permission.SEND_SMS;



        System.out.println("Checking permissions.........");
        // Check if they are granted first
        if (ContextCompat.checkSelfPermission(this, sms) != PackageManager.PERMISSION_GRANTED) {

            System.out.println();

            // Check if the user has opted out permanently
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, sms)) {

                new AlertDialog.Builder(this).setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Enable Notifications")
                        .setMessage("Would you like to opt into receiving notifications through text?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // Request permission
                                ActivityCompat.requestPermissions(SettingsActivity.this, new String[]{sms}, REQUEST_SMS);
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .create().show();
            }
            else {
                // Request permissions
                ActivityCompat.requestPermissions(this, new String[]{sms}, REQUEST_SMS);
            }
            return false;
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_SMS: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    enableNotifications = true;
                    switchNotification.setChecked(true);
                } else {
                    enableNotifications = false;
                    switchNotification.setChecked(false);
                }
                savePermissions();
            }
        }
    }

    /**
     * Save values to device
     */
    private void savePermissions() {
        SharedPreferences.Editor preferencesEditor = prefs.edit();
        preferencesEditor.putBoolean(PREF, enableNotifications);
        preferencesEditor.commit();
    }

}
