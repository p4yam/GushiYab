package ir.kivee.gushiyab;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.PowerManager;
import android.support.v4.app.ActivityCompat;
import android.telephony.SmsMessage;

/**
 * Created by payam on 19/12/2016.
 */

@SuppressWarnings("deprecation")
public class SmsReceiver extends BroadcastReceiver {

    private static final int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 9;
    private String message;


    @Override
    public void onReceive(Context context, Intent intent) {
        PowerManager pm = (PowerManager) context.getApplicationContext().getSystemService(Context.POWER_SERVICE);
        PowerManager.WakeLock mWakeLock = pm.newWakeLock((PowerManager.SCREEN_DIM_WAKE_LOCK | PowerManager.ACQUIRE_CAUSES_WAKEUP), "YourServie");
        mWakeLock.acquire();
        mWakeLock.release();
        SharedPreferences preferences = context.getSharedPreferences("myPrefrences", Context.MODE_PRIVATE);
        String myMessage = preferences.getString("message", null);
        final Bundle bundle = intent.getExtras();

        try {
            if (bundle != null) {
                final Object[] pdusObj = (Object[]) bundle.get("pdus");
                for (int i = 0; i < pdusObj.length; i++) {

                    SmsMessage currentMessage = SmsMessage.createFromPdu((byte[]) pdusObj[i]);
                    message = currentMessage.getDisplayMessageBody();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (message.equals(myMessage)) {

            Intent i = new Intent();
            i.setClassName("ir.kivee.gushiyab", "ir.kivee.gushiyab.OnCallActivity");
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(i);
        }


    }

    public static boolean checkPermission(final Context context) {

        return ActivityCompat.checkSelfPermission
                (context, android.Manifest.permission.READ_SMS) == PackageManager.PERMISSION_GRANTED;
    }
}
