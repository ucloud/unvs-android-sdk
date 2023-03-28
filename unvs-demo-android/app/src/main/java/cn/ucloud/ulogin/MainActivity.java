package cn.ucloud.ulogin;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.List;

import cn.ucloud.unvs.sdk.ThemeConfig;
import cn.ucloud.unvs.sdk.bean.NetworkInfo;
import cn.ucloud.unvs.sdk.bean.VerifyMobileBean;
import cn.ucloud.unvs.sdk.listener.UnvsBackPressListener;
import cn.ucloud.unvs.sdk.listener.UnvsCheckBoxListener;
import cn.ucloud.unvs.sdk.listener.UnvsLoginClickListener;
import cn.ucloud.unvs.sdk.listener.UnvsPrivacyCheckedChangeListener;
import cn.ucloud.unvs.sdk.listener.UnvsRegisterListener;
import cn.ucloud.unvs.sdk.listener.UnvsVerifyMobileListener;
import cn.ucloud.unvs.sdk.util.PermissionUtil;
import cn.ucloud.unvs.sdk.UnvsManager;
import cn.ucloud.unvs.sdk.bean.PreloadResultBean;
import cn.ucloud.unvs.sdk.bean.TokenBean;
import cn.ucloud.unvs.sdk.listener.UnvsPreloadListener;
import cn.ucloud.unvs.sdk.listener.UnvsTokenListener;

