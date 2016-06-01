package minimal.microfriend.base;

import android.content.Context;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import minimal.microfriend.R;

/**
 * Created by gno on 16-5-28.
 */
public class BaseTabPager {
    public Context context;
    public LinearLayout linearLayout;
    private View rootView;
    public boolean isaddview = true;

    public BaseTabPager(Context context) {
        this.context = context;
        rootView = initView();
    }

    private View initView() {
        View view = View.inflate(this.context, R.layout.tab_basepager, null);
        linearLayout = (LinearLayout) view.findViewById(R.id.tab_base_ll);
        return view;
    }

    public View getRootView() {
        return rootView;
    }

    public void initData() {

    }
}
