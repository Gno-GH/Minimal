package minimal.microfriend.pager;

import android.content.Context;
import android.util.Log;
import android.view.View;
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
    private View dividi_1, dividi_2;
    private ViewHolder holder_1, holder_2;
    private ArrayList<User> schoolFriend;
    private ArrayList<Contacts> ownerFriend;
    private boolean switch1 = true, switch2 = true;
    private boolean dswitch_1 = true, dswitch_2 = true;
    private ContactsAdapter dividiAdapter1, dividiAdapter2;

    public MicroPager(final Context context, final User user) {
        super(context);
        this.user = user;
        schoolFriend = new ArrayList<User>();
        ownerFriend = new ArrayList<Contacts>();
        title_tv.setText("好友栏");
        dividi_1 = View.inflate(context, R.layout.divide_listview_item, null);
        dividi_2 = View.inflate(context, R.layout.divide_listview_item, null);

        linearLayout.addView(dividi_1);
        linearLayout.addView(dividi_2);

        holder_1 = new ViewHolder();
        holder_2 = new ViewHolder();
        holder_1.tv_dividi_name = (TextView) dividi_1.findViewById(R.id.tv_dividi_name);
        holder_2.tv_dividi_name = (TextView) dividi_2.findViewById(R.id.tv_dividi_name);
        holder_1.iv_dividi_deal = (ImageView) dividi_1.findViewById(R.id.iv_dividi_deal);
        holder_2.iv_dividi_deal = (ImageView) dividi_2.findViewById(R.id.iv_dividi_deal);
        holder_1.lv_concacts = (ListView) dividi_1.findViewById(R.id.lv_concacts);
        holder_2.lv_concacts = (ListView) dividi_2.findViewById(R.id.lv_concacts);

        initData();
    }

    @Override
    public void initData() {
        holder_1.iv_dividi_deal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder_2.lv_concacts.setVisibility(View.GONE);
                if (dswitch_1) {
                    holder_1.lv_concacts.setVisibility(View.VISIBLE);
                    if(!dswitch_2)dswitch_2=true;
                    dswitch_1 = false;
                } else {
                    holder_1.lv_concacts.setVisibility(View.GONE);
                    dswitch_1 = true;
                }
                if (switch1) {
                    switch1 = false;
                    BmobQuery<User> bmobQuery = new BmobQuery<User>();
                    bmobQuery.include("depart,userphoto,school,major");
                    bmobQuery.addWhereEqualTo("school", user.getSchool());
                    bmobQuery.findObjects(context, new FindListener<User>() {
                        @Override
                        public void onSuccess(List<User> list) {
                            int i = 0;
                            for (User u : list) {
                                if (u.getUsername().equals(user.getUsername())) break;
                                i++;
                            }
                            list.remove(i);
                            schoolFriend = (ArrayList<User>) list;
                            dividiAdapter1 = new ContactsAdapter(user, context, schoolFriend, ownerFriend, 0);
                            holder_1.lv_concacts.setAdapter(dividiAdapter1);
                            switch1 = true;
                        }

                        @Override
                        public void onError(int i, String s) {
                            MicroTools.toast(context, s);
                            switch1 = true;
                        }
                    });
                }
            }
        });
        holder_2.iv_dividi_deal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder_1.lv_concacts.setVisibility(View.GONE);
                if (dswitch_2) {
                    holder_2.lv_concacts.setVisibility(View.VISIBLE);
                    if(!dswitch_1)dswitch_1=true;
                    dswitch_2 = false;
                } else {
                    holder_2.lv_concacts.setVisibility(View.GONE);
                    dswitch_2 = true;
                }
                if (switch2) {
                    switch2 = false;
                    BmobQuery<Contacts> bmobQuery = new BmobQuery<Contacts>();
                    bmobQuery.include("Friend,Owner");
                    bmobQuery.addWhereEqualTo("Owner", user);
                    bmobQuery.findObjects(context, new FindListener<Contacts>() {
                        @Override
                        public void onSuccess(List<Contacts> list) {
                            ownerFriend = (ArrayList<Contacts>) list;
                            dividiAdapter2 = new ContactsAdapter(user, context, schoolFriend, ownerFriend, 1);
                            holder_2.lv_concacts.setAdapter(dividiAdapter2);
                            switch2 = true;
                        }

                        @Override
                        public void onError(int i, String s) {
                            MicroTools.toast(context, s);
                            switch2 = true;
                        }
                    });
                }
            }
        });

        holder_1.tv_dividi_name.setText(dividi[0]);
        holder_2.tv_dividi_name.setText(dividi[1]);
    }

    class ViewHolder {
        public TextView tv_dividi_name;
        public ImageView iv_dividi_deal;
        public ListView lv_concacts;
    }
}

