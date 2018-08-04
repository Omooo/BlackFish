package top.omooo.blackfish.application;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.mob.MobSDK;
import com.squareup.leakcanary.LeakCanary;

/**
 * Created by Omooo on 2018/2/24.
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(this);
        MobSDK.init(this);
        LeakCanary.install(this);
    }
}
