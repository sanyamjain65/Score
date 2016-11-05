package mypocketvakil.example.com.score.Preferences;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by sanyam jain on 27-09-2016.
 */
public class SharedPreference {
    public static final String MyPREFERENCES = "LogInInfo";
    public static final String UserId = "IdKey";
    private static SharedPreferences.Editor prefsEditor;
    private static SharedPreference instance;
    private SharedPreferences preferences;

    private SharedPreference() {
    }

    private SharedPreference(Context context) {
        preferences = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        prefsEditor = preferences.edit();
    }

    public static SharedPreference getInstance(Context context) {
        if (instance == null) {
            instance = new SharedPreference(context);
        }
        return instance;
    }

    public void saveValue(String key,String value) {
        prefsEditor.putString(key, value);
        prefsEditor.commit();
    }

    public String getString(String key) {
        return preferences.getString(key,null);
    }
}
