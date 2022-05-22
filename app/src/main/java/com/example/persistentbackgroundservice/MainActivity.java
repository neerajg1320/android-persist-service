package com.example.persistentbackgroundservice;

import android.Manifest;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;

import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import static com.example.persistentbackgroundservice.EmailRoutines.send_email_using_gmail;
import static com.example.persistentbackgroundservice.SmsFilter.isFromSender;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    String TAG = "MainActivity";

    Intent mServiceIntent;
    private PersistService mService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mService = new PersistService();
        mServiceIntent = new Intent(this, mService.getClass());
        if (!isMyServiceRunning(mService.getClass())) {
            startService(mServiceIntent);
        }

        requestSmsPermission();
    }

    public void onClickSendEmail(View view) {
        String subject = "SMS Transaction";
        String text = "SMS Trasnsactions done";
        ArrayList<String> receiverEmails = new ArrayList<>();
        receiverEmails.add("neeraj76@yahoo.com");

        send_email_using_gmail(subject, text, receiverEmails);

        Log.i(TAG, String.format("Email sent: [%s]", subject));
    }


    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
        if (serviceClass.getName().equals(service.service.getClassName())) {
            Log.i ("Service status", "Running");
            return true;
        }
    }
        Log.i ("Service status", "Not running");
        return false;
    }

    private void requestSmsPermission() {
        String smspermission = Manifest.permission.RECEIVE_SMS;
        int grant = ContextCompat.checkSelfPermission(this, smspermission);

        //check if read SMS permission is granted or not
        if (grant != PackageManager.PERMISSION_GRANTED) {
            String[] permission_list = new String[1];
            permission_list[0] = smspermission;
            ActivityCompat.requestPermissions(this, permission_list, 1);
        }
    }

    @Override
    protected void onDestroy() {
        //stopService(mServiceIntent);
        Intent broadcastIntent = new Intent();
        broadcastIntent.setAction("restartservice");
        broadcastIntent.setClass(this, RestarterReceiver.class);
        this.sendBroadcast(broadcastIntent);
        super.onDestroy();
    }
}