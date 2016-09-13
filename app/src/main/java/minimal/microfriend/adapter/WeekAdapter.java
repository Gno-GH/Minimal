package minimal.microfriend.adapter;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.TextView;

import com.google.gson.Gson;

import java.io.File;

import minimal.microfriend.R;
import minimal.microfriend.entry.OwnerClass;
import minimal.microfriend.utils.MicroTools;

/**
 * 课表适配器
 * Created by gno on 16/8/3.
 */
public class WeekAdapter extends BaseAdapter implements View.OnClickListener {
    //TODO: 课程表数据适配器待完成
    private Context mContext;
    private OwnerClass mWeeks;
    private View popView;
    private PopupWindow mPartPop;
    private LinearLayout ll_parent;
    private ImageView iv_pop_close;
    private EditText et_part_name, et_part_site, et_part_number,
            et_part_start, et_part_end, et_part_teacher;
    private Button b_pop_save, b_pop_edit, b_pop_clear;
    private RadioButton rb_one, rb_all, rb_two;
    private int weekIndex, partIndex;

    public WeekAdapter(Context context, OwnerClass weeks, LinearLayout ll_parent) {
        this.mContext = context;
        this.mWeeks = weeks;
        this.ll_parent = ll_parent;
    }

    @Override
    public int getCount() {
        return mWeeks.weekclass.size();
    }

    @Override
    public Object getItem(int i) {
        return mWeeks.weekclass.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHodler hodler;
        if (view == null) {
            view = View.inflate(mContext, R.layout.listview_class, null);
            hodler = new ViewHodler();
            findId(hodler, view);
            initPop();
            view.setTag(hodler);
        } else
            hodler = (ViewHodler) view.getTag();
        setOnclick(hodler, i);
        hodler.tv_date.setText(mWeeks.weekclass.get(i).date);
        setAllText(i, hodler);
        return view;
    }

