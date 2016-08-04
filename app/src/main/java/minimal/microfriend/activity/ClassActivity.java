package minimal.microfriend.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import minimal.microfriend.R;
import minimal.microfriend.adapter.WeekAdapter;
import minimal.microfriend.base.BaseActivity;

/**
 * Created by gno on 16/8/2.
 */
public class ClassActivity extends BaseActivity{
    private ListView lv_week;
    private String[] mWeeks = {"星期一","星期二","星期三","星期四","星期五"};
    private WeekAdapter mWeekAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acticity_class);
        initView();
        initData();
    }

    @Override
    public void initView() {
        lv_week = (ListView) findViewById(R.id.lv_week);
    }

    @Override
    public void initData() {
        mWeekAdapter = new WeekAdapter(ClassActivity.this,mWeeks);
        lv_week.setAdapter(mWeekAdapter);
        lv_week.setOverScrollMode(View.OVER_SCROLL_NEVER);//去除阴影
        lv_week.setVerticalScrollBarEnabled(false);//隐藏滚动条
    }
}
