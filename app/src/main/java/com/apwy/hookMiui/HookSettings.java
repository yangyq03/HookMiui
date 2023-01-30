package com.apwy.hookMiui;

import static de.robv.android.xposed.XposedHelpers.findAndHookMethod;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodReplacement;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

public class HookSettings implements IXposedHookLoadPackage {

    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam lpparam) {
        if (!lpparam.packageName.equals("com.android.settings")) {
            return;
        }
        findAndHookMethod("com.android.settings.display.DcLightPreferenceController", lpparam.classLoader, "isAvailable", XC_MethodReplacement.returnConstant(true));
    }
}
