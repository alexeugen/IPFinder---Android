package com.example.ipfinder;

import android.app.Activity;
import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

public class Utils {
    private static int sTheme;
    public final static int MODE_DEFAULT = 0;
    public final static int MODE_NIGHT = 1;

    /**
     * Set the theme of the Activity, and restart it by creating a new Activity of the same type.
     */
    public static void changeToTheme(Activity activity, int theme) {
        sTheme = theme;
        activity.finish();
        activity.startActivity(new Intent(activity, MainActivity.class));
    }

    public static void getTheme(int theme) {
        sTheme = theme;
    }

    /**
     * Set the theme of the activity, according to the configuration.
     *
     * @param activity
     */
    public static void onActivityCreateSetTheme(AppCompatActivity activity) {
        switch (sTheme) {
            default:
            case MODE_DEFAULT:
                activity.setTheme(R.style.AppTheme);
                break;
            case MODE_NIGHT:
                activity.setTheme(R.style.AppThemeDark);
                break;
        }
    }
}