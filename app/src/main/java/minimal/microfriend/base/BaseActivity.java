package minimal.microfriend.base;

import android.app.Activity;
import android.content.Intent;

public abstract class BaseActivity extends Activity {
	public static final String GUIDE = "guide";
	public Intent intent;
	public abstract void initView();
	public void initData(){}
}
