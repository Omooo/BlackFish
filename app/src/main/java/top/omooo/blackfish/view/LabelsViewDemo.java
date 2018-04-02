package top.omooo.blackfish.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by SSC on 2018/4/2.
 */

public class LabelsViewDemo extends ViewGroup {

    private static final String TAG = "LabelsViewDemo";

    public LabelsViewDemo(Context context) {
        super(context);
    }

    public LabelsViewDemo(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public LabelsViewDemo(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
//        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
//        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
//        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
//        measureChildren(widthMeasureSpec, heightMeasureSpec);
//        if (getChildCount() == 0) {
//            Log.i(TAG, "onMeasure: 1");
//            setMeasuredDimension(0, 0);
//        } else if (widthMode == MeasureSpec.AT_MOST && heightMode == MeasureSpec.AT_MOST) {
//            Log.i(TAG, "onMeasure: 2");
////            int width = 0;
////            int height = 0;
////            for (int i = 0; i < getChildCount(); i++) {
////                View view = getChildAt(i);
////                width += view.getMeasuredWidth();
////                height += view.getMeasuredHeight();
////            }
//            setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);
//        } else if (widthMode == MeasureSpec.AT_MOST) {
//            Log.i(TAG, "onMeasure: 3");
//            View childOne = getChildAt(0);
//            int childWidth = childOne.getMeasuredWidth();
//            setMeasuredDimension(childWidth, heightSize);
//        } else if (heightMode == MeasureSpec.AT_MOST) {
//            Log.i(TAG, "onMeasure: 4");
////            View childOne = getChildAt(0);
////            int childHeight = childOne.getMeasuredHeight();
//            setMeasuredDimension(widthMeasureSpec,heightMeasureSpec);
//        }
        for (int i = 0; i < getChildCount(); i++) {
            View view = getChildAt(i);
            view.measure(MeasureSpec.UNSPECIFIED, MeasureSpec.UNSPECIFIED);
        }
        measureChildren(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(widthMeasureSpec,heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

        int margin = 20;
        int row = 0;
        int lengthX = l;
        int lengthY = t;
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            int width = child.getMeasuredWidth();
            int height = child.getMeasuredHeight();
            lengthX += width + margin;
            lengthY = row * (height + margin) + margin + height + t;
            if (lengthX > r) {
                lengthX = width + margin + l;
                row++;
                lengthY = row * (height + margin) + margin + height + t;
            }
            child.layout(lengthX - width, lengthY - height, lengthX, lengthY);
        }
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(),attrs);
    }

    @Override
    protected LayoutParams generateDefaultLayoutParams() {
        return new MarginLayoutParams(LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT);
    }

    @Override
    protected LayoutParams generateLayoutParams(LayoutParams p) {
        return new MarginLayoutParams(p);
    }
}
