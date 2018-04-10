package top.omooo.blackfish.fragment;

import android.content.Context;
import android.webkit.WebView;

import butterknife.BindView;
import top.omooo.blackfish.R;

/**
 * Created by Omooo on 2018/2/25.
 */

public class FinancialFragment extends NewBaseFragment {

    @BindView(R.id.webview)
    WebView mWebview;
    private Context mContext;

    public static FinancialFragment newInstance() {
        return new FinancialFragment();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_financial_layout;
    }

    @Override
    public void initView() {
        mContext = getActivity();
        mWebview.loadUrl("http://omooo.top/");
    }

    @Override
    public void initData() {

    }

}
