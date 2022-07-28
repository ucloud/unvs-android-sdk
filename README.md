# unvs-demo-android

UNVS 手机号一键登录SDK Demo for Android

[TOC]
## packages
* AAR

> ./SDK-packages/aar

* JAR

> ./SDK-packages/jar

## dependencies

``` java
implementation 'com.google.code.gson:gson:2.8.8'
implementation files('libs/unvs-sdk-android_x.x.x.aar')
```

## proguard-rules.pro

``` java
-dontwarn cn.ucloud.unvs.**
-keep class cn.ucloud.unvs.sdk.** {
    public <fields>;
    public <methods>;
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

-dontwarn com.cmic.gen.sdk.**
-keep class com.cmic.gen.sdk.**{*;}
```

## 配置 UNVS ID
* AndroidManifest.xml

``` xml
<application>
    <meta-data
        android:name="unvs.id"
        android:value="Your ID" />
    ...
    
    <activity
            android:name="com.cmic.gen.sdk.view.GenLoginAuthActivity"
            android:configChanges="orientation|keyboardHidden|screenSize"
            android:launchMode="singleTop"
            android:screenOrientation="behind" />
</application>
```
> 初始化
``` java
UnvsManager.create(context).register(listener);
```

* Java Runtime

> 初始化
``` java
UnvsManager.create(context).register(unvsId, listener);
```

## API

### UnvsManager

#### 获取SDK version
``` java
public static String sdkVersion()
```

#### 创建UnvsManager实例
``` java
public static UnvsManager create(Context context)
```

* parameter:
    * context: Android 上下文
* return:
    * UnvsManager实例

    
#### 获取UnvsManager实例
``` java
public static UnvsManager getInstance()
```

* parameter:
    * -
* return:
    * UnvsManager实例

#### 销毁UnvsManager实例
``` java
public static void destroy()
```

* parameter:
    * -
* return:
    * -

#### 注册服务
``` java
public void register(UnvsRegisterListener listener)
```
* parameter:
    * listener: 注册callback
* return:
    * -
    
``` java
public void register(String applicationId, UnvsRegisterListener listener)
```
* parameter:
    * applicationId: UCloud控制台上登记APP信息后分配的applicationId 
    * listener: 注册callback
* return:
    * -
    
#### 预取号
``` java
public void preloadAuthorization(UnvsPreloadListener listener, int requestCode)
```

* parameter:
    * listener: 预取号callback
    * requestCode: 请求码，callback中会返回该请求码
* return:
    * -

#### 自定义授权页UI配置
``` java
public void setAuthThemeConfigure(ThemeConfig theme)
```

* parameter:
    * theme: UI配置项
* return:
    * -

#### 取号授权（弹出授权页）
``` java
public void loginAuth(UnvsTokenListener listener, int requestCode)
```

* parameter:
    * listener: 取号授权callback
    * requestCode: 请求码，callback中会返回该请求码
* return:
    * -
    
#### 退出授权页
``` java
public void quitLoginAuth()
```

* parameter:
    * -
* return:
    * -
    
#### 手机号校验授权
``` java
public void verifyMobile(UnvsVerifyMobileListener listener, int requestCode)
```

* parameter:
    * listener: 手机号校验授权callback
    * requestCode: 请求码，callback中会返回该请求码
* return:
    * -
    
#### 获取当前网络状态及运营商信息
``` java
public NetworkInfo getNetworkInfo()
```

* parameter:
    * -
* return:
    * 当前网络状态及运营商信息

#### 设置服务接口超时时间
``` java
public void setTimeout(long overtime)
```

* parameter:
    * overtime: 超时时间（毫秒）
* return:
    * -

