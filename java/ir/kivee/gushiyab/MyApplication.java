package ir.kivee.gushiyab;

import android.app.Application;

import com.onesignal.OneSignal;

/**
 * Created by payam on 19/12/2016.
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        OneSignal.startInit(this).init();

        // OneSignal.syncHashedEmail(userEmail);
    }
}
