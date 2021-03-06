package mypocketvakil.example.com.score;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.github.akashandroid90.googlesupport.location.AppLocationActivity;
import com.google.firebase.messaging.FirebaseMessaging;

import mypocketvakil.example.com.score.JavaClasses.GeoLoc;
import mypocketvakil.example.com.score.Utils.Config;
import mypocketvakil.example.com.score.Utils.NotificationUtils;
import mypocketvakil.example.com.score.activity.Info_wall;
import mypocketvakil.example.com.score.activity.Login;
import mypocketvakil.example.com.score.activity.Notification;


public class SplashActivity extends AppLocationActivity {
    private String acess_token;
    double latitude,longitude;
    private static final String TAG = Notification.class.getSimpleName();
    private BroadcastReceiver mRegistrationBroadcastReceiver;
    private TextView txtRegId, txtMessage;

    @Override
    public void myCurrentLocation(Location currentLocation) {
        super.myCurrentLocation(currentLocation);
        latitude=currentLocation.getLatitude();
        longitude=currentLocation.getLongitude();
        loc(latitude,longitude);

    }

    private void loc(double latitude, double longitude) {
        GeoLoc g=new GeoLoc().getInstance();
        g.setLatitude(latitude);
        g.setLongitude(longitude);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash2);

        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

//                 checking for type intent filter
                if (intent.getAction().equals(Config.REGISTRATION_COMPLETE)) {
//                     gcm successfully registered
//                     now subscribe to `global` topic to receive app wide notifications
                    FirebaseMessaging.getInstance().subscribeToTopic(Config.TOPIC_GLOBAL);

                    displayFirebaseRegId();

                } else if (intent.getAction().equals(Config.PUSH_NOTIFICATION)) {
//                     new push notification is received

                    String message = intent.getStringExtra("message");

                    Toast.makeText(getApplicationContext(), "Push notification: " + message, Toast.LENGTH_LONG).show();

                    txtMessage.setText(message);
                }
            }
        };

        displayFirebaseRegId();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(SplashActivity.this);
                acess_token = sharedPref.getString("access_token", null);
//                acess_token=AppSharedPreference.getString(SplashActivity.this, AppSharedPreference.PREF_KEY.Access_token);


                if (acess_token == null || acess_token == "") {


                    Intent intent = new Intent(SplashActivity.this, Login.class);
                    startActivity(intent);
                    SplashActivity.this.finish();
                } else {
                    Intent intent = new Intent(SplashActivity.this, Info_wall.class);
                    startActivity(intent);
                    SplashActivity.this.finish();
                }


            }


        }, 2000);
    }

    private void displayFirebaseRegId() {
        SharedPreferences pref = getApplicationContext().getSharedPreferences(Config.SHARED_PREF, 0);
        String regId = pref.getString("regId", null);

        Log.e(TAG, "Firebase reg id: " + regId);


    }

    @Override
    protected void onResume() {
        super.onResume();

        // register GCM registration complete receiver
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(Config.REGISTRATION_COMPLETE));

        // register new push message receiver
        // by doing this, the activity will be notified each time a new message arrives
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(Config.PUSH_NOTIFICATION));

        // clear the notification area when the app is opened
        NotificationUtils.clearNotifications(getApplicationContext());
    }

    @Override
    protected void onPause() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mRegistrationBroadcastReceiver);
        super.onPause();
    }

}
