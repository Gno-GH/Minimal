package minimal.microfriend.pager;

import android.content.Context;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;
import minimal.microfriend.adapter.TrendsAdapter;
import minimal.microfriend.base.BaseTabPager;
import minimal.microfriend.entry.Trend;

/**
 * Created by gno on 16-5-28.
 */
public class CenterPager extends BaseTabPager {
    private ListView trend_lv;
    private ArrayList<Trend> trends;

    public CenterPager(Context context) {
        super(context);
    }

    @Override
    public void initData() {
        initTrends();
        if (isaddview) {
            trend_lv = new ListView(context);
            trend_lv.setAdapter(new TrendsAdapter(this.context, trends));
//            trend_lv.setOverScrollMode(View.OVER_SCROLL_NEVER);//去除阴影
            trend_lv.setVerticalScrollBarEnabled(false);//隐藏滚动条
            linearLayout.addView(trend_lv);
        }
    }

    private void initTrends() {
        trends = new ArrayList<Trend>();
        BmobQuery<Trend> queryTrend = new BmobQuery<Trend>();//查询
        queryTrend.order("-updatedAt");
        queryTrend.setLimit(10);
        queryTrend.setSkip(0);
        queryTrend.findObjects(this.context, new FindListener<Trend>() {
            @Override
            public void onSuccess(List<Trend> list) {
                trends = (ArrayList<Trend>) list;
//                Toast.makeText(context, "SUCCESS", Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onError(int i, String s) {

            }
        });
    }
}
