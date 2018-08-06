# BlackFish

一款实用的商城类App，一图胜千言：

http://www.z4a.net/images/2018/08/06/fish.gif

数据是我模拟的，类似：http://www.wanandroid.com/tools/mockapi/448/mall_goods 
 返回Json数据，解析一下就行了。
 
数据库是我的云服务器，连本地数据库改下IP就行。
接入了[短信验证登录](http://wiki.mob.com/sdk-sms-android-3-0-0/)和[支付宝支付](https://openhome.alipay.com/platform/appDaily.htm?tab=info)

一些简单的自定义View比如自定义Dialog、自定义Toast、自定义通用EditText等都是很好实现的，但是一些自定义View还需参考别人的实现：

验证码输入框参考自：
https://github.com/jb274585381/VerifyCodeViewDemo

RecyclerView做轮播图参考自：
https://github.com/loonggg/RecyclerViewBanner

标签云：http://www.cnblogs.com/whoislcj/p/5720202.html

自定义Behavior：https://www.jianshu.com/p/82d18b0d18f4

我的原则是尽可能少使用开源库，但是一些必须的开源库还是要用的，比如网络请求。图片加载库等等。

#### 其他

我很菜，项目写的很简单，现在也在摸索MVP Retrofit2+RxJava2，学好了优化重构项目。

差不多一周没写该项目了，就想这么多了。

#### 感谢以下开源库：

[Vlayout](https://github.com/alibaba/vlayout)

[ButterKnife](https://github.com/JakeWharton/butterknife)

[Okhttp](https://github.com/square/okhttp)

[Fresco](https://github.com/facebook/fresco)

[Lottie](https://github.com/airbnb/lottie-android)

[MaterialDateTimePicker](https://github.com/wdullaer/MaterialDateTimePicker)

[WheelView](https://github.com/CNCoderX/WheelView)
