package cn.ucloud.ulogin;

import android.content.Context;
import android.graphics.Color;

import cn.ucloud.unvs.sdk.ThemeConfig;
import cn.ucloud.unvs.sdk.util.ViewUtil;

public class DefaultTheme {

    public static ThemeConfig.Builder defaultActivityConfig(Context context) {
        return new ThemeConfig.Builder()
                .setNumberSize(22, true)
                .setNumberColor(context.getColor(R.color.unvs_number_text))
                .setNumFieldOffsetY(210)
                .setLogBtnText(context.getString(R.string.unvs_one_key_to_log_in), Color.WHITE, 14, false)
                .setLogBtnOffsetY(360)
                .setPrivacyOffsetY_B(30)
                .setPrivacyState(true)
                .setPrivacyMargin(36, 36)
                .setCheckBoxImgPath("umcsdk_check_image", "umcsdk_uncheck_image", 14, 14)
                .setCheckTipText("请同意服务条款")
                .setThemeId(R.style.Unvs_Activity_NoActionBar);
    }

    public static ThemeConfig.Builder defaultActivityLandscapeConfig(Context context) {
        return new ThemeConfig.Builder()
                .setNumberSize(20, true)
                .setNumberColor(context.getColor(R.color.unvs_number_text))
                .setNumFieldOffsetY(75)
                .setLogBtnMargin(240, 240)
                .setLogBtnText(context.getString(R.string.unvs_one_key_to_log_in), Color.WHITE, 14, false)
                .setLogBtnOffsetY(140)
                .setPrivacyOffsetY_B(10)
                .setPrivacyState(true)
                .setCheckBoxImgPath("umcsdk_check_image", "umcsdk_uncheck_image", 14, 14)
                .setCheckTipText("请同意服务条款")
                .setThemeId(R.style.Unvs_Activity_NoActionBar);
    }

    public static ThemeConfig.Builder defaultDialogConfig(Context context) {
        return new ThemeConfig.Builder()
                .setNumberSize(16, true)
                .setNumberColor(context.getColor(R.color.unvs_number_text))
                .setNumFieldOffsetY(60)
                .setLogBtnText(context.getString(R.string.unvs_one_key_to_log_in), Color.WHITE, 12, false)
                .setLogBtnOffsetY(150)
                .setPrivacyState(false)
                .setCheckTipText("请同意服务条款")
                .setPrivacyOffsetY_B(10)
                .setPrivacyMargin(8, 8)
                .setCheckBoxLocation(1)
                .setCheckBoxImgPath("umcsdk_check_image", "umcsdk_uncheck_image", 13, 13)
                .setAuthPageWindowMode((int) (ViewUtil.px2dp(context, ViewUtil.getScreenSize(context)[0]) * 0.8),
                        420)
                .setThemeId(R.style.Unvs_Dialog);
    }

    public static ThemeConfig.Builder defaultDialogBottomConfig(Context context) {
        return new ThemeConfig.Builder()
                .setNumberSize(18, true)
                .setNumberColor(context.getColor(R.color.unvs_number_text))
                .setNumFieldOffsetY(180)
                .setLogBtnText(context.getString(R.string.unvs_one_key_to_log_in), Color.WHITE, 14, false)
                .setLogBtnOffsetY(260)
                .setPrivacyState(false)
                .setCheckTipText("请同意服务条款")
                .setPrivacyOffsetY_B(30)
                .setPrivacyMargin(36, 36)
                .setPrivacyText(10, context.getColor(R.color.unvs_number_text),
                        context.getColor(R.color.unvs_primary), false, false)
                .setCheckBoxImgPath("umcsdk_check_image", "umcsdk_uncheck_image", 14, 14)
                .setAuthPageWindowMode((int) ViewUtil.px2dp(context, ViewUtil.getScreenSize(context)[0]),
                        600)
                .setWindowBottom(1)
                .setThemeId(R.style.Unvs_Dialog);
    }

}
