package top.omooo.blackfish.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.view.Display;

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
    //获取屏幕的宽 返回 int 型
    public static int getScreenWidth(Activity context){
        Display display = context.getWindowManager().getDefaultDisplay();
        Point p = new Point();
        display.getSize(p);
        return p.x;
    }
}
