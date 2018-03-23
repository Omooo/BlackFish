package top.omooo.blackfish.view;

import android.app.AlertDialog;
import android.content.Context;
import android.view.View;
import android.widget.TextView;

import top.omooo.blackfish.R;

/**
 * Created by SSC on 2018/3/22.
 */

/**
 * 内存泄漏，不玩了，气哭！
 */

// TODO: 2018/3/22 自定义Dialog 挖坑 
public class CustomDialog {
    public static void show(Context context, String title, String navigateText, String positiveText, View.OnClickListener listener) {

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View view = View.inflate(context, R.layout.view_custom_dialog_layout, null);
        TextView textTitle = view.findViewById(R.id.tv_custom_dialog_title);
        TextView navText = view.findViewById(R.id.tv_dialog_nav_text);
        TextView posText = view.findViewById(R.id.tv_dialog_pos_text);
        textTitle.setText(title);
        navText.setText(navigateText);
        posText.setText(positiveText);
        navText.setOnClickListener(listener);
        posText.setOnClickListener(listener);
        builder.setView(view);
        builder.setCancelable(false);
        builder.create().show();
    }
}
