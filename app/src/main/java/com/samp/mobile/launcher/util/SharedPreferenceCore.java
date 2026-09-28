package com.samp.mobile.launcher.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

public class SharedPreferenceCore {
    public static final String APP_PREFERENCES = "configlauncher";

    private SharedPreferences getPrefs(Context context) {
        if (context == null) return null;
        try {
            return context.getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        } catch (Exception e) {
            Log.e("SharedPreferenceCore", "Error getting SharedPreferences", e);
            return null;
        }
    }

    public void setString(Context context, String str, String str2) {
        SharedPreferences prefs = getPrefs(context);
        if (prefs == null) return;
        try {
            prefs.edit().putString(str, str2).apply();
        } catch (Exception e) {
            Log.e("SharedPreferenceCore", "Error setting string", e);
        }
    }

    public void setInt(Context context, String str, int i) {
        SharedPreferences prefs = getPrefs(context);
        if (prefs == null) return;
        try {
            prefs.edit().putInt(str, i).apply();
        } catch (Exception e) {
            Log.e("SharedPreferenceCore", "Error setting int", e);
        }
    }

    public void setBoolean(Context context, String str, boolean z) {
        SharedPreferences prefs = getPrefs(context);
        if (prefs == null) return;
        try {
            prefs.edit().putBoolean(str, z).apply();
        } catch (Exception e) {
            Log.e("SharedPreferenceCore", "Error setting boolean", e);
        }
    }

    public void setLong(Context context, String str, long j) {
        SharedPreferences prefs = getPrefs(context);
        if (prefs == null) return;
        try {
            prefs.edit().putLong(str, j).apply();
        } catch (Exception e) {
            Log.e("SharedPreferenceCore", "Error setting long", e);
        }
    }

    public void setFloat(Context context, String str, float f) {
        SharedPreferences prefs = getPrefs(context);
        if (prefs == null) return;
        try {
            prefs.edit().putFloat(str, f).apply();
        } catch (Exception e) {
            Log.e("SharedPreferenceCore", "Error setting float", e);
        }
    }

    public String getString(Context context, String str) {
        SharedPreferences prefs = getPrefs(context);
        if (prefs == null) return null;
        try {
            return prefs.getString(str, "");
        } catch (Exception e) {
            Log.e("SharedPreferenceCore", "Error getting string", e);
            return null;
        }
    }

    public int getInt(Context context, String str) {
        return getInt(context, str, 0);
    }

    public int getInt(Context context, String str, int defaultValue) {
        SharedPreferences prefs = getPrefs(context);
        if (prefs == null) return defaultValue;
        try {
            return prefs.getInt(str, defaultValue);
        } catch (Exception e) {
            Log.e("SharedPreferenceCore", "Error getting int", e);
            return defaultValue;
        }
    }

    public boolean getBoolean(Context context, String str) {
        SharedPreferences prefs = getPrefs(context);
        if (prefs == null) return false;
        try {
            return prefs.getBoolean(str, false);
        } catch (Exception e) {
            Log.e("SharedPreferenceCore", "Error getting boolean", e);
            return false;
        }
    }

    public long getLong(Context context, String str) {
        SharedPreferences prefs = getPrefs(context);
        if (prefs == null) return 0L;
        try {
            return prefs.getLong(str, 0L);
        } catch (Exception e) {
            Log.e("SharedPreferenceCore", "Error getting long", e);
            return 0L;
        }
    }

    public float getFloat(Context context, String str) {
        SharedPreferences prefs = getPrefs(context);
        if (prefs == null) return 0.0f;
        try {
            return prefs.getFloat(str, 0.0f);
        } catch (Exception e) {
            Log.e("SharedPreferenceCore", "Error getting float", e);
            return 0.0f;
        }
    }
}
