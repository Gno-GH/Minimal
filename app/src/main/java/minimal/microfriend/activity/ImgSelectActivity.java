package minimal.microfriend.activity;

import android.os.Bundle;

import minimal.microfriend.R;
import minimal.microfriend.base.BaseActivity;

/**
 * Created by gno on 16/9/9.
 */
public class ImgSelectActivity extends BaseActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select);
        initView();
        initData();
    }

    @Override
    public void initView() {

    }
}
