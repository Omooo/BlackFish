package top.omooo.blackfish.utils;

import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.widget.TextView;

/**
 * Created by SSC on 2018/3/22.
 */

/**
 * SpannableString工具类
 */
public class SpannableStringUtil {

    /**
     * 设置前景色
     * @param textView
     * @param start
     * @param end
     * @param color
     * @param text
     */
    public static void setForegroundText(TextView textView, int start, int end, int color, String text) {
        SpannableString spannableString = new SpannableString(text);
        ForegroundColorSpan span = new ForegroundColorSpan(color);
        spannableString.setSpan(span, start, end, SpannableString.SPAN_INCLUSIVE_EXCLUSIVE);
        textView.setText(spannableString);
    }
}
