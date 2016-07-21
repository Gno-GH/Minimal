package minimal.microfriend.adapter;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import minimal.microfriend.R;
import minimal.microfriend.entry.Reply;
import minimal.microfriend.entry.Trend;
import minimal.microfriend.entry.User;
import minimal.microfriend.utils.MicroTools;
import minimal.microfriend.view.AutoListView;

public class TrendsAdapter extends BaseAdapter{
    private Context context;
    private ArrayList<Trend> trends;
    private HashMap<Trend, ArrayList<Reply>> allreplies;
    private User user;
    private PopupWindow mComment;
    private View popView;
    private LinearLayout ll_pop;
    public TrendsAdapter(Context context, HashMap<Trend, ArrayList<Reply>> allreplies,User user,LinearLayout ll_pop) {
        this.context = context;
        this.allreplies = allreplies;
        this.user = user;
        this.ll_pop = ll_pop;
        trends = new ArrayList<Trend>();
        List t = Arrays.asList(allreplies.keySet().toArray());
        for (Object o:t) {
            trends.add((Trend) o);
        }

    }

    @Override
    public int getCount() {
        return trends.size();
    }

    @Override
    public Object getItem(int position) {
        return trends.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = View.inflate(this.context, R.layout.center_listview_item, null);
            holder = new ViewHolder();
            findViewId(position, convertView, holder);
            childOnClick(holder,position);
            holder.context_lv.setAdapter(new ReplyAdapter(this.context, allreplies.get(trends.get(position))));
            convertView.setTag(holder);
        } else holder = (ViewHolder) convertView.getTag();
        if(!trends.get(position).getCreateUser().getObjectId().equals(user.getObjectId()))
            holder.del_ib.setVisibility(View.INVISIBLE);
        holder.context_text.setText(trends.get(position).getContentText());
        holder.create_time.setText(trends.get(position).getCreatedAt().toString());
        holder.user_name.setText(trends.get(position).getCreateUser().getPetname());
//		holder.context_image.set
        return convertView;
    }

    private void childOnClick(ViewHolder holder,int position) {
        holder.del_ib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MicroTools.toast(context,"删除");
            }
        });
        holder.comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popView = View.inflate(context,R.layout.pop_comment,null);
                mComment = new PopupWindow(popView,
                        ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
                mComment.setBackgroundDrawable(new BitmapDrawable());
                mComment.setOutsideTouchable(true);
                mComment.showAtLocation(ll_pop, Gravity.CENTER, 0, 0);
                MicroTools.toast(context,"回复");
            }
        });
        holder.like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MicroTools.toast(context,"喜欢");
            }
        });
        holder.dislike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MicroTools.toast(context,"讨厌");
            }
        });
    }

    /**
     * 查找子控件
     *
     * @param position
     * @param convertView
     * @param holder
     */
    private void findViewId(int position, View convertView, ViewHolder holder) {
        holder.head_iv = (ImageView) convertView.findViewById(R.id.head_iv);
        holder.user_name = (TextView) convertView.findViewById(R.id.user_name);
        holder.create_time = (TextView) convertView.findViewById(R.id.create_time);
        holder.del_ib = (ImageButton) convertView.findViewById(R.id.del_ib);
        holder.comment = (ImageButton) convertView.findViewById(R.id.comment);
        holder.dislike = (ImageButton) convertView.findViewById(R.id.dislike);
        holder.like = (ImageButton) convertView.findViewById(R.id.like);
        holder.context_text = (TextView) convertView.findViewById(R.id.context_text);
        holder.context_image = (ImageView) convertView.findViewById(R.id.context_image);
        holder.context_lv = (AutoListView) convertView.findViewById(R.id.context_lv);
    }

    class ViewHolder {
        public ImageView head_iv;//头像
        public TextView user_name;//昵称
        public TextView create_time;//创建时间
        public ImageButton del_ib;//删除按钮
        public ImageButton comment;//回复按钮
        public ImageButton dislike;//扔鸡蛋
        public ImageButton like;//喜欢
        public TextView context_text;//文本内容
        public ImageView context_image;//图片内容
        public AutoListView context_lv;//评论集合
    }
}