package dvik.com.taskzy.adapter;

import android.Manifest;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
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
import java.util.List;
import java.util.Random;

import dvik.com.taskzy.R;
import dvik.com.taskzy.data.SituationModel;
import dvik.com.taskzy.utils.Constants;

/**
 * Created by Divya on 11/18/2016.
 */

public class StaggeredAdapter extends RecyclerView.Adapter<StaggeredAdapter.StaggeredViewHolder> {

    private List<SituationModel> staggeredList;
    private Context context;
    private GoogleApiClient googleApiClient;


    public static class StaggeredViewHolder extends RecyclerView.ViewHolder {
        TextView situationTitle;
        TextView headphone, weather, location, activity, time;
        LinearLayout llHeadphone, llWeather, llLocation, llActivity, llTime;
        Switch activate;

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
        }
    }

    public StaggeredAdapter(GoogleApiClient googleApiClient, List<SituationModel> staggeredList, Context context) {
        this.googleApiClient = googleApiClient;
        this.staggeredList = staggeredList;
        this.context = context;
    }

    @Override
    public StaggeredAdapter.StaggeredViewHolder onCreateViewHolder(ViewGroup parent,
                                                                   int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_home, parent, false);
        return new StaggeredViewHolder(view);
    }


    @Override
    public void onBindViewHolder(final StaggeredViewHolder holder, final int position) {

        holder.situationTitle.setText(staggeredList.get(position).getActionName());

        if (staggeredList.get(position).getHeadPhoneState().equals("")) {
            holder.llHeadphone.setVisibility(View.GONE);
        } else {
            holder.headphone.setText(staggeredList.get(position).getHeadPhoneState());
        }

        if (staggeredList.get(position).getWeatherState().equals("")) {
            holder.llWeather.setVisibility(View.GONE);
        } else {
            holder.weather.setText(staggeredList.get(position).getWeatherState());
        }

        if (staggeredList.get(position).getPlacesState() == null) {
            holder.llLocation.setVisibility(View.GONE);
        } else {
            holder.location.setText(String.valueOf(staggeredList.get(position).getPlacesState().latitude)
                    + String.valueOf(staggeredList.get(position).getPlacesState().longitude));
        }

        if (staggeredList.get(position).getUserActivity().equals("")) {
            holder.llActivity.setVisibility(View.GONE);
        } else {
            holder.activity.setText(staggeredList.get(position).getUserActivity());
        }

        if (staggeredList.get(position).getStartTime().equals("")) {
            holder.llTime.setVisibility(View.GONE);
        } else {
            holder.time.setText(staggeredList.get(position).getStartTime() + " " + staggeredList.get(position).getEndTime());
        }

        holder.activate.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                String[] stateArray;
                if (staggeredList.get(position).getPlacesState() == null) {
                    stateArray = new String[]{staggeredList.get(position).getHeadPhoneState(),
                            staggeredList.get(position).getWeatherState(),
                            "",
                            "",
                            staggeredList.get(position).getUserActivity(),
                            staggeredList.get(position).getStartTime(),
                            staggeredList.get(position).getEndTime()};
                } else {
                    stateArray = new String[]{staggeredList.get(position).getHeadPhoneState(),
                            staggeredList.get(position).getWeatherState(),
                            String.valueOf(staggeredList.get(position).getPlacesState().latitude),
                            String.valueOf(staggeredList.get(position).getPlacesState().longitude),
                            staggeredList.get(position).getUserActivity(),
                            staggeredList.get(position).getStartTime(),
                            staggeredList.get(position).getEndTime()};
                }
                if (isChecked) {
                    startSituationReceiver(position, stateArray, staggeredList.get(position).getAction());
                } else {
                    stopSituationReceiver(position);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return staggeredList.size();
    }

    private void startSituationReceiver(int pos, String[] stateArray, String action) {

        int uniqueInt = new Random().nextInt(543254);

        Intent intent = new Intent(Constants.ACTION_FENCE);
        intent.putExtra("action", action);

        String id = String.valueOf(uniqueInt);
        intent.putExtra("id", id);
        staggeredList.get(pos).setId(id);

        ArrayList<AwarenessFence> awarenessFences = new ArrayList<AwarenessFence>();

        if (!stateArray[0].equals("")) {
            awarenessFences.add(HeadphoneFence.during(Constants.getHeadPhoneStateInteger(stateArray[0], context)));
        }

        if (!stateArray[1].equals("")) {
            Integer weatherId = Constants.getWeatherStateInteger(stateArray[1], context);
            intent.putExtra("Weather", weatherId);
        }

        if (!stateArray[2].equals("") && !stateArray[3].equals("")) {
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            awarenessFences.add(LocationFence.in(Double.parseDouble(stateArray[2]), Double.parseDouble(stateArray[3]), 50.00, 5000));
        }

        if (!stateArray[4].equals("")) {
            awarenessFences.add(DetectedActivityFence.during(Constants.getActivityStateInteger(stateArray[4], context)));
        }

        if (!stateArray[5].equals("")) {
            awarenessFences.add(TimeFence.inInterval(Long.parseLong(stateArray[5]), Long.parseLong(stateArray[6])));
        }

        AwarenessFence customFence = AwarenessFence.and(awarenessFences);

        PendingIntent fencePendingIntent = PendingIntent.getBroadcast(context, uniqueInt, intent, 0);

        Awareness.FenceApi.updateFences(googleApiClient, new FenceUpdateRequest.Builder()
                .addFence(id, customFence, fencePendingIntent)
                .build())
                .setResultCallback(new ResultCallback<Status>() {
                    @Override
                    public void onResult(@NonNull Status status) {
                        if (status.isSuccess()) {
                            Toast.makeText(context, "Activated Successfully", Toast.LENGTH_LONG).show();
                        }

                        if (status.isCanceled() || status.isInterrupted()) {
                            Toast.makeText(context, "Couldn't activate fence. Please try again!!", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    private void stopSituationReceiver(int pos) {
        Intent intent = new Intent(Constants.ACTION_FENCE);
        PendingIntent fencePendingIntent = PendingIntent.getBroadcast(context,
                Integer.valueOf(staggeredList.get(pos).getId()), intent, PendingIntent.FLAG_UPDATE_CURRENT);
        fencePendingIntent.cancel();

        Awareness.FenceApi.updateFences(
                googleApiClient,
                new FenceUpdateRequest.Builder()
                        .removeFence(staggeredList.get(pos).getId())
                        .build()).setResultCallback(new ResultCallbacks<Status>() {
            @Override
            public void onSuccess(@NonNull Status status) {
                Toast.makeText(context, "De-activated successfully", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(@NonNull Status status) {
                Toast.makeText(context, "Couldn't de-activate fence. Please try again!!", Toast.LENGTH_LONG).show();

            }
        });

    }
}