    private void initPop() {
        popView = View.inflate(mContext, R.layout.pop_class_part, null);
        mPartPop = new PopupWindow(popView,
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        mPartPop.setAnimationStyle(R.style.pop_trend_anim);
        mPartPop.setBackgroundDrawable(new BitmapDrawable());
        mPartPop.setOutsideTouchable(true);
        findPopId();
        mPartPop.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                enAbled(false);
            }
        });
    }

    private void enAbled(boolean bool) {
        et_part_name.setEnabled(bool);
        et_part_site.setEnabled(bool);
        et_part_number.setEnabled(bool);
        et_part_start.setEnabled(bool);
        et_part_end.setEnabled(bool);
        et_part_teacher.setEnabled(bool);
    }

    private void findPopId() {
        iv_pop_close = (ImageView) popView.findViewById(R.id.iv_pop_close);
        iv_pop_close.setOnClickListener(this);
        et_part_name = (EditText) popView.findViewById(R.id.et_part_name);
        et_part_site = (EditText) popView.findViewById(R.id.et_part_site);
        et_part_number = (EditText) popView.findViewById(R.id.et_part_number);
        et_part_start = (EditText) popView.findViewById(R.id.et_part_start);
        et_part_end = (EditText) popView.findViewById(R.id.et_part_end);
        et_part_teacher = (EditText) popView.findViewById(R.id.et_part_teacher);

        b_pop_save = (Button) popView.findViewById(R.id.b_pop_save);
        b_pop_edit = (Button) popView.findViewById(R.id.b_pop_edit);
        b_pop_clear = (Button) popView.findViewById(R.id.b_pop_clear);
        b_pop_clear.setOnClickListener(this);
        b_pop_edit.setOnClickListener(this);
        b_pop_save.setOnClickListener(this);

        rb_one = (RadioButton) popView.findViewById(R.id.rb_one);
        rb_all = (RadioButton) popView.findViewById(R.id.rb_all);
        rb_two = (RadioButton) popView.findViewById(R.id.rb_two);
    }

    private void setOnclick(ViewHodler hodler, final int i) {
        hodler.ll_part_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popShow(i, 0);
            }
        });
        hodler.ll_part_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popShow(i, 1);
            }
        });
        hodler.ll_part_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popShow(i, 2);
            }
        });
        hodler.ll_part_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popShow(i, 3);
            }
        });
    }

    /**
     * 显示popwindow
     *
     * @param i 星期
     * @param p 节次
     */
    private void popShow(int i, int p) {
        weekIndex = i;
        partIndex = p;
        mPartPop.showAtLocation(ll_parent, Gravity.CENTER, 0, 0);
        et_part_name.setText(mWeeks.weekclass.get(i).dayclass.get(p).classname);
        et_part_site.setText(mWeeks.weekclass.get(i).dayclass.get(p).classsite);
        et_part_number.setText(mWeeks.weekclass.get(i).dayclass.get(p).classnumber);
        et_part_start.setText(mWeeks.weekclass.get(i).dayclass.get(p).classstart);
        et_part_end.setText(mWeeks.weekclass.get(i).dayclass.get(p).classend);
        et_part_teacher.setText(mWeeks.weekclass.get(i).dayclass.get(p).classteacher);
        if (mWeeks.weekclass.get(i).dayclass.get(p).classoe != null) {
            if (mWeeks.weekclass.get(i).dayclass.get(p).classoe.equals("全周"))
                rb_all.setChecked(true);
            if (mWeeks.weekclass.get(i).dayclass.get(p).classoe.equals("单周"))
                rb_one.setChecked(true);
            if (mWeeks.weekclass.get(i).dayclass.get(p).classoe.equals("双周"))
                rb_two.setChecked(true);
        }
    }

    private void setAllText(int i, ViewHodler hodler) {
        if (mWeeks.weekclass.get(i).dayclass.get(0) != null) {
            hodler.tv_class_name_1.setText(mWeeks.weekclass.get(i).dayclass.get(0).classname);
            hodler.tv_class_site_1.setText(mWeeks.weekclass.get(i).dayclass.get(0).classsite);
            hodler.tv_class_oe_1.setText(mWeeks.weekclass.get(i).dayclass.get(0).classoe);
            hodler.tv_class_number_1.setText(mWeeks.weekclass.get(i).dayclass.get(0).classnumber);
            hodler.tv_class_start_1.setText(mWeeks.weekclass.get(i).dayclass.get(0).classstart);
            hodler.tv_class_end_1.setText(mWeeks.weekclass.get(i).dayclass.get(0).classend);
            hodler.tv_class_teacher_1.setText(mWeeks.weekclass.get(i).dayclass.get(0).classteacher);
        }
        if (mWeeks.weekclass.get(i).dayclass.get(1) != null) {
            hodler.tv_class_name_2.setText(mWeeks.weekclass.get(i).dayclass.get(1).classname);
            hodler.tv_class_site_2.setText(mWeeks.weekclass.get(i).dayclass.get(1).classsite);
            hodler.tv_class_oe_2.setText(mWeeks.weekclass.get(i).dayclass.get(1).classoe);
            hodler.tv_class_number_2.setText(mWeeks.weekclass.get(i).dayclass.get(1).classnumber);
            hodler.tv_class_start_2.setText(mWeeks.weekclass.get(i).dayclass.get(1).classstart);
            hodler.tv_class_end_2.setText(mWeeks.weekclass.get(i).dayclass.get(1).classend);
            hodler.tv_class_teacher_2.setText(mWeeks.weekclass.get(i).dayclass.get(1).classteacher);
        }
        if (mWeeks.weekclass.get(i).dayclass.get(2) != null) {
            hodler.tv_class_name_3.setText(mWeeks.weekclass.get(i).dayclass.get(2).classname);
            hodler.tv_class_site_3.setText(mWeeks.weekclass.get(i).dayclass.get(2).classsite);
            hodler.tv_class_oe_3.setText(mWeeks.weekclass.get(i).dayclass.get(2).classoe);
            hodler.tv_class_number_3.setText(mWeeks.weekclass.get(i).dayclass.get(2).classnumber);
            hodler.tv_class_start_3.setText(mWeeks.weekclass.get(i).dayclass.get(2).classstart);
            hodler.tv_class_end_3.setText(mWeeks.weekclass.get(i).dayclass.get(2).classend);
            hodler.tv_class_teacher_3.setText(mWeeks.weekclass.get(i).dayclass.get(2).classteacher);
        }
        if (mWeeks.weekclass.get(i).dayclass.get(3) != null) {
            hodler.tv_class_name_4.setText(mWeeks.weekclass.get(i).dayclass.get(3).classname);
            hodler.tv_class_site_4.setText(mWeeks.weekclass.get(i).dayclass.get(3).classsite);
            hodler.tv_class_oe_4.setText(mWeeks.weekclass.get(i).dayclass.get(3).classoe);
            hodler.tv_class_number_4.setText(mWeeks.weekclass.get(i).dayclass.get(3).classnumber);
            hodler.tv_class_start_4.setText(mWeeks.weekclass.get(i).dayclass.get(3).classstart);
            hodler.tv_class_end_4.setText(mWeeks.weekclass.get(i).dayclass.get(3).classend);
            hodler.tv_class_teacher_4.setText(mWeeks.weekclass.get(i).dayclass.get(3).classteacher);
        }
    }

    private void findId(ViewHodler hodler, View view) {
        hodler.tv_date = (TextView) view.findViewById(R.id.tv_date);
        //上午第一大节
        hodler.tv_class_name_1 = (TextView) view.findViewById(R.id.tv_class_name_1);
        hodler.tv_class_site_1 = (TextView) view.findViewById(R.id.tv_class_site_1);
        hodler.tv_class_oe_1 = (TextView) view.findViewById(R.id.tv_class_oe_1);
        hodler.tv_class_number_1 = (TextView) view.findViewById(R.id.tv_class_number_1);
        hodler.tv_class_start_1 = (TextView) view.findViewById(R.id.tv_class_start_1);
        hodler.tv_class_end_1 = (TextView) view.findViewById(R.id.tv_class_end_1);
        hodler.tv_class_teacher_1 = (TextView) view.findViewById(R.id.tv_class_teacher_1);
        //上午第二大节
        hodler.tv_class_name_2 = (TextView) view.findViewById(R.id.tv_class_name_2);
        hodler.tv_class_site_2 = (TextView) view.findViewById(R.id.tv_class_site_2);
        hodler.tv_class_oe_2 = (TextView) view.findViewById(R.id.tv_class_oe_2);
        hodler.tv_class_number_2 = (TextView) view.findViewById(R.id.tv_class_number_2);
        hodler.tv_class_start_2 = (TextView) view.findViewById(R.id.tv_class_start_2);
        hodler.tv_class_end_2 = (TextView) view.findViewById(R.id.tv_class_end_2);
        hodler.tv_class_teacher_2 = (TextView) view.findViewById(R.id.tv_class_teacher_2);
        //上午第三大节
        hodler.tv_class_name_3 = (TextView) view.findViewById(R.id.tv_class_name_3);
        hodler.tv_class_site_3 = (TextView) view.findViewById(R.id.tv_class_site_3);
        hodler.tv_class_oe_3 = (TextView) view.findViewById(R.id.tv_class_oe_3);
        hodler.tv_class_number_3 = (TextView) view.findViewById(R.id.tv_class_number_3);
        hodler.tv_class_start_3 = (TextView) view.findViewById(R.id.tv_class_start_3);
        hodler.tv_class_end_3 = (TextView) view.findViewById(R.id.tv_class_end_3);
        hodler.tv_class_teacher_3 = (TextView) view.findViewById(R.id.tv_class_teacher_3);
        //上午第四大节
        hodler.tv_class_name_4 = (TextView) view.findViewById(R.id.tv_class_name_4);
        hodler.tv_class_site_4 = (TextView) view.findViewById(R.id.tv_class_site_4);
        hodler.tv_class_oe_4 = (TextView) view.findViewById(R.id.tv_class_oe_4);
        hodler.tv_class_number_4 = (TextView) view.findViewById(R.id.tv_class_number_4);
        hodler.tv_class_start_4 = (TextView) view.findViewById(R.id.tv_class_start_4);
        hodler.tv_class_end_4 = (TextView) view.findViewById(R.id.tv_class_end_4);
        hodler.tv_class_teacher_4 = (TextView) view.findViewById(R.id.tv_class_teacher_4);
        hodler.ll_part_1 = (LinearLayout) view.findViewById(R.id.ll_part_1);
        hodler.ll_part_2 = (LinearLayout) view.findViewById(R.id.ll_part_2);
        hodler.ll_part_3 = (LinearLayout) view.findViewById(R.id.ll_part_3);
        hodler.ll_part_4 = (LinearLayout) view.findViewById(R.id.ll_part_4);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_pop_close:
                mPartPop.dismiss();
                break;
            case R.id.b_pop_edit:
                enAbled(true);
                break;
            case R.id.b_pop_save:
                calssSave();
                classAsJson();
                break;
            case R.id.b_pop_clear:
                popClear();
                break;
        }
    }

    private void calssSave() {
        mWeeks.weekclass.get(weekIndex).dayclass.get(partIndex).classname
                = et_part_name.getText().toString().trim();
        mWeeks.weekclass.get(weekIndex).dayclass.get(partIndex).classsite
                = et_part_site.getText().toString().trim();
        mWeeks.weekclass.get(weekIndex).dayclass.get(partIndex).classstart
                = et_part_start.getText().toString().trim();
        mWeeks.weekclass.get(weekIndex).dayclass.get(partIndex).classend
                = et_part_end.getText().toString().trim();
        mWeeks.weekclass.get(weekIndex).dayclass.get(partIndex).classnumber
                = et_part_number.getText().toString().trim();
        mWeeks.weekclass.get(weekIndex).dayclass.get(partIndex).classteacher
                = et_part_teacher.getText().toString().trim();
        if (et_part_teacher.getText().toString().trim() != null) {
            if (rb_one.isChecked())
                mWeeks.weekclass.get(weekIndex).dayclass.get(partIndex).classoe = "单周";
            else if (rb_two.isChecked())
                mWeeks.weekclass.get(weekIndex).dayclass.get(partIndex).classoe = "双周";
            else
                mWeeks.weekclass.get(weekIndex).dayclass.get(partIndex).classoe = "全周";
        } else
            mWeeks.weekclass.get(weekIndex).dayclass.get(partIndex).classoe = "";
    }

    private void classAsJson() {
        Gson gson = new Gson();
        MicroTools.WriteStringToFile(new
                        File(mContext.getCacheDir() + "/bmob/ownerClass.json"),
                gson.toJson(mWeeks));
        notifyDataSetChanged();
        mPartPop.dismiss();
    }

    private void popClear() {
        et_part_name.setText("");
        et_part_site.setText("");
        et_part_number.setText("");
        et_part_start.setText("");
        et_part_end.setText("");
        et_part_teacher.setText("");
        rb_all.setChecked(false);
        rb_one.setChecked(false);
        rb_two.setChecked(false);
    }

    class ViewHodler {
        public TextView tv_date;
        public TextView tv_class_name_1, tv_class_site_1, tv_class_oe_1, tv_class_number_1, tv_class_start_1,
                tv_class_end_1, tv_class_teacher_1;
        public TextView tv_class_name_2, tv_class_site_2, tv_class_oe_2, tv_class_number_2, tv_class_start_2,
                tv_class_end_2, tv_class_teacher_2;
        public TextView tv_class_name_3, tv_class_site_3, tv_class_oe_3, tv_class_number_3, tv_class_start_3,
                tv_class_end_3, tv_class_teacher_3;
        public TextView tv_class_name_4, tv_class_site_4, tv_class_oe_4, tv_class_number_4, tv_class_start_4,
                tv_class_end_4, tv_class_teacher_4;
        public LinearLayout ll_part_1, ll_part_2, ll_part_3, ll_part_4;
    }
}
