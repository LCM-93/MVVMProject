package cm.mvvm.core.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import androidx.annotation.NonNull;

import com.blankj.utilcode.util.Utils;

import java.util.HashMap;
import java.util.Map;

/**
 * ****************************************************************
 * Author: LiChenMing.Chaman
 * Date: 2021-03-03 11:04
 * Desc:
 * *****************************************************************
 */
public class SharedPreferencesUtil {
    private static final String TAG = SharedPreferencesUtil.class.getSimpleName();

    private SharedPreferences sp;
    private Map<String, Object> params;


    public static SharedPreferencesUtil getDefault() {
        return new SharedPreferencesUtil(null);
    }

    public static SharedPreferencesUtil getWebSave() {
        return new SharedPreferencesUtil("aw_cookie_db");
    }

    private SharedPreferencesUtil(String db_name) {
        Context context = Utils.getApp();
        if (context == null) {
            Log.e(TAG, "Context is null");
            return;
        }
        if (db_name == null) {
            sp = PreferenceManager.getDefaultSharedPreferences(context);
        } else {
            sp = context.getSharedPreferences(db_name, Context.MODE_PRIVATE);
        }
    }

    public SharedPreferencesUtil put(@NonNull String key, @NonNull Object value) {
        if (params == null) params = new HashMap<>();
        params.put(key, value);
        return this;
    }

    public void commit() {
        if (params == null) return;
        setValue(params);
        params = null;
    }

    public void setValue(@NonNull String key, @NonNull Object value) {
        if (sp == null) {
            Log.e(TAG, "SharedPreferences is null");
            return;
        }
        try {
            SharedPreferences.Editor editor = sp.edit();
            putValue(editor, key, value);
            editor.apply();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setValue(Map<String, Object> params) {
        if (sp == null) {
            Log.e(TAG, "SharedPreferences is null");
            return;
        }
        if (params == null || params.size() <= 0) return;
        try {
            SharedPreferences.Editor editor = sp.edit();
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                if (entry.getKey() == null || entry.getValue() == null) continue;
                putValue(editor, entry.getKey(), entry.getValue());
            }
            editor.apply();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void putValue(SharedPreferences.Editor editor, String key, Object value) {
        if (value instanceof String) {
            editor.putString(key, (String) value);
        } else if (value instanceof Boolean) {
            editor.putBoolean(key, (Boolean) value);
        } else if (value instanceof Long) {
            editor.putLong(key, (Long) value);
        } else if (value instanceof Float) {
            editor.putFloat(key, (Float) value);
        } else if (value instanceof Integer) {
            editor.putInt(key, (Integer) value);
        }
    }

    public <T> T getValue(String key, @NonNull T defaultValue) {
        if (sp == null) {
            Log.e(TAG, "SharedPreferences is null");
            return null;
        }
        if (defaultValue instanceof String) {
            return (T) sp.getString(key, (String) defaultValue);
        } else if (defaultValue instanceof Boolean) {
            return (T) new Boolean(sp.getBoolean(key, (Boolean) defaultValue));
        } else if (defaultValue instanceof Long) {
            return (T) new Long(sp.getLong(key, (Long) defaultValue));
        } else if (defaultValue instanceof Float) {
            return (T) new Float(sp.getFloat(key, (Float) defaultValue));
        } else if (defaultValue instanceof Integer) {
            return (T) new Integer(sp.getInt(key, (Integer) defaultValue));
        }
        return null;
    }


    public void removeValue(String key) {
        if (sp == null) {
            Log.e(TAG, "SharedPreferences is null");
            return;
        }
        try {
            SharedPreferences.Editor editor = sp.edit();
            editor.remove(key);
            editor.apply();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
