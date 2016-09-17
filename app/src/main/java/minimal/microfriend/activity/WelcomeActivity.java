package minimal.microfriend.activity;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import minimal.microfriend.R;
import minimal.microfriend.base.BaseActivity;
import minimal.microfriend.entry.User;
import minimal.microfriend.utils.MicroTools;
import minimal.microfriend.utils.SharePrefences;

public class WelcomeActivity extends BaseActivity {
	private ImageView iv;
	private ValueAnimator animator;
	private boolean bool = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_welcome);
		initView();
		initData();
		animator();
	}

	public void initData() {
		Bmob.initialize(WelcomeActivity.this,"1fd27bc83141c867dc42b8f5b1d91c1e");
		if (!SharePrefences.getBoolean(this, GUIDE))
			intent = new Intent(WelcomeActivity.this,
					minimal.microfriend.activity.GuideActivity.class);
		else if(!SharePrefences.getBoolean(this, LOGINED))
			intent = new Intent(WelcomeActivity.this,
					minimal.microfriend.activity.LoginActivity.class);
		else {
			bool = true;
		}
	}

	private void autoLogin() {
		String password = SharePrefences.getString(this,USERPASSWORD);
		final String usernumber = SharePrefences.getString(this,USERNAME);
		User user = new User();
		user.setUsername(usernumber);
		user.setPassword(password);
		user.login(this, new SaveListener() {
            @Override
            public void onSuccess() {
                BmobQuery<User> userquery = new BmobQuery<User>();
                userquery.include("major,depart,school,petname");
                userquery.addWhereEqualTo("username",usernumber);
                userquery.findObjects(WelcomeActivity.this, new FindListener<User>() {
                    @Override
                    public void onSuccess(List<User> list) {
						intent = new Intent(WelcomeActivity.this,
								minimal.microfriend.MainActivity.class);
						intent.putExtra("user",list.get(0));
						startActivity(intent);
						finish();
                    }
                    @Override
                    public void onError(int i, String s) {
						MicroTools.toast(WelcomeActivity.this,"服务器错误");
                    }
                });
            }

            @Override
            public void onFailure(int i, String s) {
                //打印吐司 登录失败
				MicroTools.toast(WelcomeActivity.this,"登录失败");
            }
        });
	}

	public void initView() {
		iv = (ImageView) findViewById(R.id.iv);
	}

	private void animator() {
		animator = ValueAnimator.ofInt(1);
		animator.addUpdateListener(new AnimatorUpdateListener() {
			@Override
			public void onAnimationUpdate(ValueAnimator animation) {
				float fraction = animation.getAnimatedFraction();
				iv.setAlpha(fraction);
			}
		});
		animator.setDuration(3000);
		animator.start();
		animator.addListener(new AnimatorListener() {

			@Override
			public void onAnimationStart(Animator animation) {
			}

			@Override
			public void onAnimationRepeat(Animator animation) {
			}

			@Override
			public void onAnimationEnd(Animator animation) {
				if(!bool) {
					startActivity(intent);
					finish();
				}else {
					autoLogin();
				}
			}

			@Override
			public void onAnimationCancel(Animator animation) {
			}
		});
	}
}
