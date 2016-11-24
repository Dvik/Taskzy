package dvik.com.taskzy;

import android.Manifest;
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.awareness.Awareness;
import com.google.android.gms.awareness.fence.AwarenessFence;
import com.google.android.gms.awareness.fence.DetectedActivityFence;
import com.google.android.gms.awareness.fence.FenceState;
import com.google.android.gms.awareness.fence.FenceUpdateRequest;
import com.google.android.gms.awareness.fence.HeadphoneFence;
import com.google.android.gms.awareness.snapshot.WeatherResult;
import com.google.android.gms.awareness.state.HeadphoneState;
import com.google.android.gms.awareness.state.Weather;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;

import java.util.ArrayList;
import java.util.List;

import dvik.com.taskzy.adapter.StaggeredAdapter;
import dvik.com.taskzy.data.SituationModel;
import dvik.com.taskzy.utils.Constants;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, GoogleApiClient.OnConnectionFailedListener {

    RecyclerView recyclerView;
    StaggeredGridLayoutManager staggeredGridLayoutManager;
    List<SituationModel> situationList;
    StaggeredAdapter staggeredAdapter;
    GoogleApiClient mGoogleApiClient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(Awareness.API)
                .build();
        mGoogleApiClient.connect();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setUpTestFence();
            }
        });

        init();

        staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        staggeredGridLayoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(staggeredGridLayoutManager);

        situationList.add(new SituationModel("Open 1MG", "Headphones Plugged In", "Sunny", "", "Walking", "5:30 27 July 2016"));
        situationList.add(new SituationModel("Open Flipkart", "Headphones Plugged Off", "Cloudy", "", "Idle", ""));
        situationList.add(new SituationModel("Open GoIbibo", "", "Warm", "", "Idle", ""));
        situationList.add(new SituationModel("Open Amazon", "", "Hot", "", "Bicycling", "3:20 2 October 2016"));
        situationList.add(new SituationModel("Open Music Player", "", "", "", "Idle", ""));
        situationList.add(new SituationModel("Open Wallpapers", "Headphones Plugged Off", "", "", "", ""));

        staggeredAdapter = new StaggeredAdapter(situationList, HomeActivity.this);
        recyclerView.setAdapter(staggeredAdapter);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


    }

    private void init() {
        recyclerView = (RecyclerView) findViewById(R.id.content_home);
        situationList = new ArrayList<>();
    }

    private void setUpTestFence() {

        ArrayList<AwarenessFence> awarenessFences = new ArrayList<AwarenessFence>();

        awarenessFences.add(DetectedActivityFence.during(DetectedActivityFence.STILL));

        awarenessFences.add(HeadphoneFence.during(HeadphoneState.PLUGGED_IN));


        AwarenessFence stillWithHeadPhoneFence = AwarenessFence.and(awarenessFences);

        Intent intent = new Intent(Constants.ACTION_FENCE);
        intent.putExtra("Weather","Sunny");
        PendingIntent fencePendingIntent = PendingIntent.getBroadcast(HomeActivity.this, 0, intent, 0);

        TaskzyFenceReceiver mFenceBroadcastReceiver = new TaskzyFenceReceiver();
        registerReceiver(mFenceBroadcastReceiver, new IntentFilter(Constants.ACTION_FENCE));

        FenceUpdateRequest.Builder builder = new FenceUpdateRequest.Builder();
        builder.addFence(Constants.IDLE_WITH_HEADPHONES_ON, stillWithHeadPhoneFence, fencePendingIntent);

        Awareness.FenceApi.updateFences(mGoogleApiClient, builder.build());
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }


    public class TaskzyFenceReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (TextUtils.equals(Constants.ACTION_FENCE, intent.getAction())) {
                FenceState fenceState = FenceState.extract(intent);
                Toast.makeText(context, "Received", Toast.LENGTH_SHORT).show();
                //context.unregisterReceiver(this);

                if (TextUtils.equals(Constants.IDLE_WITH_HEADPHONES_ON, fenceState.getFenceKey())) {

                    if (fenceState.getCurrentState() == FenceState.TRUE) {
                        if(intent.getExtras().getString("Weather").equals("Sunny")) {
                            detectWeather(context,"Sunny");
                        }
                    }
                }


            }
        }

        private void detectWeather(final Context context, String weatherString) {
            /*if( !checkLocationPermission() ) {
                return;
            }*/
            if (ActivityCompat.checkSelfPermission(HomeActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
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
                                        getSystemService(NOTIFICATION_SERVICE);
                                // prepare intent which is triggered if the
                                // notification is selected

                                Intent intent = new Intent(context, SituationListActivity.class);
                                // use System.currentTimeMillis() to have a unique ID for the pending intent
                                PendingIntent pIntent = PendingIntent.getActivity(context, (int) System.currentTimeMillis(), intent, 0);

                                // build notification
                                // the addAction re-use the same intent to keep the example short
                                Notification n  = new Notification.Builder(context)
                                        .setContentTitle("New mail from " + "test@gmail.com")
                                        .setContentText("Subject")
                                        .setSmallIcon(R.drawable.ic_placeholder)
                                        .setContentIntent(pIntent)
                                        .setAutoCancel(true)
                                        .addAction(R.drawable.ic_calendar, "Call", pIntent)
                                        .addAction(R.drawable.ic_headphones, "More", pIntent)
                                        .addAction(R.drawable.ic_error, "And more", pIntent).build();

                                notificationManager.notify(0, n);
                            }
                        }
                    });
        }
    }

}
