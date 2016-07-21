package minimal.microfriend.pager;

import android.content.Context;
import android.view.View;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;
import minimal.microfriend.adapter.TrendsAdapter;
import minimal.microfriend.base.BaseTabPager;
import minimal.microfriend.entry.Reply;
import minimal.microfriend.entry.Trend;
import minimal.microfriend.entry.User;
import minimal.microfriend.utils.MicroTools;
import minimal.microfriend.view.RefrenshListView;

/**
 * Created by gno on 16-5-28.
 */
public class CenterPager extends BaseTabPager {
    private RefrenshListView trend_rlv;
    private HashMap<Trend, ArrayList<Reply>> allreplies;
    private ArrayList<Reply> replies;
    private ArrayList<Trend> mTrends;
    private TrendsAdapter madapter;
    private int index = 0;
    private int postion = 0;
    private int startX = 0;
    public CenterPager(Context context, User user) {
        super(context);
        this.user = user;
    }

    @Override
    public void initData() {
        if (allreplies == null) {
            trend_rlv = new RefrenshListView(context);
            trend_rlv.setDividerHeight(0);
            trend_rlv.setOverScrollMode(View.OVER_SCROLL_NEVER);//去除阴影
            trend_rlv.setVerticalScrollBarEnabled(false);//隐藏滚动条
            initTrends();
        }
    }

    private void initTrends() {
        //初始化
        allreplies = new HashMap<Trend, ArrayList<Reply>>();

        BmobQuery<Trend> queryTrend = new BmobQuery<Trend>();//查询
        queryTrend.include("createUser");
        queryTrend.order("-updatedAt");
        queryTrend.setLimit(10);
        queryTrend.setSkip(0);
        queryTrend.findObjects(this.context, new FindListener<Trend>() {
            @Override
            public void onSuccess(List<Trend> list) {
                index = list.size()-1;
                mTrends = new ArrayList<Trend>();
                for (int i = 0;i<list.size();i++) {
                    mTrends.add(list.get(i));
                    BmobQuery<Reply> queryReply = new BmobQuery<Reply>();
                    queryReply.include("trend");
                    queryReply.include("receiver,observer");
                    queryReply.order("-createdAt");
                    queryReply.addWhereEqualTo("trend", list.get(i));
                    queryReply.findObjects(context, new FindListener<Reply>() {
                        @Override
                        public void onSuccess(List<Reply> list) {
                            replies = new ArrayList<Reply>();
                            replies = (ArrayList<Reply>) list;
                            allreplies.put(mTrends.get(postion), replies);
                            if( postion== index){
                                madapter = new TrendsAdapter(context, allreplies,user,ll_root);
                                trend_rlv.setAdapter(madapter);
                                linearLayout.addView(trend_rlv);
                                madapter.notifyDataSetChanged();
                                postion = 0;
                            }
                            postion++;
                        }
                        @Override
                        public void onError(int i, String s) {
                            MicroTools.toast(context, "查询失败" + i);
                        }
                    });
                }
            }

            @Override
            public void onError(int i, String s) {
                MicroTools.toast(context, "查询失败" + i);
            }
        });
    }
}
