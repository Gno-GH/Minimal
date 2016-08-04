package minimal.microfriend.base;

import android.app.Activity;
import android.content.Intent;

import minimal.microfriend.entry.User;

public abstract class BaseActivity extends Activity {
	public static final String GUIDE = "guide";
	public static final String LOGINED = "logined";
	public static final String USERPASSWORD = "password";
	public static final String USERNAME = "username";
	public Intent intent;
	public abstract void initView();
	public User user;
	public void initData(){}
}
