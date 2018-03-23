package top.omooo.blackfish.utils;

import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.widget.TextView;

/**
 * Created by SSC on 2018/3/22.
 */

/**
 * SpannableString工具类
 */
public class SpannableStringUtil {

    private static SpannableString mSpannableString;
    /**
     * 设置前景色
     * @param textView
     * @param start
     * @param end
     * @param color
     * @param text
     */
    public static void setForegroundText(TextView textView, int start, int end, int color, String text) {
        mSpannableString = new SpannableString(text);
        ForegroundColorSpan span = new ForegroundColorSpan(color);
        mSpannableString.setSpan(span, start, end, SpannableString.SPAN_INCLUSIVE_EXCLUSIVE);
        textView.setText(mSpannableString);
    }

    public static void setRelativeSizeText(TextView textView, int start, int end,float relativeSize, String text) {
        mSpannableString = new SpannableString(text);
        RelativeSizeSpan sizeSpan = new RelativeSizeSpan(relativeSize);
        mSpannableString.setSpan(sizeSpan, start, end, SpannableString.SPAN_INCLUSIVE_EXCLUSIVE);
        textView.setText(mSpannableString);
    }
}
