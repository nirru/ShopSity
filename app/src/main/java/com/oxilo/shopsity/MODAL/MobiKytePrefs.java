package com.oxilo.shopsity.MODAL;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

/**
 * Created by ericbasendra on 29/11/15.
 */
public class MobiKytePrefs {

    private static MobiKytePrefs mobiKytePrefs;
    private final Context context;
    private final SharedPreferences preferences;
    private final SharedPreferences.Editor  editor;
    private static Gson GSON            = new Gson();
    Type typeOfObject    = new TypeToken<Object>(){}.getType();

    private MobiKytePrefs(Context context, String namePreferences, int mode) {
        this.context = context;
        if (namePreferences == null || namePreferences.equals("")) {
            namePreferences = "mobikyte";
        }
        preferences = context.getSharedPreferences(namePreferences, mode);
        editor = preferences.edit();
    }

    public static MobiKytePrefs getComplexPreferences(Context context,
                                                           String namePreferences, int mode) {
        if (mobiKytePrefs == null) {
            mobiKytePrefs = new MobiKytePrefs(context,
                    namePreferences, mode);
        }
        return mobiKytePrefs;
    }

    public void putObject(String key, Object object) {
        if (object == null) {
            throw new IllegalArgumentException("Object is null");
        }
        if (key.equals("") || key == null) {
            throw new IllegalArgumentException("Key is empty or null");
        }
        editor.putString(key, GSON.toJson(object));
    }

    public void commit() {
        editor.commit();
    }

    public <T> T getObject(String key, Class<T> a) {
        String gson = preferences.getString(key, null);
        if (gson == null) {
            return null;
        }
        else {
            try {
                return GSON.fromJson(gson, a);
            }
            catch (Exception e) {
                throw new IllegalArgumentException("Object stored with key "
                        + key + " is instance of other class");
            }
        }
    }
}
