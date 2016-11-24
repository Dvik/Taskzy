package dvik.com.taskzy.data;

/**
 * Created by Divya on 11/16/2016.
 */

public class SituationModel {

    String name;
    String headPhoneState;
    String weatherState;
    String placesState;
    String userActivity;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    String time;

    public SituationModel(String name,String headPhoneState,String weatherState,
                          String placesState,String userActivity,String time)
    {
        this.name = name;
        this.headPhoneState = headPhoneState;
        this.weatherState = weatherState;
        this.placesState = placesState;
        this.userActivity = userActivity;
        this.time = time;
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

    public String getPlacesState() {
        return placesState;
    }

    public void setPlacesState(String placesState) {
        this.placesState = placesState;
    }

    public String getUserActivity() {
        return userActivity;
    }

    public void setUserActivity(String userActivity) {
        this.userActivity = userActivity;
    }
}
