package minimal.microfriend.view;


import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.LinearLayout;


public class DragLinearLayout extends LinearLayout {

    private MinimalLayout minimalLayout;

    public DragLinearLayout(Context context) {
        super(context);
    }

    public DragLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DragLinearLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }


    public void setDragLayout(MinimalLayout minimalLayout) {
        this.minimalLayout = minimalLayout;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        if (minimalLayout != null) {
            if (minimalLayout.getStatus() != MinimalLayout.Status.Close) {
                return true;
            }
        }
        return super.onInterceptTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (minimalLayout != null) {
            Log.d("Dargtest","minimalLayout不为空");
            if (minimalLayout.getStatus() != MinimalLayout.Status.Close) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    minimalLayout.derail();
                }
                return true;
            }
        }
        return super.onTouchEvent(event);
    }
}
