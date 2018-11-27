package me.jessyan.mvparms.demo.app.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.jess.arms.utils.Preconditions;

import java.util.Map;

/**
 * @package me.jessyan.mvparms.demo.app.utils
 * @fileName SpUtils
 * @date 2018/11/21
 * @describe
 * @email shenzhencuco@gmail
 */
public class SpUtils {
    private static SpUtils mSputils;
    private Context mContext;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private String defaultFileName = "cuco";

    private SpUtils(Context context, String filename) {
        Preconditions.checkNotNull(context, "Context is null");
        mContext = context;
        sharedPreferences = context.getSharedPreferences(filename,
                Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    private SpUtils(Context context) {
        Preconditions.checkNotNull(context, "Context is null");
        mContext = context;
        sharedPreferences = context.getSharedPreferences(defaultFileName,
                Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public static SpUtils with(Context context) {
        if (mSputils == null) {
            mSputils = new SpUtils(context);
        }
        return new SpUtils(context);
    }

    /**
     * 保存数据到Sharedpreference
     *
     * @param key
     * @param value
     */
    public void put(String key, Object value) {
        Preconditions.checkNotNull(key, "key not empty");
        if (value instanceof String) {
            editor.putString(key, (String) value);
        } else if (value instanceof Integer) {
            editor.putInt(key, (Integer) value);
        } else if (value instanceof Boolean) {
            editor.putBoolean(key, (Boolean) value);
        } else if (value instanceof Float) {
            editor.putFloat(key, (Float) value);
        } else if (value instanceof Long) {
            editor.putLong(key, (Long) value);
        } else {
            editor.putString(key, value.toString());
        }
        editor.commit();
    }

    /**
     * 获取sharedpreferences值
     *
     * @param key
     * @param defaultValue
     * @return
     */
    public Object get(String key, Object defaultValue) {
        Preconditions.checkNotNull(key, "key not empty");
        if (defaultValue instanceof String) {
            return sharedPreferences.getString(key, (String) defaultValue);
        }
        if (defaultValue instanceof Integer) {
            return sharedPreferences.getInt(key, (Integer) defaultValue);
        }
        if (defaultValue instanceof Long) {
            return sharedPreferences.getLong(key, (Long) defaultValue);
        }
        if (defaultValue instanceof Float) {
            return sharedPreferences.getFloat(key, (Float) defaultValue);
        }
        if (defaultValue instanceof Boolean) {
            return sharedPreferences.getBoolean(key, (Boolean) defaultValue);
        } else {
            return sharedPreferences.getString(key, null);
        }
    }

    /**
     * 移除指定的值
     *
     * @param key
     */
    public void remove(String key) {
        editor.remove(key);
        editor.commit();
    }

    /**
     * 包含某个指定的值
     *
     * @param key
     * @return
     */
    public boolean contains(String key) {
        return sharedPreferences.contains(key);
    }

    /**
     * 返回所有的键值
     *
     * @return
     */
    public Map<String, ?> getAll() {
        return sharedPreferences.getAll();
    }

}
