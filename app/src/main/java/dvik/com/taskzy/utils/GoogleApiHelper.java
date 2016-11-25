package dvik.com.taskzy.utils;

import android.content.Context;
import android.util.Log;

import com.google.android.gms.awareness.Awareness;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;

/**
 * Created by Divya on 11/24/2016.
 */
public class GoogleApiHelper implements GoogleApiClient.OnConnectionFailedListener {
    private static final String TAG = GoogleApiHelper.class.getSimpleName();

    GoogleApiClient mGoogleApiClient;
    Context context;

    public GoogleApiHelper(Context context) {
        Log.d(TAG,"constructor");

        this.context = context;
        buildGoogleApiClient();
        connect();
    }

    public GoogleApiClient getGoogleApiClient() {
        return this.mGoogleApiClient;
    }

    public void connect() {
        Log.d(TAG,"connect");
        if (mGoogleApiClient != null) {
            mGoogleApiClient.connect();
        }
    }

    public boolean isConnected() {
        if (mGoogleApiClient != null) {
            return mGoogleApiClient.isConnected();
        } else {
            return false;
        }
    }

    private void buildGoogleApiClient() {
        Log.d(TAG,"buildConnect");

        if(mGoogleApiClient==null) {
            mGoogleApiClient = new GoogleApiClient.Builder(context)
                    .addApi(Awareness.API)
                    .build();
        }
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
    }
}