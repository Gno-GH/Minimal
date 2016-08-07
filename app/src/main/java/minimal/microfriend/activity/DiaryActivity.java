package minimal.microfriend.activity;

import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import minimal.microfriend.R;
import minimal.microfriend.adapter.DiaryAdapter;
import minimal.microfriend.base.BaseActivity;
import minimal.microfriend.entry.Diary;
import minimal.microfriend.utils.MicroTools;

/**
 * Created by gno on 16/8/6.
 */
public class DiaryActivity extends BaseActivity implements View.OnClickListener {
    private Button b_diary_close, b_diary_add;
    private GridView gv_diary;
    private TextView tv_no_one;
    private DiaryAdapter mDiaryAdapter;
    private LinearLayout ll_parent, ll_bg;
    private PopupWindow mDiaryPop;
    private View popView;
    private ImageButton ib_close;
    private Button b_ok;
    private RadioButton rb_bg_1, rb_bg_2, rb_bg_3;
    private RadioButton rb_cloudy, rb_fine, rb_rain, rb_snow;
    private EditText et_year, et_date, et_context;
    private ArrayList<Diary> mDiaries;
    private Diary mDiary;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary);
        initView();
        initPopWindows();
        initData();
    }

    private void initPopWindows() {
        popView = View.inflate(DiaryActivity.this, R.layout.pop_mdiary, null);
        ib_close = (ImageButton) popView.findViewById(R.id.ib_close);
        ib_close.setOnClickListener(this);
        b_ok = (Button) popView.findViewById(R.id.b_ok);
        b_ok.setOnClickListener(this);

        ll_bg = (LinearLayout) popView.findViewById(R.id.ll_bg);
        rb_bg_1 = (RadioButton) popView.findViewById(R.id.rb_bg_1);
        rb_bg_2 = (RadioButton) popView.findViewById(R.id.rb_bg_2);
        rb_bg_3 = (RadioButton) popView.findViewById(R.id.rb_bg_3);
        rb_bg_1.setOnClickListener(this);
        rb_bg_2.setOnClickListener(this);
        rb_bg_3.setOnClickListener(this);

        rb_snow = (RadioButton) popView.findViewById(R.id.rb_snow);
        rb_cloudy = (RadioButton) popView.findViewById(R.id.rb_cloudy);
        rb_fine = (RadioButton) popView.findViewById(R.id.rb_fine);
        rb_rain = (RadioButton) popView.findViewById(R.id.rb_rain);

        et_year = (EditText) popView.findViewById(R.id.et_year);
        et_date = (EditText) popView.findViewById(R.id.et_date);
        et_context = (EditText) popView.findViewById(R.id.et_context);

        mDiaryPop = new PopupWindow(popView,
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        mDiaryPop.setBackgroundDrawable(new BitmapDrawable());
        mDiaryPop.setOutsideTouchable(true);
    }

    @Override
    public void initView() {
        ll_parent = (LinearLayout) findViewById(R.id.ll_parent);
        b_diary_close = (Button) findViewById(R.id.b_diary_close);
        b_diary_add = (Button) findViewById(R.id.b_diary_add);
        gv_diary = (GridView) findViewById(R.id.gv_diary);
        tv_no_one = (TextView) findViewById(R.id.tv_no_one);
        b_diary_close.setOnClickListener(this);
        b_diary_add.setOnClickListener(this);
    }

    @Override
    public void initData() {
        user = (minimal.microfriend.entry.User) getIntent().getSerializableExtra("user");
        mDiaries = new ArrayList<Diary>();
        BmobQuery<Diary> diaryQuery = new BmobQuery<Diary>();
        diaryQuery.include("user");
        diaryQuery.addWhereEqualTo("user", user);
        diaryQuery.findObjects(DiaryActivity.this, new FindListener<Diary>() {
            @Override
            public void onSuccess(List<Diary> list) {
                //TODO:缓存本地日记
                mDiaries = (ArrayList<Diary>) list;
                if (list.size() > 0)
                    tv_no_one.setVisibility(View.INVISIBLE);
                mDiaryAdapter = new DiaryAdapter(DiaryActivity.this, mDiaries, tv_no_one, ll_parent);
                gv_diary.setAdapter(mDiaryAdapter);
            }

            @Override
            public void onError(int i, String s) {
                MicroTools.toast(DiaryActivity.this, s);
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.b_diary_close:
                finish();
                break;
            case R.id.b_diary_add:
                mDiary = new Diary();
                //TODO:日记图片未设置
                mDiary.setUser(user);
                mDiary.setBgtype(1);
                mDiary.setWeather(2);
                mDiary.setDate("没写");
                mDiary.setYear("没写");
                mDiary.setContent("没写");
                mDiaryPop.showAtLocation(ll_parent, Gravity.CENTER, 0, 0);
                break;
            case R.id.ib_close:
                mDiaryPop.dismiss();
                break;
            case R.id.b_ok:
                setWeatherAndContent();
                mDiary.save(DiaryActivity.this, new SaveListener() {
                    @Override
                    public void onSuccess() {
                        //TODO:添加本地日记
                        mDiaries.add(mDiary);
                        tv_no_one.setVisibility(View.INVISIBLE);
                        mDiaryAdapter.notifyDataSetChanged();
                        et_year.setText("");
                        et_date.setText("");
                        et_context.setText("");
                        rb_fine.setChecked(true);
                        rb_bg_1.setChecked(true);
                        ll_bg.setBackgroundResource(R.drawable.diary_bg_1);
                        mDiaryPop.dismiss();
                    }

                    @Override
                    public void onFailure(int i, String s) {
                        MicroTools.toast(DiaryActivity.this, "亲网络似乎在开小差!");
                    }
                });
                break;
            case R.id.rb_bg_1:
                mDiary.setBgtype(1);
                ll_bg.setBackgroundResource(R.drawable.diary_bg_1);
                break;
            case R.id.rb_bg_2:
                mDiary.setBgtype(2);
                ll_bg.setBackgroundResource(R.drawable.diary_bg_2);
                break;
            case R.id.rb_bg_3:
                mDiary.setBgtype(3);
                ll_bg.setBackgroundResource(R.drawable.diary_bg_3);
                break;
        }
    }

    private void setWeatherAndContent() {
        if (rb_cloudy.isChecked())
            mDiary.setWeather(1);
        if (rb_fine.isChecked())
            mDiary.setWeather(2);
        if (rb_rain.isChecked())
            mDiary.setWeather(3);
        if (rb_snow.isChecked())
            mDiary.setWeather(4);
        if (!et_year.getText().toString().trim().equals(""))
            mDiary.setYear(et_year.getText().toString().trim());
        if (!et_date.getText().toString().trim().equals(""))
            mDiary.setDate(et_date.getText().toString().trim());
        if (!et_context.getText().toString().trim().equals(""))
            mDiary.setContent(et_context.getText().toString().trim());
    }
}
