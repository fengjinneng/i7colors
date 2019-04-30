package com.company.qcy;

import android.content.Context;
import android.support.annotation.Keep;
import android.support.multidex.MultiDex;
import android.util.Log;

import com.taobao.sophix.PatchStatus;
import com.taobao.sophix.SophixApplication;
import com.taobao.sophix.SophixEntry;
import com.taobao.sophix.SophixManager;
import com.taobao.sophix.listener.PatchLoadStatusListener;


/**
 * Sophix入口类，专门用于初始化Sophix，不应包含任何业务逻辑。
 * 此类必须继承自SophixApplication，onCreate方法不需要实现。
 * 此类不应与项目中的其他类有任何互相调用的逻辑，必须完全做到隔离。
 * AndroidManifest中设置application为此类，而SophixEntry中设为原先Application类。
 * 注意原先Application里不需要再重复初始化Sophix，并且需要避免混淆原先Application类。
 * 如有其它自定义改造，请咨询官方后妥善处理。
 */

public class SophixStubApplication extends SophixApplication {
    private final String TAG = "SophixStubApplication";

    // 此处SophixEntry应指定真正的Application，并且保证RealApplicationStub类名不被混淆。
    @Keep
    @SophixEntry(I7colorsApplication.class)
    static class RealApplicationStub {
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
//         如果需要使用MultiDex，需要在此处调用。
        MultiDex.install(this);
        initSophix();
    }

    private void initSophix() {
        String appVersion = "0.0.0";
        try {
            appVersion = this.getPackageManager()
                    .getPackageInfo(this.getPackageName(), 0)
                    .versionName;
        } catch (Exception e) {
        }
        final SophixManager instance = SophixManager.getInstance();
        instance.setContext(this)
                .setAppVersion(appVersion)
                .setSecretMetaData("25531084", "f74aaa6b7e94ab63acc9f1a3617cd563", "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCdncu/lzRMre+FNhoBz+Dq/lFzwEyEpfy40qVJIX18MDRjZkaIV+AMN64JLOvMxn3HGQ9+WV1V9W4TeABoFgE8NaD8Jo+6YL1rXfxY/rls4CE35QEHWWij2Gg0MJH3dSH3JWa7UuwlAJQxIJ0ZetuHJhz06GomggMqMiUZu32QezouXW+y4sJYb/1PuCZ/NhROS43WvuFANVBZaXmozYD3tZ8AWntBXbIwsPoB6Unl8qzbLATuoz3gNtF+tJhCBGSGSvhAbMlnPiv0zgFkoecYf+pqmF0w/bbOxVNPtculROOeESM2r43RLdXVmftnx48CKUuMR/cC4ZOcffbXDRN5AgMBAAECggEAViKQ4VAZhbocRllNlyXqjaGUE8TITEmFtd9v8mo58G9LgfK3ovUa/LJFCsCg0FS9EdwxKmRd7Ibms/8FgdxcqnHIW2QbcchCW/WN3Xi/Dq/hl3EvwbtrwvaV48eTTX+Wo0CuRrrHUGVjlwFyuST56X67nl+Q+yBb6Ghk/A4JTbYePQiTSeNwVG0Ct+LQLD30BpN4UHJ5lCiVSnRWxB5oXj4RZgSICiTz6vCn2/qUqCYQ4+oUIf8P/WLvreiAGfu6KegUsTDBpT9SE72cR+fNaIBI1BjyvdPGhWx1p8iAxUgwRE8UoRgdOlBsC3zVMEXMgfdVT94FUETUnDod+hpELQKBgQDTVSfKfUxnGCQUWvqkixOHrVrDDlQ01kkDB0jWk0YkT4g67LP5NK67xoGqW9hpYqnFuzFZAD44fqcNvNBr0aIiNhsgP8F6Be4l9XeGsYxQXGHkQltYChnQ7hHO1/ot7ZvzyCsU/Gq32qPZZLCy2+f6BYTqq6GQCvL/fukyGC8sPwKBgQC+7iWcRW3MS5ZyWG9ArAikxTK0AqxRsFXWM6UDdlv+FEVhs/vz8hJz8/+OzXC0HphX+3uGgwEHqkqaB4Y45sDaQBo9G17M/Ti57fwuMUdmXs/AuGXMunt60rS16FiUKYv6HiOxHlnrbMnMql+na0At89DOs+UduPOGl2ddLcyyRwKBgDx6WyfMkcfTFcgpfR8HmfAzAQ1fFWQFiuFt/T9C6EjN5duPD7YaiIK+PS9Gl3kZPiKS/n1M34GFZUM+LJseY/rMZxjkhfboqY19YNZQCyqYNX+AKenZAihTaQ87qaHpJL8wInwf/F5fGJLHCPY2jn1zhOUvds3lBENvy0aFu0I7AoGBAK+0i0oXnqauPQJ3ndJfPwkbLMD54MjARQ39svLPvnLBBuSV9NyXPiIVPm6VYw1vRnVZSmEMOCtsrIC5Qe0ldf6jlUH86S4uLkD04OlFklaEFB5f09cnGjF6rLDm5HVdEqMXj3MU4l1EXSWK+MIicgqX4OYIKWZD6WBH/uiCGGlLAoGBALR90V2wEjjptytSmmWXyH96wYEoXuCMhNL0VTO6Buwz84s+T9sSxMEhWTvf3dgeoHPCSmPkzxuNAXrqOF2/6CkokwjWsjtKpVNPzNXN9T77XgnIHFAIGwuAUJwgSq0mqKQBy5IinLjdabL05btcapt5Fe5aNoJzqsx8wWYRIOkV")
                .setEnableDebug(true)
                .setEnableFullLog()
                .setPatchLoadStatusStub(new PatchLoadStatusListener() {
                    @Override
                    public void onLoad(final int mode, final int code, final String info, final int handlePatchVersion) {
                        if (code == PatchStatus.CODE_LOAD_SUCCESS) {
                        } else if (code == PatchStatus.CODE_LOAD_RELAUNCH) {
                            // 如果需要在后台重启，建议此处用SharePreference保存状态。
                        }
                    }
                }).initialize();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        SophixManager.getInstance().queryAndLoadNewPatch();

    }
}
