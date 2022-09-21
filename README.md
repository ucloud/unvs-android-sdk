# unvs-demo-android

UNVS 手机号一键登录SDK Demo for Android

[TOC]
## packages
* AAR

> ./SDK-packages/aar

* JAR

> ./SDK-packages/jar
> ./SDK-packages/jar/res

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
/**
 * 若在Manifest.xml中配置了<meta-data> name=unvs.id，则使用此方法初始化
 */
UnvsManager.create(context).register(listener);
```

* Java Runtime

> 初始化
``` java
/**
 * 若没有在Manifest.xml中配置<meta-data> name=unvs.id，则使用此方法初始化
 */
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
// 0.中文简体 1.中文繁体 2.英文
public Builder setAppLanguageType(int appLanguageType)
/** 
 * 开启安卓底部导航栏自适应
 * 开启后，导航栏唤起时，授权页面元素也会相对变化；
 * 不开启自适应，自定义内容可以铺满全屏，设置状态栏透明后，可以达到沉浸式显示效果。
 * true-开启自适应，false-关闭自适应，默认开启。
 */
public Builder setFitsSystemWindows(boolean isFitsSystemWindows)
/**
 * 可以通过方法的设置来支持dom storage，false：关闭，true：打开。默认关闭
 */
public Builder setWebDomStorageEnable(boolean isEnable)
/**
 * 设置协议勾选框+协议文本的抖动动画效果，默认无抖动。
 * 注意：如果设置了setPrivacyAlertView，则该方法的设置失效。
 */
 public Builder setPrivacyAnimation(String animation)
 /**
  * 设置未勾选协议时的弹框
  * 用户未勾选协议时，点击登录弹出弹窗，弹窗提供“同意并继续”按钮。
  * 该弹窗暂时不提供自定义样式设置。
  */
  public Builder setPrivacyAlertViewEnable(boolean isEnable)
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

### ResultCode

``` java
public class ResultCode {
    public static Map<String, String> RESULT_CODES = new ArrayMap<>();

    static {
        RESULT_CODES.put("103000", "成功");
        RESULT_CODES.put("102101", "无网络");
        RESULT_CODES.put("102102", "网络异常");
        RESULT_CODES.put("102103", "未开启数据网络");
        RESULT_CODES.put("102203", "输入参数错误");
        RESULT_CODES.put("102223", "数据解析异常，一般是卡欠费");
        RESULT_CODES.put("102507", "登录超时（授权页点登录按钮时）");
        RESULT_CODES.put("103101", "请求签名错误（若发生在客户端，可能是appkey传错，可检查是否跟appsecret弄混，或者有空格。若发生在服务端接口，需要检查验签方式是MD5还是RSA，如果是MD5，则排查signType字段，若为appsecret，需确认是否误用了appkey生签。如果是RSA，需要检查使用的私钥跟报备的公钥是否对应和报文拼接是否符合文档要求。）");
        RESULT_CODES.put("103102", "包签名错误（社区填写的appid和对应的包名包签名必须一致）");
        RESULT_CODES.put("103111", "网关IP错误（检查是否开了vpn或者境外ip）");
        RESULT_CODES.put("103119", "appid不存在（检查传的appid是否正确或是否有空格）");
        RESULT_CODES.put("103211", "其他错误，（常见于报文格式不对，先请检查是否符合这三个要求：a、json形式的报文交互必须是标准的json格式；b、发送时请设置content type为 application/json；c、参数类型都是String。如有需要请联系qq群609994083内的移动认证开发）");
        RESULT_CODES.put("103412", "无效的请求（1.加密方式错误；2.非json格式；3.空请求等）");
        RESULT_CODES.put("103414", "参数校验异常");
        RESULT_CODES.put("103511", "服务器ip白名单校验失败");
        RESULT_CODES.put("103811", "token为空");
        RESULT_CODES.put("103902", "scrip失效（客户端高频调用请求token接口）");
        RESULT_CODES.put("103911", "token请求过于频繁，10分钟内获取token且未使用的数量不超过30个");
        RESULT_CODES.put("104201", "token已失效或不存在（重复校验或失效）");
        RESULT_CODES.put("105001", "联通取号失败");
        RESULT_CODES.put("105002", "移动取号失败（一般是物联网卡）");
        RESULT_CODES.put("105003", "电信取号失败");
        RESULT_CODES.put("105012", "不支持电信取号");
        RESULT_CODES.put("105013", "不支持联通取号");
        RESULT_CODES.put("105018", "token权限不足（使用了本机号码校验的token获取号码）");
        RESULT_CODES.put("105019", "应用未授权（未在开发者社区勾选能力）");
        RESULT_CODES.put("105021", "当天已达取号限额");
        RESULT_CODES.put("105302", "appid不在白名单");
        RESULT_CODES.put("105312", "余量不足（体验版到期或套餐用完）");
        RESULT_CODES.put("105313", "非法请求");
        RESULT_CODES.put("200002", "用户未安装sim卡");
        RESULT_CODES.put("200010", "无法识别sim卡或没有sim卡");
        RESULT_CODES.put("200023", "请求超时");
        RESULT_CODES.put("200005", "用户未授权（READ_PHONE_STATE）");
        RESULT_CODES.put("200020", "授权页关闭");
        RESULT_CODES.put("200021", "数据解析异常（一般是卡欠费）");
        RESULT_CODES.put("200022", "无网络");
        RESULT_CODES.put("200023", "请求超时");
        RESULT_CODES.put("200024", "数据网络切换失败");
        RESULT_CODES.put("200025", "其他错误（socket、系统未授权数据蜂窝权限等，如需要协助，请加入qq群发问）");
        RESULT_CODES.put("200026", "输入参数错误");
        RESULT_CODES.put("200027", "未开启数据网络或网络不稳定");
        RESULT_CODES.put("200028", "网络异常");
        RESULT_CODES.put("200038", "异网取号网络请求失败");
        RESULT_CODES.put("200039", "异网取号网关取号失败");
        RESULT_CODES.put("200040", "UI资源加载异常");
        RESULT_CODES.put("200050", "EOF异常");
        RESULT_CODES.put("200072", "CA根证书校验失败");
        RESULT_CODES.put("200080", "本机号码校验仅支持移动手机号");
        RESULT_CODES.put("200082", "服务器繁忙");
        RESULT_CODES.put("200087", "授权页成功调起");
    }
}
```
