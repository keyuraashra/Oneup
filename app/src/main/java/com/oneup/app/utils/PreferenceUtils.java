package com.oneup.app.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.oneup.app.constants.Constants;

/**
 * Created by Scorpion on 11-02-2016.
 */
public class PreferenceUtils {
    /**
     * Insert string value in Shared Preferences
     *
     * @param context of application
     * @param value   to store in preferences
     * @param key     using which value is mapped
     * @return
     */
    public static boolean putStringInPreferences(final Context context,
                                                 final String value, final String key) {
        final SharedPreferences sharedPreferences = context
                .getSharedPreferences(Constants.PREFERENCE_NAME,
                        Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.commit();
        return true;
    }

    /**
     * Get Data from preferance
     *
     * @param context
     * @param defaultValue
     * @param key
     * @return
     */
    public static String getStringFromPreferences(final Context context,
                                                  final String defaultValue, final String key) {
        final SharedPreferences sharedPreferences = context
                .getSharedPreferences(Constants.PREFERENCE_NAME,
                        Context.MODE_PRIVATE);
        final String temp = sharedPreferences.getString(key, defaultValue);
        return temp;
    }

    /**
     * Insert booblean in preferance
     *
     * @param context
     * @param value
     * @param key
     * @return
     */
    public static boolean putBooleanInPreferences(final Context context,
                                                  final boolean value, final String key) {
        final SharedPreferences sharedPreferences = context
                .getSharedPreferences(Constants.PREFERENCE_NAME,
                        Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(key, value);
        editor.commit();
        return true;
    }

    /**
     * Get boolean from preferance
     *
     * @param context
     * @param defaultValue
     * @param key
     * @return
     */
    public static boolean getBooleanFromPreferences(final Context context,
                                                    final boolean defaultValue, final String key) {
        final SharedPreferences sharedPreferences = context
                .getSharedPreferences(Constants.PREFERENCE_NAME,
                        Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(key, defaultValue);
    }

    /**
     * Insert integer value in preferences
     *
     * @param context
     * @param value
     * @param key
     * @return
     */
    public static boolean putIntegerInPreferences(final Context context,
                                                  final int value, final String key) {
        final SharedPreferences sharedPreferences = context
                .getSharedPreferences(Constants.PREFERENCE_NAME,
                        Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(key, value);
        editor.commit();
        return true;
    }

    /**
     * Return integer preference value
     *
     * @param context
     * @param defaultValue
     * @param key
     * @return
     */
    public static int getIntegerFromPreferences(final Context context,
                                                final int defaultValue, final String key) {
        final SharedPreferences sharedPreferences = context
                .getSharedPreferences(Constants.PREFERENCE_NAME,
                        Context.MODE_PRIVATE);
        final int temp = sharedPreferences.getInt(key, defaultValue);
        return temp;
    }

    /**
     * @param context
     * @return
     */
    public static boolean isRegistered(Context context) {
        return PreferenceUtils.getBooleanFromPreferences(context, false,
                Constants.IS_REGISTERED);
    }

    /**
     * @param context
     * @param isRegistered
     */
    public static void setIsRegistered(Context context, boolean isRegistered) {
        PreferenceUtils.putBooleanInPreferences(context, isRegistered,
                Constants.IS_REGISTERED);
    }

    /**
     * Clear all preferences
     *
     * @param context
     */
    public static void clearPreferences(Context context) {
        final SharedPreferences sharedPreferences = context
                .getSharedPreferences(Constants.PREFERENCE_NAME,
                        Context.MODE_PRIVATE);
        sharedPreferences.edit().clear().commit();
    }
}
