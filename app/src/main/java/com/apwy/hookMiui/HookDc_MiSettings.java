package com.apwy.hookMiui;

import static de.robv.android.xposed.XposedHelpers.findAndHookMethod;

import android.content.Context;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodReplacement;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

public class HookDc_MiSettings implements IXposedHookLoadPackage {

    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam lpparam) {
        if (!lpparam.packageName.equals("com.xiaomi.misettings")) {
            return;
        }
        findAndHookMethod("com.xiaomi.misettings.display.b", lpparam.classLoader, "e", Context.class, XC_MethodReplacement.returnConstant(false));
    }
}
