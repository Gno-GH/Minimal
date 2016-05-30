package minimal.microfriend.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ViewDragHelper;
import android.support.v4.widget.ViewDragHelper.Callback;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

@SuppressLint("ClickableViewAccessibility")
public class MinimalLayout extends FrameLayout {
	private ViewDragHelper dragHelper;
	private View mainLayout;
	private View leftLayout;
	private int mheight;
	private int mwidth;
	private int mleft;
	private int mleftdx;
	private OnDragListener dragListener;
	private Status status = Status.Close;

	public static enum Status {
		Open, Close, Draging;
	}

	public interface OnDragListener {
		public void onClose();

		public void onOpen();

		public void onDraging(float scale);
	}

	public void setOnDragListener(OnDragListener dragListener) {
		this.dragListener = dragListener;
	}

	public MinimalLayout(Context context) {
		super(context);
		init();
	}

	public MinimalLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public MinimalLayout(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	private void init() {
		dragHelper = ViewDragHelper.create(this, callback);
	}

	Callback callback = new Callback() {

		@Override
		public boolean tryCaptureView(View arg0, int arg1) {
			return true;
		}

		@Override
		public int getViewHorizontalDragRange(View child) {
			return mleft;
		}

		@Override
		public int clampViewPositionHorizontal(View child, int left, int dx) {
			if (child == mainLayout) {
				left = fixleft(left);
			}
			return left;
		}

		/**
		 * 修正左边位置
		 * 
		 * @param left
		 * @return
		 */
		private int fixleft(int left) {
			if (left > mleft)
				return mleft;
			if (left < 0) {
				return 0;
			}
			return left;
		}

		@Override
		public void onViewPositionChanged(View changedView, int left, int top,
				int dx, int dy) {
			super.onViewPositionChanged(changedView, left, top, dx, dy);
			mleftdx = fixleft(mainLayout.getLeft() + dx);// 修正拖拽位置
			float scale = Math.abs(mleftdx / (float) mleft);// 获取拖拽比例
			if (changedView == leftLayout) {
				leftLayout.layout(0, 0, mwidth, mheight);
				mainLayout.layout(mleftdx, 0, mleftdx + mwidth, mheight);
			}
			fllowAnimation(scale);
			if (dragListener != null && mainLayout.getLeft() > 0
					&& mainLayout.getLeft() < mleft) {
				dragListener.onDraging(scale);
				status = Status.Draging;
			}
			invalidate();// 兼容低版本
		}

		@Override
		public void onViewReleased(View releasedChild, float xvel, float yvel) {
			super.onViewReleased(releasedChild, xvel, yvel);
			if (xvel > 0 || (xvel == 0 && mleftdx > mleft / 3)) {
				isOpen(mleft, true);
				if (status != Status.Open&&dragListener!=null) {
					dragListener.onOpen();
					status = Status.Open;
				}
			} else {
				isOpen(0, true);
				if (status != Status.Close&&dragListener!=null) {
					dragListener.onClose();
					status = Status.Close;
				}
			}
			invalidate();
		}

		/**
		 * 
		 * @param left
		 *            欲滑动到的目的位置
		 * @param isSoomth
		 *            是否为平滑滑动
		 */
		private void isOpen(int left, boolean isSoomth) {
			if (isSoomth == true)
				if (dragHelper.smoothSlideViewTo(mainLayout, left, 0)) {
					ViewCompat.postInvalidateOnAnimation(MinimalLayout.this);
				} else {
					mainLayout.layout(left, 0, left + mwidth, mheight);
				}
		}

		@Override
		public void onViewDragStateChanged(int state) {
			super.onViewDragStateChanged(state);
		}

		@Override
		public void onViewCaptured(View capturedChild, int activePointerId) {
			super.onViewCaptured(capturedChild, activePointerId);
		}
	};

	public void computeScroll() {
		super.computeScroll();
		if (dragHelper.continueSettling(true)) {
			ViewCompat.postInvalidateOnAnimation(this);
		}
	}

	protected void fllowAnimation(float scale) {
		mainLayout.setAlpha(1 - scale / 9);
		mainLayout.setScaleY(1 - scale / 4.5f);
		mainLayout.setScaleX(1 - scale / 4);
		leftLayout.setAlpha(scale + 0.1f);
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		return false;
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		try {
			dragHelper.processTouchEvent(event);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}

	protected void onFinishInflate() {
		if (this.getChildCount() < 2)
			throw new IllegalStateException(
					"The number of children is less than two");
		if (!(getChildAt(0) instanceof ViewGroup && getChildAt(1) instanceof ViewGroup))
			throw new IllegalAccessError("Child type is not Viewgroup");
		leftLayout = getChildAt(0);
		mainLayout = getChildAt(1);
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		mwidth = mainLayout.getMeasuredWidth();
		mheight = mainLayout.getMeasuredHeight();
		mleft = (int) (mwidth * 0.6f);
	}
}
