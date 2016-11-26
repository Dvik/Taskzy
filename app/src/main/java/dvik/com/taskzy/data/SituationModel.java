package dvik.com.taskzy.data;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by Divya on 11/16/2016.
 */

public class SituationModel {

    String name;
    String id;
    String action;
    String actionName;
    String headPhoneState;
    String weatherState;
    LatLng placesState;
    String userActivity;
    String startTime;
    String endTime;

    public SituationModel(String name, String id, String action, String actionName, String headPhoneState, String weatherState,
                          LatLng placesState, String userActivity, String startTime, String endTime) {
        this.name = name;
        this.id = id;
        this.action = action;
        this.actionName = actionName;
        this.headPhoneState = headPhoneState;
        this.weatherState = weatherState;
        this.placesState = placesState;
        this.userActivity = userActivity;
        this.startTime = startTime;
        this.endTime = endTime;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getActionName() {
        return actionName;
    }

    public void setActionName(String actionName) {
        this.actionName = actionName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHeadPhoneState() {
        return headPhoneState;
    }

    public void setHeadPhoneState(String headPhoneState) {
        this.headPhoneState = headPhoneState;
    }

    public String getWeatherState() {
        return weatherState;
    }

    public void setWeatherState(String weatherState) {
        this.weatherState = weatherState;
    }

    public LatLng getPlacesState() {
        return placesState;
    }

    public void setPlacesState(LatLng placesState) {
        this.placesState = placesState;
    }

    public String getUserActivity() {
        return userActivity;
    }

    public void setUserActivity(String userActivity) {
        this.userActivity = userActivity;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
}
