package top.omooo.blackfish.utils;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import top.omooo.blackfish.listener.OnNetResultListener;

/**
 * Created by SSC on 2018/3/3.
 */

public class OkHttpUtil {
    private static OkHttpUtil sOkHttpUtil = null;
    private OkHttpClient mHttpClient;
    private Request mRequest;

    private OkHttpUtil(){

    }

    public static OkHttpUtil getInstance() {
        if (sOkHttpUtil == null) {
            synchronized (OkHttpUtil.class) {
                if (sOkHttpUtil == null) {
                    sOkHttpUtil = new OkHttpUtil();
                }
            }
        }
        return sOkHttpUtil;
    }

    public void startGet(String url, final OnNetResultListener listener) {
        mHttpClient = new OkHttpClient();
        mRequest = new Request.Builder().url(url).build();
        mHttpClient.newCall(mRequest).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                listener.onFailureListener(e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                listener.onSuccessListener(response.body().string());
            }
        });
    }
}
