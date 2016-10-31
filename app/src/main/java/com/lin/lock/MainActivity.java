package com.lin.lock;

import android.app.Activity;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

/**
 *  Created by Lin on 2016/10/28.
 */
public class MainActivity extends Activity {

    private DevicePolicyManager mDevicePolicyManager;
    private ComponentName mComponentName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //获取设备管理器
        mDevicePolicyManager = (DevicePolicyManager) getSystemService(Context.DEVICE_POLICY_SERVICE);
        mComponentName = new ComponentName(this, LockReceiver.class);
        // 判断该组件是否有系统管理员的权限
        if (mDevicePolicyManager.isAdminActive(mComponentName)) {
            mDevicePolicyManager.lockNow(); //锁屏
        } else {
            activeManager();    //激活权限
        }
        //结束进程
        android.os.Process.killProcess(android.os.Process.myPid());
    }

    /**
     * 激活设备管理器获取权限
     */
    private void activeManager() {
        Intent intent = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
        intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, mComponentName);
        intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION, "一键锁屏");
        startActivity(intent);
    }

}
