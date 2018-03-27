package top.omooo.blackfish.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.InputType;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import top.omooo.blackfish.R;
import top.omooo.blackfish.listener.OnSuperEditClickListener;

/**
 * Created by SSC on 2018/3/26.
 */

public class SuperEditText extends RelativeLayout {

    private RelativeLayout mLayout;
    private ImageView mImageLeft,mImageRight;
    private TextView mTextView;
    private EditText mEditText;
    private int typeMode;

    private static final String TAG = "SuperEditText";

    private OnSuperEditClickListener mClickListener;

    public void setOnSuperEditClickListener(OnSuperEditClickListener listener) {
        this.mClickListener = listener;
    }

    public String getData() {
        if (typeMode == 0) {
            return mEditText.getText().toString();
        } else {
            return mEditText.getHint().toString();
        }
    }
    public SuperEditText(Context context) {
        super(context);
    }

    public SuperEditText(Context context, AttributeSet attrs) {
        super(context, attrs);

        View view = LayoutInflater.from(context).inflate(R.layout.view_super_edit_text_layout, this);
        mLayout = view.findViewById(R.id.super_text_layout);
        mImageLeft = view.findViewById(R.id.iv_create_icon);
        mImageRight = view.findViewById(R.id.iv_create_to_right);
        mTextView = view.findViewById(R.id.tv_create_title);
        mEditText = view.findViewById(R.id.et_create_right);

        mLayout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mClickListener) {
                    //minSdkVersion=26
                    if (mEditText.getFocusable() == FOCUSABLE) {
                        mEditText.requestFocus();
                    }
                    mClickListener.onSuperEditClick(mTextView.getText().toString());
                    Log.i(TAG, "onClick: " + v.getId());
                }
            }
        });

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.SuperEditText);
        int drawableId = typedArray.getResourceId(R.styleable.SuperEditText_iconLeft, 0);
        String leftText = typedArray.getString(R.styleable.SuperEditText_titleText);
        String hintText = typedArray.getString(R.styleable.SuperEditText_hintTextRight);
        int typeMode = typedArray.getInteger(R.styleable.SuperEditText_typeMode, 0);
        int hintColor = typedArray.getColor(R.styleable.SuperEditText_hintTextColor, getResources().getColor(R.color.splash_main_title_color));

        /**
         * typeMode用于标识样式
         * 0 --> 右边为纯EditText
         * 1 --> 右边为纯TextView 无箭头
         * 2 --> 右边为TextView 有箭头
         * 3 --> 右边为TextView 有箭头，字体为黑色
         */
        mTextView.setText(leftText);
        mImageLeft.setImageResource(drawableId);
        mEditText.setHint(hintText);
        if (typeMode == 0) {

        } else if (typeMode == 1) {
            mEditText.setHintTextColor(hintColor);
            mEditText.setCursorVisible(false);
            mEditText.setClickable(false);
            mEditText.setFocusable(false);
            mEditText.setInputType(InputType.TYPE_NULL);
        } else if (typeMode == 2) {
            mEditText.setCursorVisible(false);
            mEditText.setClickable(false);
            mEditText.setFocusable(false);
            mEditText.setInputType(InputType.TYPE_NULL);
            mImageRight.setVisibility(VISIBLE);
        } else if (typeMode == 3) {
            mEditText.setCursorVisible(false);
            mEditText.setClickable(false);
            mEditText.setFocusable(false);
            mEditText.setInputType(InputType.TYPE_NULL);
            mImageRight.setVisibility(VISIBLE);
            mEditText.setHintTextColor(getResources().getColor(R.color.splash_main_title_color));
        }
        //释放资源
        typedArray.recycle();
    }

    public SuperEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

}
