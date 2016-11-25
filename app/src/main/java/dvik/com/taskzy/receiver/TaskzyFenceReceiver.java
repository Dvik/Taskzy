package dvik.com.taskzy.receiver;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import android.text.TextUtils;
import android.widget.Toast;

import com.google.android.gms.awareness.Awareness;
import com.google.android.gms.awareness.fence.FenceState;
import com.google.android.gms.awareness.snapshot.WeatherResult;
import com.google.android.gms.awareness.state.Weather;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;

import dvik.com.taskzy.R;
import dvik.com.taskzy.SituationListActivity;
import dvik.com.taskzy.TaskApplication;
import dvik.com.taskzy.service.BackGroundService;
import dvik.com.taskzy.utils.Constants;

import static android.content.Context.NOTIFICATION_SERVICE;

/**
 * Created by Divya on 11/24/2016.
 */
public class TaskzyFenceReceiver extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {

        if (TextUtils.equals(Constants.ACTION_FENCE, intent.getAction())) {
            FenceState fenceState = FenceState.extract(intent);
            Toast.makeText(context, "Received", Toast.LENGTH_SHORT).show();
            //context.unregisterReceiver(this);

            if (TextUtils.equals(Constants.IDLE_WITH_HEADPHONES_ON, fenceState.getFenceKey())) {

                if (fenceState.getCurrentState() == FenceState.TRUE) {
                    if (intent.getExtras().getString("Weather").equals("Sunny")) {
                        Intent background = new Intent(context, BackGroundService.class);
                        context.startService(background);
                    }
                }
            }


        }
    }


}