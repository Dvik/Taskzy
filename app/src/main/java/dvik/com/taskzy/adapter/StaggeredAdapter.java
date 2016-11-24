package dvik.com.taskzy.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import dvik.com.taskzy.R;
import dvik.com.taskzy.data.SituationModel;

/**
 * Created by Divya on 11/18/2016.
 */

public class StaggeredAdapter extends RecyclerView.Adapter<StaggeredAdapter.StaggeredViewHolder> {

    private List<SituationModel> staggeredList;
    private Context context;


    public static class StaggeredViewHolder extends RecyclerView.ViewHolder {
        TextView situationTitle;
        TextView headphone, weather, location, activity, time;
        LinearLayout llHeadphone, llWeather, llLocation, llActivity, llTime;

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
        }
    }

    public StaggeredAdapter(List<SituationModel> staggeredList, Context context) {
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

        holder.situationTitle.setText(staggeredList.get(position).getName());

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

        if (staggeredList.get(position).getPlacesState().equals("")) {
            holder.llLocation.setVisibility(View.GONE);
        } else {
            holder.location.setText(staggeredList.get(position).getPlacesState());
        }

        if (staggeredList.get(position).getUserActivity().equals("")) {
            holder.llActivity.setVisibility(View.GONE);
        } else {
            holder.activity.setText(staggeredList.get(position).getUserActivity());
        }

        if (staggeredList.get(position).getTime().equals("")) {
            holder.llTime.setVisibility(View.GONE);
        } else {
            holder.time.setText(staggeredList.get(position).getTime());
        }

    }

    @Override
    public int getItemCount() {
        return staggeredList.size();
    }
}
