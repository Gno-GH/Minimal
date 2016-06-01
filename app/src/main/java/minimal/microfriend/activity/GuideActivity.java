package minimal.microfriend.activity;

import java.util.ArrayList;

import minimal.microfriend.R;
import minimal.microfriend.adapter.WelcomePagerAdapter;
import minimal.microfriend.base.BaseActivity;
import minimal.microfriend.utils.SharePrefences;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class GuideActivity extends BaseActivity {
    private ViewPager vp_welcome;
    private LinearLayout layout_change;
    private Button b_learn;
    private ArrayList<ImageView> views;
    private int last;
	private final int[] imageIds = { R.drawable.guide_1,
			R.drawable.guide_2, R.drawable.guide_3, R.drawable.guide_4 };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        initView();
        initData();
    }

    public void initView() {
        vp_welcome = (ViewPager) findViewById(R.id.vp_welcome);
        layout_change = (LinearLayout) findViewById(R.id.layout_change);
        b_learn = (Button) findViewById(R.id.b_learn);
        vp_welcome.setOverScrollMode(View.OVER_SCROLL_NEVER);
    }

    public void initData() {
        views = new ArrayList<ImageView>();
        ImageView imageView;
		for (int i = 0; i < imageIds.length; i++) {
            imageView = new ImageView(this);
            imageView.setBackgroundResource(imageIds[i]);
            views.add(imageView);
        }
        vp_welcome.setAdapter(new WelcomePagerAdapter(views));
        for (int k = 0; k < views.size(); k++) {
            imageView = new ImageView(this);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
            imageView.setBackgroundResource(R.drawable.welcome_selecter);
            if (k != 0) {
                params.leftMargin = 8;
                imageView.setEnabled(false);
            }
            imageView.setLayoutParams(params);
            layout_change.addView(imageView);
        }
        vp_welcome.addOnPageChangeListener(new OnPageChangeListener() {
            @Override
            public void onPageSelected(int arg0) {
                layout_change.getChildAt(arg0).setEnabled(true);
                layout_change.getChildAt(last).setEnabled(false);
                if (arg0 == 3)
                    b_learn.setVisibility(View.VISIBLE);
                else
                    b_learn.setVisibility(View.GONE);
                last = arg0;
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }

            @Override
            public void onPageScrollStateChanged(int arg0) {
            }
        });
        intent = new Intent(GuideActivity.this, minimal.microfriend.MainActivity.class);
    }

    public void learn(View view) {
        SharePrefences.setBoolbean(this, GUIDE, true);
        startActivity(intent);
        finish();
    }
}
