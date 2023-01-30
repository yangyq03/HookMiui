package com.apwy.hookMiui;

import de.robv.android.xposed.IXposedHookInitPackageResources;
import de.robv.android.xposed.callbacks.XC_InitPackageResources;

public class HookNewHD implements IXposedHookInitPackageResources {
    @Override
    public void handleInitPackageResources(XC_InitPackageResources.InitPackageResourcesParam resparam) {
        if (!resparam.packageName.equals("com.android.systemui")) {
            return;
        }
        resparam.res.setReplacement("com.android.systemui", "bool", "statusbar_use_new_hd", true);
    }
}
