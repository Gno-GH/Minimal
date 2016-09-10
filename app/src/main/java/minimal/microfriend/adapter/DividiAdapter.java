package minimal.microfriend.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;
import minimal.microfriend.R;
import minimal.microfriend.entry.Contacts;
import minimal.microfriend.entry.User;
import minimal.microfriend.utils.MicroTools;
import minimal.microfriend.view.AutoListView;

/**
 * Created by gno on 16/9/10.
 */
public class DividiAdapter extends BaseAdapter {
    private User user;
    private String[] dividi;
    private Context context;
    private boolean switch1 = true, switch2 = true;
    private boolean dswitch_1 = true, dswitch_2 = true;
    private ContactsAdapter contactsAdapter_1, contactsAdapter_2;
    private ArrayList<User> schoolFriend;
    private ArrayList<Contacts> ownerFriend;
    private ArrayList<AutoListView> autos;

    public DividiAdapter(String[] dividi, Context context, User user) {
        this.dividi = dividi;
        this.context = context;
        this.user = user;
        this.autos = new ArrayList<AutoListView>();
    }

    @Override
    public int getCount() {
        return dividi.length;
    }

    @Override
    public Object getItem(int position) {
        return dividi[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ViewHolder holder;
        if (view == null) {
            view = View.inflate(context, R.layout.divide_listview_item, null);
            holder = new ViewHolder();
            holder.tv_dividi_name = (TextView) view.findViewById(R.id.tv_dividi_name);
            holder.iv_dividi_deal = (ImageView) view.findViewById(R.id.iv_dividi_deal);
            holder.lv_concacts = (AutoListView) view.findViewById(R.id.lv_concacts);
            view.setTag(holder);
        } else holder = (ViewHolder) view.getTag();
        if (position == 0) {
            if (autos.size() == 0) autos.add(holder.lv_concacts);
            hoderSetOne(holder);
        } else {
            if (autos.size() == 1) autos.add(holder.lv_concacts);
            hoderSetTwo(holder);
        }
        holder.tv_dividi_name.setText(dividi[position]);
        return view;
    }

    private void hoderSetTwo(final ViewHolder holder) {
        holder.iv_dividi_deal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                autos.get(0).setVisibility(View.GONE);
                if (dswitch_2) {
                    holder.lv_concacts.setVisibility(View.VISIBLE);
                    if (!dswitch_1) dswitch_1 = true;
                    dswitch_2 = false;
                } else {
                    holder.lv_concacts.setVisibility(View.GONE);
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
                            ownerFriend = new ArrayList<Contacts>();
                            ownerFriend = (ArrayList<Contacts>) list;
                            contactsAdapter_2 = new ContactsAdapter(user, context, schoolFriend, ownerFriend, 1);
                            holder.lv_concacts.setAdapter(contactsAdapter_2);
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
    }

    private void hoderSetOne(final ViewHolder holder) {
        holder.iv_dividi_deal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                autos.get(1).setVisibility(View.GONE);
                if (dswitch_1) {
                    holder.lv_concacts.setVisibility(View.VISIBLE);
                    if (!dswitch_2) dswitch_2 = true;
                    dswitch_1 = false;
                } else {
                    holder.lv_concacts.setVisibility(View.GONE);
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
                            schoolFriend = new ArrayList<User>();
                            schoolFriend = (ArrayList<User>) list;
                            contactsAdapter_1 = new ContactsAdapter(user, context, schoolFriend, ownerFriend, 0);
                            holder.lv_concacts.setAdapter(contactsAdapter_1);
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
    }

    class ViewHolder {
        public TextView tv_dividi_name;
        public ImageView iv_dividi_deal;
        public AutoListView lv_concacts;
    }
}