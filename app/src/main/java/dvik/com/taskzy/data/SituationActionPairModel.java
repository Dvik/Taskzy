package dvik.com.taskzy.data;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by Divya on 11/16/2016.
 */

public class SituationActionPairModel implements Parcelable{

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

    public SituationActionPairModel(String name, String id, String action, String actionName, String headPhoneState, String weatherState,
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


    protected SituationActionPairModel(Parcel in) {
        name = in.readString();
        id = in.readString();
        action = in.readString();
        actionName = in.readString();
        headPhoneState = in.readString();
        weatherState = in.readString();
        placesState = in.readParcelable(LatLng.class.getClassLoader());
        userActivity = in.readString();
        startTime = in.readString();
        endTime = in.readString();
    }

    public static final Creator<SituationActionPairModel> CREATOR = new Creator<SituationActionPairModel>() {
        @Override
        public SituationActionPairModel createFromParcel(Parcel in) {
            return new SituationActionPairModel(in);
        }

        @Override
        public SituationActionPairModel[] newArray(int size) {
            return new SituationActionPairModel[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(id);
        parcel.writeString(action);
        parcel.writeString(actionName);
        parcel.writeString(headPhoneState);
        parcel.writeString(weatherState);
        parcel.writeParcelable(placesState, i);
        parcel.writeString(userActivity);
        parcel.writeString(startTime);
        parcel.writeString(endTime);
    }
}
