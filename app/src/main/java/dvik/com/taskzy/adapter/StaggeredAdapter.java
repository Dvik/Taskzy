package dvik.com.taskzy.adapter;

import android.Manifest;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.awareness.Awareness;
import com.google.android.gms.awareness.fence.AwarenessFence;
import com.google.android.gms.awareness.fence.DetectedActivityFence;
import com.google.android.gms.awareness.fence.FenceUpdateRequest;
import com.google.android.gms.awareness.fence.HeadphoneFence;
import com.google.android.gms.awareness.fence.LocationFence;
import com.google.android.gms.awareness.fence.TimeFence;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.ResultCallbacks;
import com.google.android.gms.common.api.Status;

import java.util.ArrayList;

import dvik.com.taskzy.R;
import dvik.com.taskzy.data.SituationContract;
import dvik.com.taskzy.utils.Constants;
import dvik.com.taskzy.utils.CursorRecyclerViewAdapter;
import dvik.com.taskzy.utils.Utils;

/**
 * Created by Divya on 11/18/2016.
 */

public class StaggeredAdapter extends CursorRecyclerViewAdapter<StaggeredAdapter.StaggeredViewHolder> {

    private Context context;
    private GoogleApiClient googleApiClient;
    private Cursor cursor;


    public static class StaggeredViewHolder extends RecyclerView.ViewHolder {
        TextView situationTitle;
        TextView headphone, weather, location, activity, time;
        LinearLayout llHeadphone, llWeather, llLocation, llActivity, llTime;
        Switch activate;
        ProgressBar progressBar;
        FrameLayout itemContainer;

        public StaggeredViewHolder(View v) {
            super(v);
            situationTitle = (TextView) v.findViewById(R.id.title_list_home);
            headphone = (TextView) v.findViewById(R.id.headphone_list_home);
            weather = (TextView) v.findViewById(R.id.weather_list_home);
            location = (TextView) v.findViewById(R.id.location_list_home);
            activity = (TextView) v.findViewById(R.id.activity_list_home);
            time = (TextView) v.findViewById(R.id.time_list_home);
            llHeadphone = (LinearLayout) v.findViewById(R.id.ll_headphone_list_home);
            llWeather = (LinearLayout) v.findViewById(R.id.ll_weather_list_home);
            llLocation = (LinearLayout) v.findViewById(R.id.ll_location_list_home);
            llActivity = (LinearLayout) v.findViewById(R.id.ll_activity_list_home);
            llTime = (LinearLayout) v.findViewById(R.id.ll_time_list_home);
            activate = (Switch) v.findViewById(R.id.switch_activate);
            progressBar = (ProgressBar) v.findViewById(R.id.progress_dialog);
            itemContainer = (FrameLayout) v.findViewById(R.id.content_item);
        }
    }

    public StaggeredAdapter(GoogleApiClient googleApiClient, Cursor cursor, Context context) {
        super(context, cursor);
        this.googleApiClient = googleApiClient;
        this.context = context;
    }

