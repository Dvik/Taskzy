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
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.awareness.Awareness;
import com.google.android.gms.awareness.fence.FenceState;
import com.google.android.gms.awareness.snapshot.WeatherResult;
import com.google.android.gms.awareness.state.Weather;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;

import dvik.com.taskzy.R;
import dvik.com.taskzy.TaskApplication;
import dvik.com.taskzy.utils.Constants;
import dvik.com.taskzy.utils.Utils;

import static android.content.Context.NOTIFICATION_SERVICE;

/**
 * Created by Divya on 11/24/2016.
 */
public class TaskzyFenceReceiver extends BroadcastReceiver {

    GoogleApiClient mGoogleApiClient;
    Context mContext;

    @Override
    public void onReceive(Context context, Intent intent) {

        this.mContext = context;
        FenceState fenceState = FenceState.extract(intent);
        mGoogleApiClient = TaskApplication.getGoogleApiHelper().getGoogleApiClient();

        if (TextUtils.equals(intent.getStringExtra("id"), fenceState.getFenceKey())) {
            if (fenceState.getCurrentState() == FenceState.TRUE) {
                final String action = intent.getStringExtra("action");
                String id = intent.getStringExtra("id");
                final Integer weatherId = intent.getIntExtra("Weather", 0);
                final String appName = intent.getStringExtra("appName");
                final String desc = intent.getStringExtra("situationDesc");

                if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    if (Utils.isPlayServicesAvailable(context)) {
                        if (weatherId != 0) {
                            Awareness.SnapshotApi.getWeather(mGoogleApiClient)
                                    .setResultCallback(new ResultCallback<WeatherResult>() {
                                        @Override
                                        public void onResult(@NonNull WeatherResult weatherResult) {
                                            Weather weather = weatherResult.getWeather();

                                            if (weather.getConditions()[0] == weatherId) {
                                                showNotification(action,appName,desc);
                                            }
                                        }
                                    });
                        } else {
                            showNotification(action,appName,desc);
                        }

                    }
                }
            }
        }


    }

    private void showNotification(String action,String appName,String desc)
    {
        NotificationManager notificationManager = (NotificationManager)
                mContext.getSystemService(NOTIFICATION_SERVICE);

        Intent intent = mContext.getPackageManager().getLaunchIntentForPackage(action);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        // use System.currentTimeMillis() to have a unique ID for the pending intent
        PendingIntent pIntent = PendingIntent.getActivity(mContext, (int) System.currentTimeMillis(), intent, 0);

        // build notification
        // the addAction re-use the same intent to keep the example short
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(mContext)
                .setContentTitle("Open " + appName)
                .setContentText(desc)
                .setSmallIcon(R.drawable.ic_launcher)
                .setContentIntent(pIntent)
                .setAutoCancel(true);

        Notification notification = notificationBuilder.build();

        notificationManager.notify(0, notification);
    }


}