package top.omooo.blackfish.MallPagerActivity;

import android.content.Context;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import top.omooo.blackfish.BaseActivity;
import top.omooo.blackfish.R;
import top.omooo.blackfish.utils.KeyBoardUtil;
import top.omooo.blackfish.view.CustomToast;
import top.omooo.blackfish.view.LabelsViewDemo;

/**
 * Created by SSC on 2018/4/1.
 */

public class SearchActivity extends BaseActivity {

    private EditText mEditText;
    private TextView mTextCancel;
    private LabelsViewDemo mLabelsHistory,mLabelsFire;

    private Context mContext;
    private static final String TAG = "SearchActivity";
    private String[] searchTexts = new String[10];

    @Override
    public int getLayoutId() {
        return R.layout.activity_mall_search_layout;
    }

    @Override
    public void initViews() {
        mContext = SearchActivity.this;
        mEditText = findView(R.id.et_search);
        mTextCancel = findView(R.id.tv_search_cancel);
        mLabelsHistory = findView(R.id.labels_view_history);
        mLabelsFire = findView(R.id.labels_view_fire);

        // TODO: 2018/4/2 点击EditText软键盘不显示？？？
        mEditText.setFocusableInTouchMode(true);
        mEditText.requestFocus();
        KeyBoardUtil.showKeyBoard(mEditText);

        addSearchText("111");
    }

    @Override
    public void initListener() {
        mTextCancel.setOnClickListener(this);

        mEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                switch (actionId) {
                    case EditorInfo.IME_ACTION_SEARCH:

                        CustomToast.show(mContext,"搜索");
                        break;
                    default:break;
                }
                return false;
            }
        });
    }

    @Override
    public void processClick(View view) {
        switch (view.getId()) {
            case R.id.tv_search_cancel:
                KeyBoardUtil.closeKeyBoard(mEditText);
                finish();
                break;
            default:break;
        }
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onBackPressed() {
        KeyBoardUtil.closeKeyBoard(mEditText);
        super.onBackPressed();
    }

    private void addChildView(String[] text) {
        for (int i = 0; i < text.length; i++) {
            TextView view = (TextView) LayoutInflater.from(mContext).inflate(R.layout.view_lables_item_layout, null);
            view.setText(text[i]);
            mLabelsHistory.addView(view);
        }
    }

    private void addSearchText(String text) {
        for (int i = 9; i >= 0; i--) {
            if (i != 0 && searchTexts[i] != null) {
                searchTexts[i] = searchTexts[i - 1];
            } else if (i == 0) {
                searchTexts[i] = text;
            }
        }
        for (int i = 0; i < searchTexts.length; i++) {
            Log.i(TAG, "addSearchText: " + searchTexts[i]);
        }
    }
}
