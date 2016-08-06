package minimal.microfriend.activity;

import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import java.util.ArrayList;

import minimal.microfriend.R;
import minimal.microfriend.adapter.DiaryAdapter;
import minimal.microfriend.base.BaseActivity;
import minimal.microfriend.entry.Diary;

/**
 * Created by gno on 16/8/6.
 */
public class DiaryActivity extends BaseActivity implements View.OnClickListener {
    private Button b_diary_close, b_diary_add;
    private GridView gv_diary;
    private TextView tv_no_one;
    private DiaryAdapter mDiaryAdapter;
    private LinearLayout ll_parent;
    private PopupWindow mDiary;
    private View popView;
    private ImageButton ib_close;
    private Button b_ok;

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
        mDiary = new PopupWindow(popView,
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        mDiary.setBackgroundDrawable(new BitmapDrawable());
        mDiary.setOutsideTouchable(true);
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
        //TODO: 日记查询
        ArrayList<Diary> diaries = new ArrayList<Diary>();
        for (int i = 0; i < 5; i++)
            diaries.add(new Diary());
        if (diaries.size() > 0)
            tv_no_one.setVisibility(View.INVISIBLE);
        mDiaryAdapter = new DiaryAdapter(DiaryActivity.this, diaries);
        gv_diary.setAdapter(mDiaryAdapter);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.b_diary_close:
                finish();
                break;
            case R.id.b_diary_add:
                mDiary.showAtLocation(ll_parent, Gravity.CENTER, 0, 0);
                break;
            case R.id.ib_close:
                mDiary.dismiss();
                break;
            case R.id.b_ok:
                //TODO: 日记添加完成
                mDiary.dismiss();
                break;
        }
    }
}
