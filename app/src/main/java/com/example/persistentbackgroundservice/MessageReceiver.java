package com.example.persistentbackgroundservice;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.provider.Telephony;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.EditText;

public class MessageReceiver extends BroadcastReceiver {
    String TAG = "MessageReceiver";

//    private  static EditText editText;
//
//    public void setEditText(EditText editText) {
//        MessageReceiver.editText=editText;
//    }

    @Override
    public void onReceive(Context context, Intent intent) {
        //message will be holding complete sms that is received
        SmsMessage[] messages = Telephony.Sms.Intents.getMessagesFromIntent(intent);
        for(SmsMessage sms : messages)
        {
            String msg = sms.getMessageBody();
            Log.i(TAG, msg);
            // here we are spliting the sms using " : " symbol
            String[] parts = msg.split(": ");

            String otp = parts[0];
//            editText.setText(otp);
        }
    }
}
