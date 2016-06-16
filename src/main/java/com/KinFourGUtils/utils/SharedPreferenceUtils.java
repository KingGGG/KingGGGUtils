package com.KinFourGUtils.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class SharedPreferenceUtils {

	public static Editor getEditor(Context context, String fileName) {
		SharedPreferences sp = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
		return sp.edit();
	}

	public static SharedPreferences getShare(Context context, String fileName) {
		SharedPreferences userInfo = context.getSharedPreferences(fileName, 0);
		return userInfo;
	}

	public static void saveString(Context context, String fileName, String key, String value) {
		Editor editor = getEditor(context, fileName);
		editor.putString(key, value);
		editor.commit();
	}

	public static String getString(Context context, String fileName, String key, String defaultValue) {
		SharedPreferences sharePreferences = getShare(context, fileName);
		String value = sharePreferences.getString(key, defaultValue);
		return value;
	}

	public static void saveInteger(Context context, String fileName, String key, int value) {
		Editor editor = getEditor(context, fileName);
		editor.putInt(key, value);
		editor.commit();
	}

	public static int getInteger(Context context, String fileName, String key, int defaultValue) {
		SharedPreferences sharePreferences = getShare(context, fileName);
		int value = sharePreferences.getInt(key, defaultValue);
		return value;
	}

	public static void saveBoolean(Context context, String fileName, String key, boolean value) {
		Editor editor = getEditor(context, fileName);
		editor.putBoolean(key, value);
		editor.commit();
	}

	public static boolean getBoolean(Context context, String fileName, String key, boolean defaultValue) {
		SharedPreferences sharePreferences = getShare(context, fileName);
		boolean value = sharePreferences.getBoolean(key, defaultValue);
		return value;
	}

	public static void saveLong(Context context, String fileName, String key, long value) {
		Editor editor = getEditor(context, fileName);
		editor.putLong(key, value);
		editor.commit();
	}

	public static long getLong(Context context, String fileName, String key, long defaultValue) {
		SharedPreferences sharePreferences = getShare(context, fileName);
		long value = sharePreferences.getLong(key, defaultValue);
		return value;
	}

	public static void deleteAllInfo(Context context, String fileName) {
		getShare(context, fileName).edit().clear();
	}
}
