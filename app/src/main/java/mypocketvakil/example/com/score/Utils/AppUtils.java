package mypocketvakil.example.com.score.Utils;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.view.inputmethod.InputMethodManager;

/**
 * Created by sanyam jain on 15-09-2016.
 */
public class AppUtils extends Application {
    public static void hideKeyBoard(Activity activity) {
        try {
            InputMethodManager inputMethodManager = ((InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE));
            inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
        } catch (Exception e) {
            //	e.printStackTrace();
        }
    }
}
