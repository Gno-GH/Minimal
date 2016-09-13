package minimal.microfriend.adapter;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;

import cn.bmob.v3.listener.DeleteListener;
import cn.bmob.v3.listener.DownloadFileListener;
import minimal.microfriend.R;
import minimal.microfriend.entry.Diary;
import minimal.microfriend.utils.MicroTools;

/**
 * 日记适配器
 * Created by gno on 16/8/6.
 */
public class DiaryAdapter extends BaseAdapter implements View.OnClickListener {
    private Context context;
    private ArrayList<Diary> mDiaries;
    private TextView tv_no_one;
    private LinearLayout ll_parent, ll_bg;
    private PopupWindow mDiaryPop;
    private View popView;
    private ImageButton ib_close;
    private RadioButton rb_bg_1, rb_bg_2, rb_bg_3;
    private RadioButton rb_cloudy, rb_fine, rb_rain, rb_snow;
    private EditText et_year, et_date, et_context;
    private ImageView iv_img;

    public DiaryAdapter(Context context, ArrayList<Diary> diaries, TextView tv_no_one, LinearLayout ll_parent) {
        this.context = context;
        this.mDiaries = diaries;
        this.ll_parent = ll_parent;
        this.tv_no_one = tv_no_one;
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
            holder.tv_date = (TextView) view.findViewById(R.id.tv_date);
            view.setTag(holder);
        } else
            holder = (ViewHolder) view.getTag();
        if (mDiaries.get(i).getBgtype() == 1)
            holder.iv_img.setBackgroundResource(R.drawable.diary_bg_1);
        if (mDiaries.get(i).getBgtype() == 2)
            holder.iv_img.setBackgroundResource(R.drawable.diary_bg_2);
        if (mDiaries.get(i).getBgtype() == 3)
            holder.iv_img.setBackgroundResource(R.drawable.diary_bg_3);
        holder.tv_date.setText(mDiaries.get(i).getCreatedAt().substring(5, 10));
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
     *
     * @param i
     */
    private void diaryDel(final int i) {
        //TODO 删除本地日记
        mDiaries.get(i).delete(context, new DeleteListener() {
            @Override
            public void onSuccess() {
                mDiaries.remove(mDiaries.get(i));
                if (mDiaries.size() == 0)
                    tv_no_one.setVisibility(View.VISIBLE);
                notifyDataSetChanged();
            }

            @Override
            public void onFailure(int i, String s) {
                MicroTools.toast(context, "网络似乎在开小差!");
            }
        });
    }

    /**
     * 日记详情
     *
     * @param i
     */
    private void diaryLook(int i) {
        initPopWindows();
        initPopBg(i);
        initPopOther(i);
        mDiaryPop.showAtLocation(ll_parent, Gravity.CENTER, 0, 0);
    }

    private void initPopOther(int i) {
        et_year.setText(mDiaries.get(i).getYear() + "");
        et_date.setText(mDiaries.get(i).getDate() + "");
        et_context.setText(mDiaries.get(i).getContent() + "");
        if (mDiaries.get(i).getBgtype() == 1) {
            rb_bg_1.setChecked(true);
            ll_bg.setBackgroundResource(R.drawable.diary_bg_1);
        }
        if (mDiaries.get(i).getBgtype() == 2) {
            rb_bg_2.setChecked(true);
            ll_bg.setBackgroundResource(R.drawable.diary_bg_2);
        }
        if (mDiaries.get(i).getBgtype() == 3) {
            rb_bg_3.setChecked(true);
            ll_bg.setBackgroundResource(R.drawable.diary_bg_3);
        }
        if (mDiaries.get(i).getWeather() == 1)
            rb_cloudy.setChecked(true);
        if (mDiaries.get(i).getWeather() == 2)
            rb_fine.setChecked(true);
        if (mDiaries.get(i).getWeather() == 3)
            rb_rain.setChecked(true);
        if (mDiaries.get(i).getWeather() == 4)
            rb_snow.setChecked(true);
    }

    private void initPopBg(int i) {
        if (mDiaries.get(i).getImg() != null) {
            File img = new File(context.getCacheDir() + "/bmob/" +
                    mDiaries.get(i).getImg().getFilename());
            if (img.exists()) {
                Drawable dimg = Drawable.createFromPath(img.getPath());
                iv_img.setBackground(dimg);
            } else {
                mDiaries.get(i).getImg().download(context, new DownloadFileListener() {
                    @Override
                    public void onSuccess(String s) {
                        Drawable dimg = Drawable.createFromPath(s);
                        iv_img.setBackground(dimg);
                    }

                    @Override
                    public void onFailure(int i, String s) {
                        MicroTools.toast(context, "网络错误");
                    }
                });
            }
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ib_close:
                mDiaryPop.dismiss();
                break;
        }
    }

    private void initPopWindows() {
        popView = View.inflate(context, R.layout.pop_mdiary_look, null);
        ib_close = (ImageButton) popView.findViewById(R.id.ib_close);
        ib_close.setOnClickListener(this);
        ll_bg = (LinearLayout) popView.findViewById(R.id.ll_bg);
        rb_bg_1 = (RadioButton) popView.findViewById(R.id.rb_bg_1);
        rb_bg_2 = (RadioButton) popView.findViewById(R.id.rb_bg_2);
        rb_bg_3 = (RadioButton) popView.findViewById(R.id.rb_bg_3);

        rb_snow = (RadioButton) popView.findViewById(R.id.rb_snow);
        rb_cloudy = (RadioButton) popView.findViewById(R.id.rb_cloudy);
        rb_fine = (RadioButton) popView.findViewById(R.id.rb_fine);
        rb_rain = (RadioButton) popView.findViewById(R.id.rb_rain);

        et_year = (EditText) popView.findViewById(R.id.et_year);
        et_date = (EditText) popView.findViewById(R.id.et_date);
        et_context = (EditText) popView.findViewById(R.id.et_context);

        iv_img = (ImageView) popView.findViewById(R.id.iv_img);

        mDiaryPop = new PopupWindow(popView,
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        mDiaryPop.setBackgroundDrawable(new BitmapDrawable());
        mDiaryPop.setOutsideTouchable(true);
    }

    class ViewHolder {
        public ImageView iv_img;
        public ImageButton ib_del;
        public TextView tv_date;
    }
}