### 自定义授权页配置项 (ThemeConfig.Builder)
``` java
// 设置状态栏颜色
public Builder setStatusBar(int statusBarColor, boolean isLightColor)
// 设置自定义ContentView
public Builder setAuthContentView(View contentView)
// 设置手机号码字体大小
public Builder setNumberSize(int numberSize, boolean isBold)
// 设置手机号码字体颜色
public Builder setNumberColor(int numberColor)
// 设置手机号码的横向位置偏移
public Builder setNumberOffsetX(int numberOffsetX)
// 设置手机号码的纵向位置相对于顶部偏移
public Builder setNumFieldOffsetY(int numFieldOffsetY)
// 设置手机号码的纵向位置相对于底部偏移
public Builder setNumFieldOffsetY_B(int numFieldOffsetY_B)
// 设置授权按钮文本
public Builder setLogBtnText(String logBtnText)
// 设置授权按钮文本以及字体信息
public Builder setLogBtnText(String logBtnText, int logBtnTextColor, int logBtnTextSize, boolean isBold)
// 设置授权按钮字体颜色
public Builder setLogBtnTextColor(int logBtnTextColor)
// 设置授权按钮背景资源名称（btn.png就填btn）
public Builder setLogBtnImgPath(String logBtnBackgroundPath)
// 设置授权按钮尺寸
public Builder setLogBtn(int width, int height)
// 设置授权按钮横向margin
public Builder setLogBtnMargin(int marginLeft, int marginRight)
// 设置授权按钮的纵向位置相对于顶部偏移
public Builder setLogBtnOffsetY(int logBtnOffsetY)
// 设置授权按钮的纵向位置相对于底部偏移
public Builder setLogBtnOffsetY_B(int logBtnOffsetY_B)
// 设置协议勾选框未选中时的提示文本
public Builder setCheckTipText(String checkTipText)
// 设置协议勾选框选中状态图片资源名称
public Builder setCheckedImgPath(String checkedImgPath)
// 设置协议勾选框未选中状态图片资源名称
public Builder setUncheckedImgPath(String uncheckedImgPath)
// 设置协议勾选框详情
public Builder setCheckBoxImgPath(String checkedImgPath, String uncheckedImgPath, int width, int height)
// 设置协议默认勾选状态
public Builder setPrivacyState(boolean privacyState)
// 设置协议字体颜色
public Builder setClauseColor(int clauseBaseColor, int clauseColor)
// 设置协议横向margin
public Builder setPrivacyMargin(int privacyMarginLeft, int privacyMarginRight)
// 设置协议纵向相对于顶部的偏移
public Builder setPrivacyOffsetY(int privacyOffsetY)
// 设置协议纵向相对于底部的偏移
public Builder setPrivacyOffsetY_B(int privacyOffsetY_B)
// 设置协议名称是否显示书名号
public Builder setPrivacyBookSymbol(boolean haveBookSymbol)
// 设置复选框相对右侧协议文案居上或者居中，默认居上。0-居上，1-居中
public Builder setCheckBoxLocation(int checkBoxLocation)
// 设置协议政策勾选框的勾选状态切换回调
public Builder setPrivacyCheckedChangeListener(UnvsPrivacyCheckedChangeListener listener)
// 设置授权页的进场动画
public Builder setAuthPageActIn(String authPageActIn, String activityOut)
// 设置授权页的退场动画
public Builder setAuthPageActOut(String activityIn, String authPageActOut)
// 设置授权页窗口宽高比例
public Builder setAuthPageWindowMode(int windowWidth, int windowHeight) 
// 设置授权页窗口X轴Y轴偏移
public Builder setAuthPageWindowOffset(int windowX, int windowY)
// 设置授权页是否居于底部，0=居中；1=底部，设置为1Y轴的偏移失效
public Builder setWindowBottom(int windowBottom) 
// 设置授权页弹窗主题，也可在Manifest设置
public Builder setThemeId(int themeId)
// 0.中文简体1.中文繁体2.英文
public Builder setAppLanguageType(int appLanguageType)
/** 开启安卓底部导航栏自适应，开启后，导航栏唤起时，授权页面元素也会相对变化；
  * 不开启自适应，自定义内容可以铺满全屏，设置状态栏透明后，可以达到沉浸式显示效果。
  * 0-开启自适应，1-关闭自适应，默认开启。
**/
public Builder setFitsSystemWindows(boolean isFitsSystemWindows)
```

### UnvsRegisterListener

``` java
// 注册完成
void onRegisted(boolean isSuccess, Exception exception);
```

### UnvsPreloadListener

``` java
// 预取号完成
void onPreloaded(int requestCode, PreloadResultBean token);
// 预取号失败
void onPreloadFailed(int requestCode, Exception exception);
```

### UnvsTokenListener

``` java
// 获取授权Token完成
void onGetToken(int requestCode, TokenBean token);
// 获取授权Token失败
void onGetTokenFailed(int requestCode, Exception exception);
```

### UnvsVerifyMobileListener

``` java
// 校验完成
void onVerified(int requestCode, VerifyMobileBean result);
// 校验失败
void onVerifiedFailed(int requestCode, Exception exception);
```
