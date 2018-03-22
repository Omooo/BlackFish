package top.omooo.blackfish.MinePageActivity;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;

import java.util.List;

import top.omooo.blackfish.BaseActivity;
import top.omooo.blackfish.R;
import top.omooo.blackfish.utils.FrescoEngine;
import top.omooo.blackfish.utils.RequestPermissionUtil;
import top.omooo.blackfish.view.CustomToast;

/**
 * Created by SSC on 2018/3/21.
 */

public class SuggestionActivity extends BaseActivity {

    private TextView mTextSugFace, mTextOther;
    private EditText mEditSug, mEditPhone;
    private SimpleDraweeView mAddImage;
    private Button mButtonSubmit;

    private Context mContext;
    private static final int REQUEST_CODE = 1;

    @Override
    public int getLayoutId() {
        return R.layout.activity_suggestion_layout;
    }

    @Override
    public void initViews() {

        if (Build.VERSION.SDK_INT >= 23) {
//            requestPermission();
            RequestPermissionUtil.reqPermission(this,Manifest.permission.READ_EXTERNAL_STORAGE,"申请必要权限用于读取内存卡",REQUEST_CODE);
        }
        mContext = SuggestionActivity.this;
        getWindow().getDecorView().setSystemUiVisibility( View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        mTextSugFace = findView(R.id.tv_sug_interface);
        mTextOther = findView(R.id.tv_sug_other);

        mEditSug = findView(R.id.et_sug);
        mEditPhone = findView(R.id.et_sug_phone_text);
        mAddImage = findView(R.id.iv_sug_add_image);
        mButtonSubmit = findView(R.id.btn_sug_submit);

        //默认选择反馈类型为：体验与界面
        switchType(mTextSugFace,true);
        mAddImage.setImageURI(getUriFromDrawableRes(mContext,R.drawable.icon_sug_add_image));
    }

    @Override
    public void initListener() {
        mTextSugFace.setOnClickListener(this);
        mTextOther.setOnClickListener(this);

        mAddImage.setOnClickListener(this);
        mButtonSubmit.setOnClickListener(this);
    }

    @Override
    public void processClick(View view) {
        switch (view.getId()) {
            case R.id.tv_sug_interface:
                switchType(mTextSugFace, true);
                switchType(mTextOther,false);
                break;
            case R.id.tv_sug_other:
                switchType(mTextSugFace, false);
                switchType(mTextOther,true);
                break;
            case R.id.iv_sug_add_image:
//                selectImage(1);
                break;
            case R.id.btn_sug_submit:
                if (mEditSug.getText().length() > 3) {
                    CustomToast.show(mContext, "已提交，谢谢反馈。");
                    finish();
                    overridePendingTransition(0,R.anim.activity_login_top_out);
                } else {
                    CustomToast.show(mContext,"请填写反馈内容，不少于三个字");
                }
                break;
            default:break;
        }
    }

    @Override
    protected void initData() {

    }

    private void switchType(TextView textView, boolean isSelected) {
        if (isSelected) {
            textView.setBackground(getDrawable(R.drawable.shape_sug_text_view_selected));
            textView.setTextColor(getColor(R.color.splash_main_title_color));
        } else {
            textView.setBackground(getDrawable(R.drawable.shape_sug_text_view_unselected));
            textView.setTextColor(getColor(R.color.colorDivider));
        }
    }

    // TODO: 2018/3/21 图片选择器，不支持Fresco 卒 
    private void selectImage(int requestCode) {
        Matisse.from(SuggestionActivity.this)
                .choose(MimeType.allOf())
                .countable(true)
                .maxSelectable(5)
//                .addFilter(new GifSizeFilter(320, 320, 5 * Filter.K * Filter.K))
//                .gridExpectedSize(getResources().getDimensionPixelSize(R.dimen.grid_expected_size))
                .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                .thumbnailScale(0.85f)
                .imageEngine(new FrescoEngine())
                .forResult(requestCode);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        List<Uri> mSelected;
        if (requestCode == 1 && resultCode == RESULT_OK) {
            mSelected = Matisse.obtainResult(data);
            mAddImage.setImageURI(mSelected.get(0));
        }
    }

    public Uri getUriFromDrawableRes(Context context, int id) {
        Resources resources = context.getResources();
        String path = ContentResolver.SCHEME_ANDROID_RESOURCE + "://"
                + resources.getResourcePackageName(id) + "/"
                + resources.getResourceTypeName(id) + "/"
                + resources.getResourceEntryName(id);
        return Uri.parse(path);
    }

    private boolean requestPermission() {
        //检查是否已经有该权限
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            //权限没有开启，请求权限
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 0);
            return true;
        }else{
            //权限已经开启
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //同意
                CustomToast.show(this,"权限申请成功");
            } else {
                //权限被拒绝
                CustomToast.show(this,"权限已被拒绝");
            }
        }
    }
}
