package minimal.microfriend.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class SharePrefences {
	private static final String CONFIG = "cofing";
	private static SharedPreferences preferences;

	/**
	 * 从cofing中获取一个布尔值默认为false
	 * 
	 * @param context
	 * @param key
	 * @return
	 */
	public static boolean getBoolean(Context context, String key) {
		preferences = context
				.getSharedPreferences(CONFIG, Context.MODE_PRIVATE);
		boolean value = preferences.getBoolean(key, false);
		return value;
	}
	/**
	 * 从cofing中获取一个字符串默认为null
	 *
	 * @param context
	 * @param key
	 * @return
	 */
	public static String getString(Context context, String key) {
		preferences = context
				.getSharedPreferences(CONFIG, Context.MODE_PRIVATE);
		String value = preferences.getString(key, "null");
		return value;
	}

	/**
	 * 写入一个布尔值到config
	 * 
	 * @param context
	 * @param key
	 * @param value
	 */
	public static void setBoolbean(Context context, String key, Boolean value) {
		preferences = context
				.getSharedPreferences(CONFIG, Context.MODE_PRIVATE);
		Editor editor = preferences.edit();
		editor.putBoolean(key, value);
		editor.commit();
	}

	/**
	 * 写入一个字符串到config
	 * @param context
	 * @param key
	 * @param value
     */
	public  static void setString(Context context, String key, String value) {
		preferences = context
				.getSharedPreferences(CONFIG, Context.MODE_PRIVATE);
		Editor editor = preferences.edit();
		editor.putString(key,value);
		editor.commit();
	}
}
