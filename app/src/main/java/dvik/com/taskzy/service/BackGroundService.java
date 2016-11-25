package dvik.com.taskzy.service;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

import com.google.android.gms.awareness.Awareness;
import com.google.android.gms.awareness.snapshot.WeatherResult;
import com.google.android.gms.awareness.state.Weather;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;

import dvik.com.taskzy.R;
import dvik.com.taskzy.SituationListActivity;
import dvik.com.taskzy.TaskApplication;

/**
 * Created by Divya on 11/25/2016.
 */

public class BackGroundService extends Service {

    private boolean isRunning;
    private Context context;
    private Thread backgroundThread;
    private GoogleApiClient mGoogleApiClient;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        this.context = this;
        this.isRunning = false;
        this.backgroundThread = new Thread(myTask);
        mGoogleApiClient = TaskApplication.getGoogleApiHelper().getGoogleApiClient();
    }

    private Runnable myTask = new Runnable() {
        public void run() {
            // Do something here
            detectWeather(context,"");
            stopSelf();
        }
    };

    @Override
    public void onDestroy() {
        this.isRunning = false;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if(!this.isRunning) {
            this.isRunning = true;
            this.backgroundThread.start();
        }
        return START_STICKY;
    }

    private void detectWeather(final Context context, String weatherString) {
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
        Awareness.SnapshotApi.getWeather(mGoogleApiClient)
                .setResultCallback(new ResultCallback<WeatherResult>() {
                    @Override
                    public void onResult(@NonNull WeatherResult weatherResult) {
                        Weather weather = weatherResult.getWeather();

                        if (weather.getConditions()[0] == Weather.CONDITION_HAZY) {
                            Toast.makeText(context, "You have come to the right place dear", Toast.LENGTH_LONG).show();
                            NotificationManager notificationManager = (NotificationManager)
                                    context.getSystemService(NOTIFICATION_SERVICE);
                            // prepare intent which is triggered if the
                            // notification is selected

                            Intent intent = new Intent(context, SituationListActivity.class);
                            // use System.currentTimeMillis() to have a unique ID for the pending intent
                            PendingIntent pIntent = PendingIntent.getActivity(context, (int) System.currentTimeMillis(), intent, 0);

                            // build notification
                            // the addAction re-use the same intent to keep the example short
                            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context)
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
                        }
                    }
                });
    }
}
