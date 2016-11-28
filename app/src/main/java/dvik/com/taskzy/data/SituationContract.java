package dvik.com.taskzy.data;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by Divya on 11/28/2016.
 */

public class SituationContract {

    public static final String CONTENT_AUTHORITY = "dvik.com.taskzy.provider.SituationProvider";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final class SituationEntry implements BaseColumns {

        public static final String TABLE_SITUATIONS = "situations";

        public static final String COLUMN_ID = "_id";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_HEADPHONE_STATE = "head_hone_state";
        public static final String COLUMN_WEATHER_STATE = "weather_state";
        public static final String COLUMN_LATITUDE = "latitude";
        public static final String COLUMN_LONGITUDE = "longitude";
        public static final String COLUMN_ACTIVITY = "activity";
        public static final String COLUMN_TIME = "time";


        public static final String[] SITUATION_PROJECTION = {COLUMN_ID, COLUMN_NAME, COLUMN_HEADPHONE_STATE,
                COLUMN_WEATHER_STATE, COLUMN_LATITUDE, COLUMN_LONGITUDE, COLUMN_ACTIVITY, COLUMN_TIME};

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon()
                .appendPath(TABLE_SITUATIONS).build();

        public static final String CONTENT_DIR_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + TABLE_SITUATIONS;

        public static Uri buildFavoritesUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }
}