package top.omooo.blackfish.utils;

import android.app.Activity;
import android.app.Dialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import top.omooo.blackfish.R;
import top.omooo.blackfish.adapter.PickerAdapter;

/**
 * Created by SSC on 2018/3/29.
 */

public class PickerUtil {
    private Dialog mDialog;
    private TextView mTextCancel, mTextDeterMine;
    private RecyclerView mRecyclerView;
    public void showPicker(Activity activity, String[] strings) {

        mDialog = new Dialog(activity, R.style.BottomDialogStyle);
        View view = LayoutInflater.from(activity).inflate(R.layout.view_picker_layout, null);

        mTextCancel = view.findViewById(R.id.tv_picker_cancel);
        mTextDeterMine = view.findViewById(R.id.tv_picker_determine);
        mRecyclerView = view.findViewById(R.id.rv_picker);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(activity));
        mRecyclerView.setAdapter(new PickerAdapter(activity,strings));

        mTextCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
            }
        });
        mTextDeterMine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        mDialog.setContentView(view);
        Window window = mDialog.getWindow();
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        layoutParams.width = DensityUtil.getScreenWidth(activity);
        layoutParams.height = DensityUtil.dip2px(activity, 300);
        layoutParams.gravity = Gravity.BOTTOM;
        window.setAttributes(layoutParams);
        mDialog.show();
    }
}
