package minimal.microfriend.pager;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;
import minimal.microfriend.R;
import minimal.microfriend.adapter.ContactsAdapter;
import minimal.microfriend.adapter.DividiAdapter;
import minimal.microfriend.base.BaseTabPager;
import minimal.microfriend.entry.Contacts;
import minimal.microfriend.entry.User;
import minimal.microfriend.utils.MicroTools;
import minimal.microfriend.view.AutoListView;

/**
 * Created by gno on 16-5-28.
 */
public class MicroPager extends BaseTabPager {
    private String dividi[] = {"我的校友", "我的小伙伴"};
    private ArrayList<User> schoolFriend;
    private ArrayList<Contacts> ownerFriend;
    private ListView lv_dividi;
    private DividiAdapter mAdapter;

    public MicroPager(final Context context, final User user) {
        super(context);
        this.user = user;
        initData();
    }

    @Override
    public void initData() {
        LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        title_tv.setText("好友栏");
        lv_dividi = new ListView(context);
        lv_dividi.setLayoutParams(p);
        mAdapter = new DividiAdapter(dividi,context,user);
        lv_dividi.setAdapter(mAdapter);
        linearLayout.addView(lv_dividi);

        lv_dividi.setDividerHeight(0);
        lv_dividi.setOverScrollMode(View.OVER_SCROLL_NEVER);//去除阴影
        lv_dividi.setVerticalScrollBarEnabled(false);//隐藏滚动条
    }
}

