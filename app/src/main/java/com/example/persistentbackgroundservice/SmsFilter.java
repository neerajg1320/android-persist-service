package com.example.persistentbackgroundservice;

import android.telephony.SmsMessage;

public class SmsFilter {
    public static boolean isFromSender(SmsMessage sms, String match) {
        String sender = sms.getOriginatingAddress();
        return sender.indexOf(match) != -1 ? true: false;
    }

    public static boolean isBodyContains(SmsMessage sms, String match) {
        String body = sms.getMessageBody();
        return body.indexOf(match) != -1 ? true: false;
    }
}
