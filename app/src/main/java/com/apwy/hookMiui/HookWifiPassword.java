package com.apwy.hookMiui;

import static de.robv.android.xposed.XposedHelpers.callStaticMethod;
import static de.robv.android.xposed.XposedHelpers.findAndHookMethod;
import static de.robv.android.xposed.XposedHelpers.findClass;
import static de.robv.android.xposed.XposedHelpers.getObjectField;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.net.wifi.WifiConfiguration;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.HashMap;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

public class HookWifiPassword implements IXposedHookLoadPackage {
    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam lpparam) {
        if (!lpparam.packageName.equals("com.android.settings")) {
            return;
        }
        findAndHookMethod("com.android.settings.wifi.MiuiWifiSettings", lpparam.classLoader, "showSharePasswordDialog", WifiConfiguration.class, new XC_MethodHook() {
            @Override
            @SuppressLint("DiscouragedApi")
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                super.afterHookedMethod(param);
                Dialog dialog = (Dialog) getObjectField(param.thisObject, "mSharedDialog");
                if (dialog != null && dialog.isShowing()) {
                    Context context = dialog.getContext();
                    int id = context.getResources().getIdentifier("layout_wifi_share_qrcode", "id", context.getPackageName());
                    LinearLayout ll = dialog.findViewById(id);
                    TextView tv = (TextView) ll.getChildAt(ll.getChildCount() - 1);
                    tv.setTextIsSelectable(true);
                    WifiConfiguration configuration = (WifiConfiguration) param.args[0];
                    HashMap<String, String> stringHashMap = WifiSpliterNew.wifiSpliterNew(getWifiPwd(context, configuration));
                    if (!configuration.hiddenSSID) {
                        tv.setText(String.format("密码:%s", stringHashMap.get("P")));
                    } else {
                        tv.setText(String.format("名称:%s%n密码:%s", stringHashMap.get("S"), stringHashMap.get("P")));
                    }

                }
            }
        });
    }

    private String getWifiPwd(Context context, WifiConfiguration configuration) {
        Class<?> aClass = findClass("com.android.settings.wifi.QRCodeUtils", context.getClassLoader());
        return (String) callStaticMethod(aClass, "getWifiQrcodeText", context, configuration);
    }
}
