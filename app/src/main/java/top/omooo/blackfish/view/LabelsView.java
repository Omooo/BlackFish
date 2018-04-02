package top.omooo.blackfish.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by SSC on 2018/4/1.
 */

public class LabelsView extends ViewGroup {

    private int mWordMargin = 20;
    private int mLineMargin = 20;

    public LabelsView(Context context) {
        super(context);
    }

    public LabelsView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public LabelsView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    // TODO: 2018/4/2 解决最下面一行绘制不全的问题 
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int count = getChildCount();
        int maxWidth = MeasureSpec.getSize(widthMeasureSpec) - getPaddingLeft() - getPaddingRight();

        int contentHeight = 0;  //记录内容的宽度
        int lineWidth = 0;  //记录行的宽度
        int maxLineWidth = 0;   //记录最宽的行宽
        int maxItemHeight = 0;  //记录一行中item最大的高度
        boolean begin = true;   //是否是行的开头

        for (int i = 0; i < count; i++) {
            View view = getChildAt(i);
            measureChild(view, widthMeasureSpec, heightMeasureSpec);
            if (maxWidth < lineWidth + view.getMeasuredWidth()) {
                contentHeight += maxItemHeight;
                maxItemHeight = 0;
                maxLineWidth = Math.max(maxLineWidth, lineWidth);
                lineWidth = 0;
                begin = true;
            }
            maxItemHeight = Math.max(maxItemHeight, view.getMeasuredHeight());
            if (begin) {
                begin = false;
            } else {
                lineWidth += mWordMargin;
            }
            lineWidth += view.getMeasuredWidth();
        }
        contentHeight += maxItemHeight;
        maxLineWidth = Math.max(maxLineWidth, lineWidth);

        //测量控件的最终宽高
        setMeasuredDimension(measureWidth(widthMeasureSpec, maxLineWidth), measureHeight(heightMeasureSpec, contentHeight));
    }

    private int measureHeight(int heightMeasureSpec, int contentHeight) {

        int result = 0;
        int specMode = MeasureSpec.getMode(heightMeasureSpec);
        int specSize = MeasureSpec.getSize(heightMeasureSpec);

        if (specMode == MeasureSpec.EXACTLY) {
            result = specSize;
        } else {
            result = contentHeight + getPaddingTop() + getPaddingBottom();
            if (specMode == MeasureSpec.AT_MOST) {
                result = Math.min(result, specSize);
            }
        }
        return result;
    }

    private int measureWidth(int widthMeasureSpec, int maxLineWidth) {
        int result = 0;
        int specMode = MeasureSpec.getMode(widthMeasureSpec);
        int specSize = MeasureSpec.getSize(widthMeasureSpec);

        if (specMode == MeasureSpec.EXACTLY) {
            result = specSize;
        } else {
            result = maxLineWidth + getPaddingLeft() + getPaddingRight();
            if (specMode == MeasureSpec.AT_MOST) {
                result = Math.min(result, specSize);
            }
        }
        return result;
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int x = getPaddingLeft();
        int y = getPaddingTop();

        int contentWidth = r - l;
        int maxItemHeight = 0;

        int count = getChildCount();
        for (int i = 0; i < count; i++) {
            View view = getChildAt(i);
            if (contentWidth < x + view.getMeasuredWidth() + getPaddingRight()) {
                x = getPaddingLeft();
                y += mLineMargin;
                y += maxItemHeight;
                maxItemHeight = 0;
            }
            view.layout(x, y, x + view.getMeasuredWidth(), y + view.getMeasuredHeight());
            x += view.getMeasuredWidth();
            x += mWordMargin;
            maxItemHeight = Math.max(maxItemHeight, view.getMeasuredHeight());
        }
    }

    @Override
    protected LayoutParams generateLayoutParams(LayoutParams p) {
        return new MarginLayoutParams(p);
    }

    @Override
    protected LayoutParams generateDefaultLayoutParams() {
        return new MarginLayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(), attrs);
    }
}
