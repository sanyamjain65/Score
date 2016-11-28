package mypocketvakil.example.com.score;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.WakefulBroadcastReceiver;

import mypocketvakil.example.com.score.Services.SimpleWakefulService;

/**
 * Created by sanyam jain on 28-11-2016.
 */
public class YourWakefulReciever extends WakefulBroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent service = new Intent(context, SimpleWakefulService.class);

        context.startService(service);
    }
}
