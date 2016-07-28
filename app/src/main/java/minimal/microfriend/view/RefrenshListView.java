package minimal.microfriend.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import minimal.microfriend.R;

/**
 * Created by gno on 16/7/18.
 */
public class RefrenshListView extends ListView implements AbsListView.OnScrollListener {
    private View mHeadView;
    private int mHeight;//头布局高度
    private int startY = -1;
    private static final int STATE_PULL_TO_REFRENSH = 1;//下拉刷新
    private static final int STATE_RELEASE_TO_REFRENSH = 2;//释放刷新
    private static final int STATE_REFRENSHING = 3;//正在刷新
    private int mCurrentState = STATE_PULL_TO_REFRENSH;
    private TextView tv_rf;
    private ImageView iv_drag;
    private ProgressBar pb_refrensh;
    private RotateAnimation animUp;//向上动画
    private RotateAnimation animDown;//向下动画
    private OnRefrenshListener mListener;
    private View mFootview;
    private int mFootHeight;//脚布局高度
    private boolean isLoadingMore = false;
    private boolean userFootView = true;

    public void setUserFootView(boolean userFootView) {
        this.userFootView = userFootView;
    }

    public RefrenshListView(Context context) {
        super(context);
        initHeadView();
        initFootView();
    }

    public RefrenshListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initHeadView();
        initFootView();
    }

    public RefrenshListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initHeadView();
        initFootView();
    }

    /**
     * 初始化头部刷新布局
     */
    private void initHeadView() {
        mHeadView = View.inflate(getContext(), R.layout.listview_refrensh_head, null);
        mHeadView.measure(0, 0);
        mHeight = mHeadView.getMeasuredHeight();
        mHeadView.setPadding(0, -mHeight, 0, 0);
        this.addHeaderView(mHeadView);
        tv_rf = (TextView) mHeadView.findViewById(R.id.tv_rf);
        iv_drag = (ImageView) mHeadView.findViewById(R.id.iv_drag);
        pb_refrensh = (ProgressBar) mHeadView.findViewById(R.id.pb_refrensh);
        initAnimation();
    }

    /**
     * 初始化脚布局
     */
    private void initFootView() {
        mFootview = View.inflate(getContext(), R.layout.listview_refrensh_foot, null);
        mFootview.measure(0, 0);
        mFootHeight = mFootview.getMeasuredHeight();
        mFootview.setPadding(0, -mFootHeight, 0, 0);
        this.addFooterView(mFootview);
        this.setOnScrollListener(this);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startY = (int) ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                if (startY == -1)
                    startY = (int) ev.getY();
                if (mCurrentState == STATE_REFRENSHING)
                    break;//正在刷新时滑动无效果
                int endY = (int) ev.getY();
                int dY = endY - startY;
                if (dY > 0 && getFirstVisiblePosition() == 0) {
                    int paddingTop = dY - mHeight;
                    //状态切换
                    if (paddingTop > 0 && mCurrentState != STATE_RELEASE_TO_REFRENSH) {
                        mCurrentState = STATE_RELEASE_TO_REFRENSH;
                        refrenshState();
                    } else if (paddingTop < 0 && mCurrentState != STATE_PULL_TO_REFRENSH) {
                        mCurrentState = STATE_PULL_TO_REFRENSH;
                        refrenshState();
                    }
                    mHeadView.setPadding(0, paddingTop, 0, 0);
                    return true;
                }
                break;
            case MotionEvent.ACTION_UP:
                startY = -1;
                //释放刷新切换到正在刷新
                if (mCurrentState == STATE_RELEASE_TO_REFRENSH) {
                    //完全显示
                    mHeadView.setPadding(0, 0, 0, 0);
                    mCurrentState = STATE_REFRENSHING;
                    refrenshState();
                    if (mListener != null) {
                        mListener.onReFrensh();
                    }
                } else if (mCurrentState == STATE_PULL_TO_REFRENSH) {
                    //完全隐藏
                    mHeadView.setPadding(0, -mHeight, 0, 0);
                }
                break;

        }
        return super.onTouchEvent(ev);
    }

    /**
     * 初始化动画
     */
    private void initAnimation() {
        animUp = new RotateAnimation(0, -180,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        animUp.setDuration(500);
        animUp.setFillAfter(true);//保持状态
        animDown = new RotateAnimation(-180, 0,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        animDown.setDuration(500);
        animDown.setFillAfter(true);//保持状态
    }

    /**
     * 根据状态刷新界面
     */
    private void refrenshState() {
        switch (mCurrentState) {
            case STATE_PULL_TO_REFRENSH:
                iv_drag.setVisibility(View.VISIBLE);
                tv_rf.setText("下拉刷新");
                iv_drag.startAnimation(animDown);
                pb_refrensh.setVisibility(View.INVISIBLE);
                break;
            case STATE_RELEASE_TO_REFRENSH:
                iv_drag.setVisibility(View.VISIBLE);
                tv_rf.setText("释放刷新");
                iv_drag.startAnimation(animUp);
                pb_refrensh.setVisibility(View.INVISIBLE);
                break;
            case STATE_REFRENSHING:
                pb_refrensh.setVisibility(View.VISIBLE);
                tv_rf.setText("正在刷新");
                Log.d("ABC","正在刷新");
                iv_drag.clearAnimation();//清除动画
                iv_drag.setVisibility(View.INVISIBLE);
                break;
        }
    }

    /**
     * 刷新完成
     */
    public void onRefrenshComplete() {
        mHeadView.setPadding(0, -mHeight, 0, 0);
        mCurrentState = STATE_PULL_TO_REFRENSH;
        pb_refrensh.setVisibility(View.INVISIBLE);
        iv_drag.setVisibility(View.VISIBLE);
        if (isLoadingMore&&userFootView) {
            mFootview.setPadding(0, -mFootHeight, 0, 0);
            isLoadingMore = false;
        }
    }

    public void setOnRefrenshListener(OnRefrenshListener listener) {
        mListener = listener;
    }

    @Override
    public void onScrollStateChanged(AbsListView absListView, int i) {
        //滚动条空闲
        if (i == SCROLL_STATE_IDLE && !isLoadingMore&&userFootView) {
            isLoadingMore = true;
            int lastPos = getLastVisiblePosition();
            if (lastPos >= getCount() - 1) {
                mFootview.setPadding(0, 0, 0, 0);
                setSelection(getCount() - 1);
                if (mListener != null) {
                    mListener.loadMore();
                }
            }
        }
    }

    @Override
    public void onScroll(AbsListView absListView, int i, int i1, int i2) {

    }

    /**
     * 下拉刷新回调接口
     */
    public interface OnRefrenshListener {
        public void onReFrensh();//正在刷新

        public void loadMore();//加载更多
    }
//
//    @Override
//    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
//                MeasureSpec.AT_MOST);
//        super.onMeasure(widthMeasureSpec, expandSpec);
//    }
}
