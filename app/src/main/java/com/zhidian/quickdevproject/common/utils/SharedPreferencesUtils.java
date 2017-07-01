package com.zhidian.quickdevproject.common.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.TextUtils;

import java.util.List;
import java.util.Map;

public class SharedPreferencesUtils {

    private static Context mContext;

    public static void init(Context context) {
        if (null == context) {
            throw new IllegalArgumentException("context cannot be null.");
        }
        mContext = context.getApplicationContext();
    }

    public static boolean put(String key, Object value) {
        return put(null, key, value);
    }

    public static boolean put(String name, String key, Object value) {

        if (TextUtils.isEmpty(key) || null == value) {
            throw new RuntimeException("key or value cannot be null.");
        }
        SharedPreferences.Editor editor = getSharedPreferences(name).edit();
        if (value instanceof String) {
            editor.putString(key, String.valueOf(value));
        } else if (value instanceof Boolean) {
            editor.putBoolean(key, Boolean.parseBoolean(value.toString()));
        } else if (value instanceof Float) {
            editor.putFloat(key, (Float) value);
        } else if (value instanceof Integer) {
            editor.putInt(key, (Integer) value);
        } else if (value instanceof Long) {
            editor.putLong(key, (Long) value);
        }
        return editor.commit();
    }

    private static SharedPreferences getSharedPreferences(String name) {
        if (null == mContext) {
            throw new IllegalStateException("Please invoke init method first.");
        }
        SharedPreferences sharedPreferences;
        //if (TextUtils.isEmpty(name)) {
        //    sharedPreferences = getSharedPreferences();
        //} else {
        //    sharedPreferences = mContext.getSharedPreferences(name, Context.MODE_PRIVATE);
        //}
        sharedPreferences = mContext.getSharedPreferences(
                "CloudIntercomLibrary", Context.MODE_PRIVATE);
        return sharedPreferences;
    }

    private static SharedPreferences getSharedPreferences() {
        return PreferenceManager.getDefaultSharedPreferences(mContext);
    }

    public static boolean putAll(String key, List<?> list) {
        return putAll(null, key, list);
    }

    public static boolean putAll(String name, String key, List<?> list) {
        if (TextUtils.isEmpty(key) || (list == null || list.size() == 0)) {
            throw new RuntimeException("key or list cannot be null.");
        }
        SharedPreferences.Editor editor = getSharedPreferences(name).edit();
        int size = list.size();
        if (list.get(0) instanceof String) {
            for (int i = 0; i < size; i++) {
                editor.putString(key + i, (String) list.get(i));
            }
        } else if (list.get(0) instanceof Long) {
            for (int i = 0; i < size; i++) {
                editor.putLong(key + i, (Long) list.get(i));
            }
        } else if (list.get(0) instanceof Float) {
            for (int i = 0; i < size; i++) {
                editor.putFloat(key + i, (Float) list.get(i));
            }
        } else if (list.get(0) instanceof Integer) {
            for (int i = 0; i < size; i++) {
                editor.putLong(key + i, (Integer) list.get(i));
            }
        } else if (list.get(0) instanceof Boolean) {
            for (int i = 0; i < size; i++) {
                editor.putBoolean(key + i, (Boolean) list.get(i));
            }
        }
        return editor.commit();
    }

    public static Map<String, ?> getAll() {
        return getAll(null);
    }

    public static Map<String, ?> getAll(String name) {
        return getSharedPreferences(name).getAll();
    }

    public static boolean getBoolean(String key) {
        return getBoolean(key, false);
    }


    public static boolean getBoolean(String key, boolean defaultValue) {
        return getSharedPreferences(null).getBoolean(key, defaultValue);
    }

    public static long getLong(String name) {
        return getLong(name, 0l);
    }

    public static long getLong(String name, long defaultValue) {
        return getSharedPreferences(null).getLong(name, defaultValue);
    }

    public static float getFloat(String name) {
        return getFloat(name, 0f);
    }

    public static float getFloat(String name, float defaultValue) {
        return getSharedPreferences(null).getFloat(name, defaultValue);
    }

    public static int getInt(String key) {
        return getInt(key, 0);
    }

    public static int getInt(String key, int defaultValue) {
        return getSharedPreferences(null).getInt(key, defaultValue);
    }

    public static String getString(String key) {
        return getString(key, "");
    }


    public static String getString(String key, String defaultValue) {
        return getSharedPreferences(null).getString(key, defaultValue);
    }

    public static boolean remove(String key) {
        return remove(null, key);
    }

    public static boolean remove(String name, String key) {
        SharedPreferences.Editor editor = getSharedPreferences(name).edit();
        editor.remove(key);
        return editor.commit();
    }

    public static boolean clear() {
        return clear(null);
    }

    public static boolean clear(String name) {
        SharedPreferences.Editor editor = getSharedPreferences(name).edit();
        editor.clear();
        return editor.commit();
    }

}
