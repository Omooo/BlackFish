package top.omooo.blackfish.utils;

import android.content.Context;

/**
 * Created by SSC on 2018/3/23.
 */

public class DensityUtil {
    /**
     * dp 转 int
     * @param context
     * @param dpValue
     * @return
     */
    public static int dip2px(Context context,float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
