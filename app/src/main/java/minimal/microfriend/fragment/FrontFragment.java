package minimal.microfriend.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.io.File;
import java.util.ArrayList;

import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UploadFileListener;
import minimal.microfriend.R;
import minimal.microfriend.adapter.ContextPagerAdapter;
import minimal.microfriend.base.BaseFragment;
import minimal.microfriend.base.BaseTabPager;
import minimal.microfriend.entry.Trend;
import minimal.microfriend.entry.User;
import minimal.microfriend.pager.CenterPager;
import minimal.microfriend.pager.MessagePager;
import minimal.microfriend.pager.MicroPager;
import minimal.microfriend.utils.MicroTools;
import minimal.microfriend.view.DragLinearLayout;
import minimal.microfriend.view.MinimalLayout;
import minimal.microfriend.view.NoScrollViewPager;

@SuppressLint("ValidFragment")
public class FrontFragment extends BaseFragment implements RadioGroup.OnCheckedChangeListener, View.OnClickListener, View.OnLongClickListener {
    private NoScrollViewPager front_vp;
    private ArrayList<BaseTabPager> pagers;
    private RadioGroup front_rg;
    private MinimalLayout minimalLayout;
    private DragLinearLayout dragLinearLayout;
    private RadioButton radio_center;
    private PopupWindow popTrendIss;
    private View popview;
    private Button b_trend_close, b_trend_ok;
    private EditText et_trend_content;
    private ImageView iv_trend_img;
    private Trend mTrend;
    private CenterPager centerPager;
    private boolean isTrendUpIng = false;

    public FrontFragment(MinimalLayout minimalLayout) {
        super();
        this.minimalLayout = minimalLayout;
    }

    @Override
    public View iniView(LayoutInflater inflater) {
        View view = inflater.inflate(R.layout.fragment_front, null);
        front_vp = (NoScrollViewPager) view.findViewById(R.id.front_vp);
        front_rg = (RadioGroup) view.findViewById(R.id.front_rg);
        dragLinearLayout = (DragLinearLayout) view.findViewById(R.id.dl_layout);
        dragLinearLayout.setDragLayout(minimalLayout);
        radio_center = (RadioButton) view.findViewById(R.id.radio_center);
        radio_center.setOnLongClickListener(this);
        return view;
    }

    @Override
    public void initData() {
        centerPager = new CenterPager(this.activity, user);
        pagers = new ArrayList<BaseTabPager>();
        pagers.add(new MessagePager(this.activity, user));
        pagers.add(centerPager);
        pagers.add(new MicroPager(this.activity, user));
        pagers.get(0).initData();
        front_vp.setAdapter(new ContextPagerAdapter(pagers));
        front_rg.setOnCheckedChangeListener(this);
        iniPopWindow();
    }

    @Override
    public User initUser() {
        if (getArguments() != null) {
            return (User) getArguments().getSerializable("user");
        } else
            return new User();
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        int index = 0;
        switch (checkedId) {
            case R.id.radio_mes:
                index = 0;
                break;
            case R.id.radio_center:
                index = 1;
                break;
            case R.id.radio_micro:
                index = 2;
                break;
        }
        front_vp.setCurrentItem(index, false);
        pagers.get(index).initData();
    }

    private void iniPopWindow() {
        popview = View.inflate(activity, R.layout.pop_trend, null);
        popTrendIss = new PopupWindow(popview,
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        popTrendIss.setAnimationStyle(R.style.pop_trend_anim);
        popTrendIss.setBackgroundDrawable(new BitmapDrawable());
        popTrendIss.setOutsideTouchable(true);
        b_trend_close = (Button) popview.findViewById(R.id.b_trend_close);
        b_trend_ok = (Button) popview.findViewById(R.id.b_trend_ok);
        iv_trend_img = (ImageView) popview.findViewById(R.id.iv_trend_img);
        et_trend_content = (EditText) popview.findViewById(R.id.et_trend_content);
        b_trend_ok.setOnClickListener(this);
        b_trend_close.setOnClickListener(this);
        iv_trend_img.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.b_trend_close:
                popClose();
                break;
            case R.id.b_trend_ok:
                popOk();
                break;
            case R.id.iv_trend_img:
                selectImg();
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data != null) {
            iv_trend_img.setImageURI(data.getData());
            File img = MicroTools.uriToFile(data.getData(), (Activity) activity, user);
            BmobFile bimg = new BmobFile(img);
            mTrend.setContentImg(bimg);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void selectImg() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, 0);
    }

    private void popOk() {
        if(!isTrendUpIng) {
            isTrendUpIng = true;
            mTrend.setContentText(et_trend_content.getText().toString().trim());
            if (mTrend.getContentImg() != null) {
                mTrend.getContentImg().upload(activity, new UploadFileListener() {
                    @Override
                    public void onSuccess() {
                        upTrend();
                    }

                    @Override
                    public void onFailure(int i, String s) {
                        MicroTools.toast(activity, "网络似乎在开小差");
                    }
                });
            } else
                upTrend();
        }
    }

    private void upTrend() {
        mTrend.save(activity, new SaveListener() {
            @Override
            public void onSuccess() {
                centerPager.trendAdd(mTrend);
                isTrendUpIng = false;
                popClose();
            }

            @Override
            public void onFailure(int i, String s) {
                MicroTools.toast(activity, "网络似乎在开小差");
                isTrendUpIng = false;
                popClose();
            }
        });
    }

    private void popClose() {
        iv_trend_img.setImageURI(null);
        et_trend_content.setText("");
        popTrendIss.dismiss();
    }

    @Override
    public boolean onLongClick(View view) {
        switch (view.getId()) {
            case R.id.radio_center:
                if (radio_center.isChecked()) {
                    mTrend = new Trend();
                    mTrend.setDislike(0);
                    mTrend.setLike(0);
                    mTrend.setCreateUser(user);
                    popTrendIss.showAtLocation(front_vp, Gravity.BOTTOM, 0, 0);
                }
                break;
        }
        return true;
    }
}
