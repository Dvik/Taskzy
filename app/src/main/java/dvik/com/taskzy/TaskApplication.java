package dvik.com.taskzy;

import android.app.Application;
import android.support.v7.app.AppCompatDelegate;

import dvik.com.taskzy.utils.GoogleApiHelper;

/**
 * Created by Divya on 11/24/2016.
 */

public class TaskApplication extends Application {
    private GoogleApiHelper googleApiHelper;
    private static TaskApplication mInstance;

    @Override
    public void onCreate() {
        super.onCreate();

        mInstance = this;
        googleApiHelper = new GoogleApiHelper(getApplicationContext());
    }

    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    public static synchronized TaskApplication getInstance() {
        return mInstance;
    }

    public GoogleApiHelper getGoogleApiHelperInstance() {
        return this.googleApiHelper;
    }
    public static GoogleApiHelper getGoogleApiHelper() {
        return getInstance().getGoogleApiHelperInstance();
    }
}