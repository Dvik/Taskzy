package dvik.com.taskzy.utils;

import android.content.Context;

import com.google.android.gms.awareness.fence.DetectedActivityFence;
import com.google.android.gms.awareness.state.HeadphoneState;
import com.google.android.gms.awareness.state.Weather;

import dvik.com.taskzy.R;


/**
 * Created by Divya on 11/16/2016.
 */

public class Constants {

    public final static String ACTION_FENCE = "dvik.taskzy.action_fence";

    public static int getHeadPhoneStateInteger(String state, Context context) {

        if (state.equals(context.getString(R.string.headphone_plugged)))
            return HeadphoneState.PLUGGED_IN;

        if (state.equals(context.getString(R.string.headphone_unplugged)))
            return HeadphoneState.UNPLUGGED;

        return HeadphoneState.UNPLUGGED;

    }

    public static int getWeatherStateInteger(String state, Context context) {
        if (state.equals(context.getString(R.string.weather_hazy)))
            return Weather.CONDITION_HAZY;

        if (state.equals(context.getString(R.string.weather_snowy)))
            return Weather.CONDITION_SNOWY;

        if (state.equals(context.getString(R.string.weather_clear)))
            return Weather.CONDITION_CLEAR;

        if (state.equals(context.getString(R.string.weather_cloudy)))
            return Weather.CONDITION_CLOUDY;

        if (state.equals(context.getString(R.string.weather_foggy)))
            return Weather.CONDITION_FOGGY;

        if (state.equals(context.getString(R.string.weather_icy)))
            return Weather.CONDITION_ICY;

        if (state.equals(context.getString(R.string.weather_rainy)))
            return Weather.CONDITION_RAINY;

        if (state.equals(context.getString(R.string.weather_stormy)))
            return Weather.CONDITION_STORMY;

        if (state.equals(context.getString(R.string.weather_windy)))
            return Weather.CONDITION_WINDY;

        return Weather.CONDITION_HAZY;
    }

    public static int getActivityStateInteger(String state, Context context) {
        if (state.equals(context.getString(R.string.in_vehicle)))
            return DetectedActivityFence.IN_VEHICLE;

        if (state.equals(context.getString(R.string.on_bicycle)))
            return DetectedActivityFence.ON_BICYCLE;

        if (state.equals(context.getString(R.string.on_foot)))
            return DetectedActivityFence.ON_FOOT;

        if (state.equals(context.getString(R.string.running)))
            return DetectedActivityFence.RUNNING;

        if (state.equals(context.getString(R.string.still)))
            return DetectedActivityFence.STILL;

        if (state.equals(context.getString(R.string.tilting)))
            return DetectedActivityFence.TILTING;

        if (state.equals(context.getString(R.string.walking)))
            return DetectedActivityFence.WALKING;

        return DetectedActivityFence.STILL;
    }

}
