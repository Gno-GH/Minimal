package minimal.microfriend.activity;

import minimal.microfriend.R;
import minimal.microfriend.base.BaseActivity;
import minimal.microfriend.utils.SharePrefences;
import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

public class WelcomeActivity extends BaseActivity {
	private ImageView iv;
	private ValueAnimator animator;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_welcome);
		initView();
		initData();
		animator();
	}

	public void initData() {
		if (!SharePrefences.getBoolean(this, GUIDE))
			intent = new Intent(WelcomeActivity.this,
					minimal.microfriend.activity.GuideActivity.class);
		else if(!SharePrefences.getBoolean(this, LOGINED))
			intent = new Intent(WelcomeActivity.this,
					minimal.microfriend.activity.LoginActivity.class);
		else
			intent = new Intent(WelcomeActivity.this,
					minimal.microfriend.MainActivity.class);
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
		animator.addListener(new AnimatorListener() {

			@Override
			public void onAnimationStart(Animator animation) {
			}

			@Override
			public void onAnimationRepeat(Animator animation) {
			}

			@Override
			public void onAnimationEnd(Animator animation) {
				startActivity(intent);
				finish();
			}

			@Override
			public void onAnimationCancel(Animator animation) {
			}
		});
		animator.setDuration(3000);
		animator.start();
	}

}
