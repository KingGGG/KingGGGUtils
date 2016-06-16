package com.KinFourGUtils.activity;

import android.app.Activity;
import android.os.Build;
import android.view.View;
import android.view.WindowManager;

import com.KinFourGUtils.utils.SystemStatusManager;

/**
 * 作者：KingGGG on 15/11/12 15:01
 */
public class BaseActivity extends Activity {

    public void onTitleLeftClickEvent(View view) {
        finish();
    }

    public void setImmerseStatusColor(int res){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // 透明状态栏
            getWindow().addFlags(
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            // 透明导航栏
            getWindow().addFlags(
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            SystemStatusManager tintManager = new SystemStatusManager(this);
            tintManager.setStatusBarTintEnabled(true);
            // 设置状态栏的颜色
            tintManager.setStatusBarTintResource(res);
            getWindow().getDecorView().setFitsSystemWindows(true);
        }
    }
}
