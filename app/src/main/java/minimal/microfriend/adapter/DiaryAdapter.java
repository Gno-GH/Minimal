package minimal.microfriend.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;

import java.util.ArrayList;

import minimal.microfriend.R;
import minimal.microfriend.entry.Diary;

/**
 * Created by gno on 16/8/6.
 */
public class DiaryAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<Diary> mDiaries;

    public DiaryAdapter(Context context, ArrayList<Diary> diaries) {
        this.context = context;
        this.mDiaries = diaries;
    }

    @Override
    public int getCount() {
        return mDiaries.size();
    }

    @Override
    public Object getItem(int i) {
        return mDiaries.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if (view == null) {
            view = View.inflate(context, R.layout.gridview_diary, null);
            holder = new ViewHolder();
            holder.iv_img = (ImageView) view.findViewById(R.id.iv_img);
            holder.ib_del = (ImageButton) view.findViewById(R.id.ib_del);
            view.setTag(holder);
        } else
            holder = (ViewHolder) view.getTag();
        holder.iv_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                diaryLook(i);
            }
        });
        holder.ib_del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                diaryDel(i);
            }
        });
        return view;
    }

    /**
     * 日记删除
     * @param i
     */
    private void diaryDel(int i) {
        //本地删除
        //网络删除
        //视图更新
    }

    /**
     * 日记详情
     * @param i
     */
    private void diaryLook(int i) {
        //弹出窗口

        //日记可编辑
        //日记可保存 网络更新 本地更新
    }

    class ViewHolder {
        public ImageView iv_img;
        public ImageButton ib_del;
    }
}
