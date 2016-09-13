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
    private int[] levelImg = {R.drawable.one, R.drawable.two, R.drawable.three, R.drawable.four, R.drawable.five};
    private int[] sexImg = {R.drawable.sex_boy, R.drawable.sex_girl};

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
        int level = 1;
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
                MicroTools.toast(context, "开始聊天");
                //TODO 打开消息界面 可以进行聊天
            }
        });
        if (model == 0) {
            level = 17 - Integer.parseInt(schoolFriend.get(position).getUsername().substring(0, 2));
            hodler.tv_cname.setText(schoolFriend.get(position).getPetname());
            hodler.tv_cdepart.setText(schoolFriend.get(position).getDepart().getDname());
            if (schoolFriend.get(position).getSex().equals("男"))
                hodler.iv_sex.setBackgroundResource(sexImg[0]);
            else hodler.iv_sex.setBackgroundResource(sexImg[1]);
        } else {
            level = 17 - Integer.parseInt(ownerFriend.get(position).getFriend().getUsername().substring(0, 2));
            hodler.tv_cname.setText(ownerFriend.get(position).getFriend().getPetname());
            hodler.tv_cdepart.setText(ownerFriend.get(position).getFriend().getDepart().getDname());
            Log.d("ABC",ownerFriend.get(position).getFriend().getDepart().getDname());
            if (ownerFriend.get(position).getFriend().getSex().equals("男"))
                hodler.iv_sex.setBackgroundResource(sexImg[0]);
            else hodler.iv_sex.setBackgroundResource(sexImg[1]);
        }
        setLevel(hodler, level);
        return view;
    }

    private void setLevel(ViewHodler hodler, int level) {
        hodler.iv_level.setBackgroundResource(levelImg[level - 1]);
    }

    class ViewHodler {
        public CricularView cv_img;
        public TextView tv_cname, tv_cdepart;
        public ImageView iv_level, iv_sex, iv_sendmsg;
    }
}
