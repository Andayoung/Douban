package com.douban.douban.activity;

import android.app.Activity;

import com.douban.douban.R;

import org.androidannotations.annotations.EActivity;

/**
 * 作者：Andayoung on 2016/10/26 15:11
 * 邮箱：zhoup324@163.com
 */
@EActivity(R.layout.sign_in_content)
public class SignInActivity extends Activity {
    @Override
    protected void onPause() {
        super.onPause();
        overridePendingTransition(R.anim.back_enter, R.anim.back_exit);

    }
}
