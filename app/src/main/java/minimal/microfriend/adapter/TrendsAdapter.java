package minimal.microfriend.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import minimal.microfriend.R;
import minimal.microfriend.entry.Trend;
import minimal.microfriend.view.AutoListView;

public class TrendsAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<Trend> trends;

    public TrendsAdapter(Context context, ArrayList<Trend> trends) {
        this.context = context;
        this.trends = trends;
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
            holder.context_lv.setAdapter(new ReplyAdapter(this.context, trends.get(position).getReplies()));
            convertView.setTag(holder);
        }
        else holder = (ViewHolder) convertView.getTag();
        holder.context_text.setText(trends.get(position).getContext_text());
//		holder.context_image.set
        return convertView;
    }

    /**
     * 查找子控件
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