package minimal.microfriend.base;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import minimal.microfriend.R;
import minimal.microfriend.entry.User;

/**
 * Created by gno on 16-5-28.
 */
public class BaseTabPager{
    public Context context;
    public LinearLayout linearLayout;
    public LinearLayout ll_root;
    private View rootView;
    public TextView title_tv;
    public User user;
    public boolean isaddview = true;

    public BaseTabPager(Context context) {
        this.context = context;
        rootView = initView();
    }

    private View initView() {
        View view = View.inflate(this.context, R.layout.tab_basepager, null);
        linearLayout = (LinearLayout) view.findViewById(R.id.tab_base_ll);
        ll_root = (LinearLayout) view.findViewById(R.id.ll_root);
        title_tv = (TextView) view.findViewById(R.id.title_tv);
        return view;
    }

    public View getRootView() {
        return rootView;
    }

    public void initData() {}
}
