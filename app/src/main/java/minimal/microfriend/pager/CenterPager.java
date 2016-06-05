package minimal.microfriend.pager;

import android.content.Context;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;
import minimal.microfriend.adapter.TrendsAdapter;
import minimal.microfriend.base.BaseTabPager;
import minimal.microfriend.entry.Reply;
import minimal.microfriend.entry.Trend;

/**
 * Created by gno on 16-5-28.
 */
public class CenterPager extends BaseTabPager {
    private ListView trend_lv;
    private HashMap<Trend, ArrayList<Reply>> allreplies;
    private ArrayList<Reply> replies;
    public CenterPager(Context context) {
        super(context);
    }

    @Override
    public void initData() {
        initTrends();
//        if (isaddview) {
//            trend_lv = new ListView(context);
//            trend_lv.setAdapter(new TrendsAdapter(this.context, allreplies));
////            trend_lv.setOverScrollMode(View.OVER_SCROLL_NEVER);//去除阴影
//            trend_lv.setVerticalScrollBarEnabled(false);//隐藏滚动条
//            linearLayout.addView(trend_lv);
//        }
    }

    private void initTrends() {
        allreplies = new HashMap<Trend,ArrayList<Reply>>();
        BmobQuery<Trend> queryTrend = new BmobQuery<Trend>();//查询
        queryTrend.include("createUser");
        queryTrend.order("-updatedAt");
        queryTrend.setLimit(10);
        queryTrend.setSkip(0);
        queryTrend.findObjects(this.context, new FindListener<Trend>() {
            @Override
            public void onSuccess(List<Trend> list) {
                Toast.makeText(context,"查询成功"+list.get(0).getCreateUser().getUsername(),Toast.LENGTH_SHORT).show();
                for (Trend t : list){
                    replies = new ArrayList<Reply>();
                    BmobQuery<Reply> queryReply = new BmobQuery<Reply>();
                    queryReply.include("trend");
                    queryReply.include("observer");
                    queryReply.include("receiver");
                    queryReply.addWhereEqualTo("trend",t);
                    queryReply.findObjects(context, new FindListener<Reply>() {
                        @Override
                        public void onSuccess(List<Reply> list) {
                            replies = (ArrayList<Reply>) list;
                            Toast.makeText(context,"查询成功"+list.size(),Toast.LENGTH_SHORT).show();
                        }
                        @Override
                        public void onError(int i, String s) {
                            Toast.makeText(context,"查询失败2",Toast.LENGTH_SHORT).show();
                        }
                    });
                    allreplies.put(t,replies);
                }
            }
            @Override
            public void onError(int i, String s) {
                Toast.makeText(context,"查询失败",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
