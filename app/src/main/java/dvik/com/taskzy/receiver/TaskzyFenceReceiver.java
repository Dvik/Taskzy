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
        Toast.makeText(context, "Received", Toast.LENGTH_SHORT).show();
        Log.d("dsfs", intent.getStringExtra("id"));
        mGoogleApiClient = TaskApplication.getGoogleApiHelper().getGoogleApiClient();

        if (TextUtils.equals(intent.getStringExtra("id"), fenceState.getFenceKey())) {
            if (fenceState.getCurrentState() == FenceState.TRUE) {
                final String action = intent.getStringExtra("action");
                String id = intent.getStringExtra("id");
                final Integer weatherId = intent.getIntExtra("Weather", Weather.CONDITION_CLEAR);

                      /*if( !checkLocationPermission() ) {
                return;
            }*/
                if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                if (Utils.isPlayServicesAvailable(context)) {
                    Awareness.SnapshotApi.getWeather(mGoogleApiClient)
                            .setResultCallback(new ResultCallback<WeatherResult>() {
                                @Override
                                public void onResult(@NonNull WeatherResult weatherResult) {
                                    Weather weather = weatherResult.getWeather();

                                    if (weather.getConditions()[0] == weatherId) {
                                        Toast.makeText(mContext, "You have come to the right place dear", Toast.LENGTH_LONG).show();
                                        NotificationManager notificationManager = (NotificationManager)
                                                mContext.getSystemService(NOTIFICATION_SERVICE);
                                        // prepare intent which is triggered if the
                                        // notification is selected
                                    /*Intent intent = packageManager
                                            .getLaunchIntentForPackage(app.packageName);

                                    if (null != intent) {
                                        startActivity(intent);
                                    }*/
                                        try {
                                            Intent intent = mContext.getPackageManager().getLaunchIntentForPackage(action);
                                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                                            // use System.currentTimeMillis() to have a unique ID for the pending intent
                                            PendingIntent pIntent = PendingIntent.getActivity(mContext, (int) System.currentTimeMillis(), intent, 0);

                                            // build notification
                                            // the addAction re-use the same intent to keep the example short
                                            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(mContext)
                                                    .setContentTitle("New mail from " + "test@gmail.com")
                                                    .setContentText("Subject")
                                                    .setSmallIcon(R.drawable.ic_placeholder)
                                                    .setContentIntent(pIntent)
                                                    .setAutoCancel(true)
                                                    .addAction(R.drawable.ic_calendar, "Call", pIntent)
                                                    .addAction(R.drawable.ic_headphones, "More", pIntent)
                                                    .addAction(R.drawable.ic_error, "And more", pIntent);
                                            Notification notification = notificationBuilder.build();

                                            notificationManager.notify(0, notification);
                                        } catch (NullPointerException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }
                            });
                }
            }
        }


    }


}