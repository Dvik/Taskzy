package dvik.com.taskzy.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Divya on 11/29/2016.
 */

public class PrefManager {

    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context _context;

    int PRIVATE_MODE = 0;

    private static final String PREF_NAME = "TaskzyPref";

    private static final String KEY_ADD_DEFAULT_DATA = "shouldAdd";

    public PrefManager(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void setShouldAddDefaultData(Boolean bool) {
        editor.putBoolean(KEY_ADD_DEFAULT_DATA, bool);
        editor.commit();
    }

    public Boolean getShouldAddDefaultData() {
        return pref.getBoolean(KEY_ADD_DEFAULT_DATA, true);
    }


}