    @Override
    public StaggeredAdapter.StaggeredViewHolder onCreateViewHolder(ViewGroup parent,
                                                                   int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_home, parent, false);
        return new StaggeredViewHolder(view);
    }


    @Override
    public void onBindViewHolder(final StaggeredViewHolder holder, Cursor cursor, final int position) {

        final Integer id = cursor.getInt(cursor.getColumnIndex(SituationContract.SituationEntry.COLUMN_ID));
        final String name = cursor.getString(cursor.getColumnIndex(SituationContract.SituationEntry.COLUMN_NAME));
        final String heaphone = cursor.getString(cursor.getColumnIndex(SituationContract.SituationEntry.COLUMN_HEADPHONE_STATE));
        final String weather = cursor.getString(cursor.getColumnIndex(SituationContract.SituationEntry.COLUMN_WEATHER_STATE));
        final String latitude = cursor.getString(cursor.getColumnIndex(SituationContract.SituationEntry.COLUMN_LATITUDE));
        final String longitude = cursor.getString(cursor.getColumnIndex(SituationContract.SituationEntry.COLUMN_LONGITUDE));
        final String userActivity = cursor.getString(cursor.getColumnIndex(SituationContract.SituationEntry.COLUMN_ACTIVITY));
        final String time = cursor.getString(cursor.getColumnIndex(SituationContract.SituationEntry.COLUMN_TIME));
        final String action = cursor.getString(cursor.getColumnIndex(SituationContract.SituationEntry.COLUMN_ACTION));
        final String actionName = cursor.getString(cursor.getColumnIndex(SituationContract.SituationEntry.COLUMN_ACTION_NAME));
        final String checked = cursor.getString(cursor.getColumnIndex(SituationContract.SituationEntry.COLUMN_CHECKED));

        holder.situationTitle.setText("Open " + actionName);

        if (TextUtils.isEmpty(heaphone)) {
            holder.llHeadphone.setVisibility(View.GONE);
        } else {
            holder.headphone.setText(heaphone);
        }

        if (TextUtils.isEmpty(weather)) {
            holder.llWeather.setVisibility(View.GONE);
        } else {
            holder.weather.setText(weather);
        }

        if (TextUtils.isEmpty(latitude) || TextUtils.isEmpty(longitude)) {
            holder.llLocation.setVisibility(View.GONE);
        } else {
            holder.location.setText(latitude + "," + longitude);
        }

        if (TextUtils.isEmpty(userActivity)) {
            holder.llActivity.setVisibility(View.GONE);
        } else {
            holder.activity.setText(userActivity);
        }

        if (TextUtils.isEmpty(time)) {
            holder.llTime.setVisibility(View.GONE);
        } else {
            holder.time.setText(Utils.getProperDate(Long.parseLong(time), "dd/MM/yyyy hh:mm"));
        }

        if (!TextUtils.isEmpty(checked)) {
            if (TextUtils.equals(checked, "1"))
                holder.activate.setChecked(true);
            else holder.activate.setChecked(false);
        }

        holder.activate.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                holder.itemContainer.setBackgroundColor(ContextCompat.getColor(context,R.color.item_progress));
                holder.progressBar.setVisibility(View.VISIBLE);
                String[] stateArray;

                stateArray = new String[]{String.valueOf(id),
                        heaphone,
                        weather,
                        latitude,
                        longitude,
                        userActivity,
                        time};

                if (isChecked) {
                    setPairChecked("1", String.valueOf(id));
                    startSituationReceiver(holder.itemContainer,holder.progressBar,stateArray, action, actionName);
                } else {
                    setPairChecked("0", String.valueOf(id));
                    stopSituationReceiver(holder.itemContainer,holder.progressBar,String.valueOf(id));
                }


            }
        });

    }

    @Override
    public int getItemCount() {
        return super.getItemCount();
    }

    private void setPairChecked(String checked, String id) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(SituationContract.SituationEntry.COLUMN_CHECKED, checked);

        context.getContentResolver().update(SituationContract.SituationEntry.CONTENT_URI, contentValues,
                SituationContract.SituationEntry.COLUMN_ID + "= ?", new String[]{String.valueOf(id)});
    }

    private void startSituationReceiver(final View container, final View progress, String[] stateArray, String action, String actionName) {

        Intent intent = new Intent(Constants.ACTION_FENCE);
        intent.putExtra("action", action);
        intent.putExtra("appName", actionName);

        intent.putExtra("id", stateArray[0]);

        ArrayList<AwarenessFence> awarenessFences = new ArrayList<AwarenessFence>();

        if (!stateArray[1].equals("")) {
            awarenessFences.add(HeadphoneFence.during(Constants.getHeadPhoneStateInteger(stateArray[1], context)));
        }

        if (!stateArray[2].equals("")) {
            Integer weatherId = Constants.getWeatherStateInteger(stateArray[2], context);
            intent.putExtra("Weather", weatherId);
        }

        if (!stateArray[3].equals("") && !stateArray[4].equals("")) {
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            awarenessFences.add(LocationFence.in(Double.parseDouble(stateArray[3]), Double.parseDouble(stateArray[4]), 50.00, 5000));
        }

        if (!stateArray[5].equals("")) {
            awarenessFences.add(DetectedActivityFence.during(Constants.getActivityStateInteger(stateArray[5], context)));
        }

        if (!stateArray[6].equals("")) {
            awarenessFences.add(TimeFence.inInterval(Long.parseLong(stateArray[6]), Long.parseLong(stateArray[6]) + 5L));
        }

        AwarenessFence customFence = AwarenessFence.and(awarenessFences);

        PendingIntent fencePendingIntent = PendingIntent.getBroadcast(context, Integer.valueOf(stateArray[0]), intent, 0);

        Awareness.FenceApi.updateFences(googleApiClient, new FenceUpdateRequest.Builder()
                .addFence(stateArray[0], customFence, fencePendingIntent)
                .build())
                .setResultCallback(new ResultCallback<Status>() {
                    @Override
                    public void onResult(@NonNull Status status) {

                        container.setBackgroundColor(ContextCompat.getColor(context,R.color.item_back));
                        progress.setVisibility(View.GONE);
                        if (status.isSuccess()) {
                        }

                        if (status.isCanceled() || status.isInterrupted()) {
                            Toast.makeText(context, "Couldn't activate fence. Please try again!!", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    private void stopSituationReceiver(final View container, final View progress,String id) {
        Intent intent = new Intent(Constants.ACTION_FENCE);
        PendingIntent fencePendingIntent = PendingIntent.getBroadcast(context,
                Integer.valueOf(id), intent, PendingIntent.FLAG_UPDATE_CURRENT);
        fencePendingIntent.cancel();

        Awareness.FenceApi.updateFences(
                googleApiClient,
                new FenceUpdateRequest.Builder()
                        .removeFence(id)
                        .build()).setResultCallback(new ResultCallbacks<Status>() {
            @Override
            public void onSuccess(@NonNull Status status) {
                container.setBackgroundColor(ContextCompat.getColor(context,R.color.item_back));
                progress.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(@NonNull Status status) {
                container.setBackgroundColor(ContextCompat.getColor(context,R.color.item_back));
                progress.setVisibility(View.GONE);
                Toast.makeText(context, "Couldn't de-activate fence. Please try again!!", Toast.LENGTH_LONG).show();

            }
        });

    }
}
