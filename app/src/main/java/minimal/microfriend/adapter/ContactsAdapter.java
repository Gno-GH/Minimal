package minimal.microfriend.adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import minimal.microfriend.R;
import minimal.microfriend.entry.Contacts;
import minimal.microfriend.entry.User;
import minimal.microfriend.utils.MicroTools;
import minimal.microfriend.view.CricularView;

/**
 * Created by gno on 16/9/8.
 */
public class ContactsAdapter extends BaseAdapter {
    private Context context;
    private User user;
    private ArrayList<User> schoolFriend;
    private ArrayList<Contacts> ownerFriend;
    private int model;//适配器模式

    public ContactsAdapter(User user, Context context, ArrayList<User> schoolFriend, ArrayList<Contacts> ownerFriend, int model) {
        this.user = user;
        this.context = context;
        this.schoolFriend = schoolFriend;
        this.ownerFriend = ownerFriend;
        this.model = model;
    }

    @Override
    public int getCount() {
        if (model == 0) return schoolFriend.size();
        else return ownerFriend.size();
    }

    @Override
    public Object getItem(int position) {
        if (model == 0) return schoolFriend.get(position);
        else return ownerFriend.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ViewHodler hodler;
        if (view == null) {
            view = View.inflate(context, R.layout.contacts_listview_item, null);
            hodler = new ViewHodler();
            hodler.cv_img = (CricularView) view.findViewById(R.id.cv_img);
            hodler.tv_cname = (TextView) view.findViewById(R.id.tv_cname);
            hodler.tv_cdepart = (TextView) view.findViewById(R.id.tv_cdepart);
            hodler.iv_level = (ImageView) view.findViewById(R.id.iv_level);
            hodler.iv_sex = (ImageView) view.findViewById(R.id.iv_sex);
            hodler.iv_sendmsg = (ImageView) view.findViewById(R.id.iv_sendmsg);
            view.setTag(hodler);
        } else {
            hodler = (ViewHodler) view.getTag();
        }
        hodler.iv_sendmsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MicroTools.toast(context,"开始聊天");
                //TODO 打开消息界面 可以进行聊天
            }
        });
        if (model == 0) {
            //TODO :好友信息设置 系院无法查询
            hodler.tv_cname.setText(schoolFriend.get(position).getPetname());
            hodler.tv_cdepart.setText(schoolFriend.get(position).getDepart().getDname());
        }else {
            hodler.tv_cname.setText(ownerFriend.get(position).getFriend().getPetname());
            hodler.tv_cdepart.setText(ownerFriend.get(position).getFriend().getDepart().getDname());
        }
        return view;
    }

    class ViewHodler {
        public CricularView cv_img;
        public TextView tv_cname, tv_cdepart;
        public ImageView iv_level, iv_sex,iv_sendmsg;
    }
}