public class MainActivity extends AppCompatActivity implements View.OnClickListener,
    UnvsRegisterListener, UnvsPreloadListener, UnvsTokenListener, UnvsVerifyMobileListener {
    private final String TAG = getClass().getSimpleName();

    private UnvsManager unvs;
    private EditText edit_overtime;
    private RecyclerView recycler_logs;
    private LogAdapter adapter;
    private List<LogBean> logs;
    private NetworkInfo network;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActionBar supportActionBar = getSupportActionBar();
        supportActionBar.setBackgroundDrawable(new ColorDrawable(ContextCompat.getColor(this, R.color.color_primary_unable)));
        supportActionBar.setTitle(String.format("UNVS SDK(%s)", UnvsManager.sdkVersion()));
        edit_overtime = findViewById(R.id.edit_timeout);
        recycler_logs = findViewById(R.id.recycler_logs);
        recycler_logs.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        logs = new ArrayList<>();
        adapter = new LogAdapter(this, logs);
        recycler_logs.setAdapter(adapter);
        findViewById(R.id.btn_timeout).setOnClickListener(this);
        findViewById(R.id.btn_preload).setOnClickListener(this);
        findViewById(R.id.btn_login).setOnClickListener(this);
        findViewById(R.id.btn_login_land).setOnClickListener(this);
        findViewById(R.id.btn_login_dialog).setOnClickListener(this);
        findViewById(R.id.btn_login_dialog_bottom).setOnClickListener(this);
        findViewById(R.id.btn_verify_mobile).setOnClickListener(this);
        unvs = UnvsManager.create(this);
        unvs.register(this);
        long timeout = unvs.getTimeout();
        edit_overtime.setText(String.valueOf(timeout));
        notifyLog(new LogBean(String.format("[current timeout]: %d ms", timeout)));
        network = unvs.getNetworkInfo();
        notifyLog(new LogBean(String.format("[network]: %s %s",
            network.getNetworkType().name(), network.getOperatorType().name())));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_timeout: {
                String timeout = edit_overtime.getText().toString();
                if (timeout == null || timeout.isEmpty()) {
                    AlertDialog dialog = new AlertDialog.Builder(this)
                        .setTitle("Error")
                        .setMessage("Please input valid timeout value!")
                        .setCancelable(true)
                        .create();
                    dialog.show();
                    return;
                }

                unvs.setTimeout(Long.parseLong(timeout));
                notifyLog(new LogBean(String.format("[set timeout]: %s ms", timeout)));
                break;
            }
            case R.id.btn_preload: {
                unvs.preloadAuthorization(this, 1000);
                break;
            }
            case R.id.btn_login: {
                unvs.setAuthThemeConfigure(DefaultTheme.defaultActivityConfig(this)
                    .setAuthContentView(initActivityView(this, false))
                    .setStatusBar(ContextCompat.getColor(this, R.color.white), true)
                    /**
                     * 配置自定义协议
                     * 参数1：协议同意整体文案
                     * 参数2~9：两两一对，
                     *      一对中的第一个：会将整体文案中的对应文本改为超链接并自动添加书名号
                     *      一对中的第二个：是第一个参数的url地址
                     *      最多添加4个自定义协议
                     * {@link ThemeConfig.PLACEHOLDER} 是运营商协议的占位符，sdk将会自动根据识别出的手机号运营商显示对应协议，法律法规要求必须添加
                     */
                    .setPrivacyAlignment("登录即同意" + ThemeConfig.PLACEHOLDER + "，以及自定义协议1和自定义协议2。", "自定义协议1", "https://www.baidu.com", "自定义协议2", "https://www.ucloud.cn", null, null, null, null)
                    /**
                     * setEnableDialogBackButton 只对对话框形式的授权页面有效
                     */
                    .setEnableDialogBackButton(true)
                    .setPrivacyCheckedChangeListener(new UnvsPrivacyCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(boolean b) {
                            /**
                             * 回调条款勾选框check状态
                             */
                            Log.i("TEST", "onCheckedChanged->" + b);
                        }
                    })
                    .setLogBtnClickListener(new UnvsLoginClickListener() {
                        @Override
                        public void onLoginClickStart(Context context) {
                            Log.i("TEST", "onLoginClickStart");
                        }

                        @Override
                        public void onLoginClickComplete(Context context) {
                            Log.i("TEST", "onLoginClickComplete");
                            unvs.quitLoginAuth();
                        }
                    })
                    .setBackPressedListener(new UnvsBackPressListener() {
                        @Override
                        public void onBackPressed() {
                            /**
                             * 回调返回键点击
                             */
                            Log.i("TEST", "onBackPressed");
                        }
                    })
                    .setCheckBoxListener(new UnvsCheckBoxListener() {
                        @Override
                        public void onLoginClick(Context context) {
                            /**
                             * 该回调只在没有勾选条款的情况下点击"一键登录"时才会回调
                             */
                            Log.i("TEST", "onLoginClick");
                        }
                    })
                    .build());

                unvs.loginAuth(this);
                break;
            }
            case R.id.btn_login_land: {
                if (getRequestedOrientation() != ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                }
                unvs.setAuthThemeConfigure(DefaultTheme.defaultActivityLandscapeConfig(this)
                    .setAuthContentView(initActivityView(this, true))
                    .setStatusBar(ContextCompat.getColor(this, R.color.white), true)
                    /**
                     * setEnableDialogBackButton 只对对话框形式的授权页面有效
                     */
                    .setEnableDialogBackButton(true)
                    .setPrivacyCheckedChangeListener(new UnvsPrivacyCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(boolean b) {
                            /**
                             * 回调条款勾选框check状态
                             */
                            Log.i("TEST", "onCheckedChanged->" + b);
                        }
                    })
                    .setLogBtnClickListener(new UnvsLoginClickListener() {
                        @Override
                        public void onLoginClickStart(Context context) {
                            Log.i("TEST", "onLoginClickStart");
                        }

                        @Override
                        public void onLoginClickComplete(Context context) {
                            Log.i("TEST", "onLoginClickComplete");
                            unvs.quitLoginAuth();
                        }
                    })
                    .setBackPressedListener(new UnvsBackPressListener() {
                        @Override
                        public void onBackPressed() {
                            /**
                             * 回调返回键点击
                             */
                            Log.i("TEST", "onBackPressed");
                        }
                    })
                    .setCheckBoxListener(new UnvsCheckBoxListener() {
                        @Override
                        public void onLoginClick(Context context) {
                            /**
                             * 该回调只在没有勾选条款的情况下点击"一键登录"时才会回调
                             */
                            Log.i("TEST", "onLoginClick");
                        }
                    })
                    .build());

                unvs.loginAuth(this);
                break;
            }
            case R.id.btn_login_dialog: {
                unvs.setAuthThemeConfigure(DefaultTheme.defaultDialogConfig(this)
                    .setAuthContentView(initDialogView(this))
                    /**
                     * setEnableDialogBackButton 只对对话框形式的授权页面有效
                     */
                    .setEnableDialogBackButton(true)
                    .setPrivacyCheckedChangeListener(new UnvsPrivacyCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(boolean b) {
                            /**
                             * 回调条款勾选框check状态
                             */
                            Log.i("TEST", "onCheckedChanged->" + b);
                        }
                    })
                    .setLogBtnClickListener(new UnvsLoginClickListener() {
                        @Override
                        public void onLoginClickStart(Context context) {
                            Log.i("TEST", "onLoginClickStart");
                        }

                        @Override
                        public void onLoginClickComplete(Context context) {
                            Log.i("TEST", "onLoginClickComplete");
                            unvs.quitLoginAuth();
                        }
                    })
                    .setBackPressedListener(new UnvsBackPressListener() {
                        @Override
                        public void onBackPressed() {
                            /**
                             * 回调返回键点击
                             */
                            Log.i("TEST", "onBackPressed");
                        }
                    })
                    .setCheckBoxListener(new UnvsCheckBoxListener() {
                        @Override
                        public void onLoginClick(Context context) {
                            /**
                             * 该回调只在没有勾选条款的情况下点击"一键登录"时才会回调
                             */
                            Log.i("TEST", "onLoginClick");
                        }
                    })
                    .build());

                unvs.loginAuth(this);
                break;
            }
            case R.id.btn_login_dialog_bottom: {
                unvs.setAuthThemeConfigure(DefaultTheme.defaultDialogBottomConfig(this)
                    .setAuthContentView(initDialogBottomView(this))
                    /**
                     * setEnableDialogBackButton 只对对话框形式的授权页面有效
                     */
                    .setEnableDialogBackButton(true)
                    .setPrivacyCheckedChangeListener(new UnvsPrivacyCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(boolean b) {
                            /**
                             * 回调条款勾选框check状态
                             */
                            Log.i("TEST", "onCheckedChanged->" + b);
                        }
                    })
                    .setLogBtnClickListener(new UnvsLoginClickListener() {
                        @Override
                        public void onLoginClickStart(Context context) {
                            Log.i("TEST", "onLoginClickStart");
                        }

                        @Override
                        public void onLoginClickComplete(Context context) {
                            Log.i("TEST", "onLoginClickComplete");
                            unvs.quitLoginAuth();
                        }
                    })
                    .setBackPressedListener(new UnvsBackPressListener() {
                        @Override
                        public void onBackPressed() {
                            /**
                             * 回调返回键点击
                             */
                            Log.i("TEST", "onBackPressed");
                        }
                    })
                    .setCheckBoxListener(new UnvsCheckBoxListener() {
                        @Override
                        public void onLoginClick(Context context) {
                            /**
                             * 该回调只在没有勾选条款的情况下点击"一键登录"时才会回调
                             */
                            Log.i("TEST", "onLoginClick");
                        }
                    })
                    .build());

                unvs.loginAuth(this);
                break;
            }
            case R.id.btn_verify_mobile: {
                unvs.verifyMobile(this);
                break;
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.i(TAG, "onActivityResult->[requestCode]:" + requestCode);
    }

    private View.OnClickListener clickListener = v -> {
        switch (v.getId()) {
            case R.id.unvs_txt_change_account: {
                Toast.makeText(MainActivity.this, R.string.unvs_change_account, Toast.LENGTH_SHORT).show();
                break;
            }
            case R.id.unvs_btn_wechat: {
                Toast.makeText(MainActivity.this, R.string.unvs_login_with_wechat, Toast.LENGTH_SHORT).show();
                break;
            }
            case R.id.unvs_btn_qq: {
                Toast.makeText(MainActivity.this, R.string.unvs_login_with_qq, Toast.LENGTH_SHORT).show();
                break;
            }
            case R.id.unvs_btn_weibo: {
                Toast.makeText(MainActivity.this, R.string.unvs_login_with_weibo, Toast.LENGTH_SHORT).show();
                break;
            }
        }
    };

    private View initActivityView(Context context, boolean isLandscape) {
        View contentView = LayoutInflater.from(context).inflate(
            isLandscape ? R.layout.unvs_activity_landscape_content : R.layout.unvs_activity_content, null);
        int supportStringId = 0;
        switch (network.getOperatorType()) {
            case CMCC:
                supportStringId = R.string.unvs_supporter_cmcc;
                break;
            case CUCC:
                supportStringId = R.string.unvs_supporter_cucc;
                break;
            case CTCC:
                supportStringId = R.string.unvs_supporter_ctcc;
                break;
        }

        if (supportStringId != 0)
            ((TextView) contentView.findViewById(R.id.unvs_txt_supporter)).setText(supportStringId);

        contentView.findViewById(R.id.unvs_txt_change_account).setOnClickListener(clickListener);
        contentView.findViewById(R.id.unvs_btn_wechat).setOnClickListener(clickListener);
        contentView.findViewById(R.id.unvs_btn_qq).setOnClickListener(clickListener);
        contentView.findViewById(R.id.unvs_btn_weibo).setOnClickListener(clickListener);

        return contentView;
    }

    private View initDialogView(Context context) {
        View contentView = LayoutInflater.from(context).inflate(R.layout.unvs_dialog_content, null);
        int supportStringId = 0;
        switch (network.getOperatorType()) {
            case CMCC:
                supportStringId = R.string.unvs_supporter_cmcc;
                break;
            case CUCC:
                supportStringId = R.string.unvs_supporter_cucc;
                break;
            case CTCC:
                supportStringId = R.string.unvs_supporter_ctcc;
                break;
        }
        if (supportStringId != 0)
            ((TextView) contentView.findViewById(R.id.unvs_txt_supporter)).setText(supportStringId);

        contentView.findViewById(R.id.unvs_txt_change_account).setOnClickListener(clickListener);
        contentView.findViewById(R.id.unvs_btn_wechat).setOnClickListener(clickListener);
        contentView.findViewById(R.id.unvs_btn_qq).setOnClickListener(clickListener);
        contentView.findViewById(R.id.unvs_btn_weibo).setOnClickListener(clickListener);

        return contentView;
    }

    private View initDialogBottomView(Context context) {
        View contentView = LayoutInflater.from(context).inflate(R.layout.unvs_dialog_bottom_content, null);
        int supportStringId = 0;
        switch (network.getOperatorType()) {
            case CMCC:
                supportStringId = R.string.unvs_supporter_cmcc;
                break;
            case CUCC:
                supportStringId = R.string.unvs_supporter_cucc;
                break;
            case CTCC:
                supportStringId = R.string.unvs_supporter_ctcc;
                break;
        }
        if (supportStringId != 0)
            ((TextView) contentView.findViewById(R.id.unvs_txt_supporter)).setText(supportStringId);

        contentView.findViewById(R.id.unvs_txt_change_account).setOnClickListener(clickListener);
        contentView.findViewById(R.id.unvs_btn_wechat).setOnClickListener(clickListener);
        contentView.findViewById(R.id.unvs_btn_qq).setOnClickListener(clickListener);
        contentView.findViewById(R.id.unvs_btn_weibo).setOnClickListener(clickListener);

        return contentView;
    }

    @Override
    protected void onDestroy() {
        if (unvs != null)
            unvs.destroy();
        super.onDestroy();
    }

    @Override
    public void onRegisted(boolean isSuccess, Exception exception) {
        if (isSuccess) {
            Log.d(TAG, String.format("[regist]: %b", true));
            notifyLog(new LogBean("Regist success"));
        } else {
            notifyLog(new LogBean("Regist failed!\n[exception]:" + exception.getMessage()));
            AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("Error")
                .setMessage(exception.getMessage())
                .setCancelable(true)
                .create();
            dialog.show();
        }
    }

    @Override
    public void onPreloaded(int requestCode, PreloadResultBean token) {
        Log.d(TAG, String.format("[preload]: code=%d data=%s", requestCode, token.toString()));
        notifyLog(new LogBean(String.format("[preload]: code=%d data=%s", requestCode, token.toString())));
    }

    @Override
    public void onPreloadFailed(int requestCode, Exception exception) {
        Log.w(TAG, String.format("[preload failed]: code=%d", requestCode), exception);
        notifyLog(new LogBean(String.format("[preload failed]: code=%d\n[exception]:%s", requestCode, exception.getMessage())));
    }

    @Override
    public void onGetToken(int requestCode, TokenBean token) {
        Log.d(TAG, String.format("[token]: code=%d data=%s", requestCode, token.toString()));
        notifyLog(new LogBean(String.format("[token]: code=%d data=%s", requestCode, token.toString())));
        if (getRequestedOrientation() == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE)
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    @Override
    public void onGetTokenFailed(int requestCode, Exception exception) {
        Log.w(TAG, String.format("[token failed]: code=%d", requestCode), exception);
        notifyLog(new LogBean(String.format("[token failed]: code=%d\n[exception]:%s", requestCode, exception.getMessage())));
    }

    @Override
    public void onVerified(int requestCode, VerifyMobileBean result) {
        Log.d(TAG, String.format("[verify mobile]: code=%d data=%s", requestCode, result.toString()));
        notifyLog(new LogBean(String.format("[verify mobile]: code=%d data=%s", requestCode, result.toString())));
    }

    @Override
    public void onVerifiedFailed(int requestCode, Exception exception) {
        Log.w(TAG, String.format("[verify mobile failed]: code=%d", requestCode), exception);
        notifyLog(new LogBean(String.format("[verify mobile failed]: code=%d\n[exception]:%s", requestCode, exception.getMessage())));
    }

    private void notifyLog(LogBean log) {
        logs.add(log);
        int pos = logs.size() - 1;
        adapter.notifyItemInserted(pos);
        recycler_logs.smoothScrollToPosition(pos);
    }
}