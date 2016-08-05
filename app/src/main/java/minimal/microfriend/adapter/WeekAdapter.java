package minimal.microfriend.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import minimal.microfriend.R;

/**
 * Created by gno on 16/8/3.
 */
public class WeekAdapter extends BaseAdapter {
    //TODO: 课程表数据适配器待完成
    private Context mContext;
    private String[] mWeeks;

    public WeekAdapter(Context context, String[] weeks) {
        this.mContext = context;
        this.mWeeks = weeks;
    }

    @Override
    public int getCount() {
        return mWeeks.length;
    }

    @Override
    public Object getItem(int i) {
        return mWeeks[i];
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
            findId(hodler,view);
            view.setTag(hodler);
        }else hodler = (ViewHodler) view.getTag();
        hodler.tv_date.setText(mWeeks[i])   ;
        return view;
    }

    private void findId(ViewHodler hodler,View view) {
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
    }
}
