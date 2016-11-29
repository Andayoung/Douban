package com.douban.douban.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.douban.douban.R;
import com.douban.douban.fragment.LeftFragment_;
import com.douban.douban.fragment.TodayFragment_;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.NoTitle;
import org.androidannotations.annotations.ViewById;

/**
 * 作者：Andayoung on 2016/10/24 16:49
 * 邮箱：zhoup324@163.com
 */
@NoTitle
@EActivity(R.layout.activity_main)
public class MainActivity extends SlidingFragmentActivity {

    @ViewById(R.id.img_left)
    ImageView topButton;
    @ViewById(R.id.txt_title)
    TextView topTextView;
    @ViewById(R.id.content_frame)
    FrameLayout frameLayout;
    @ViewById(R.id.txt_doub)
    TextView txtDoub;
    public Fragment mContent;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initSlidingMenu(savedInstanceState);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        getSupportFragmentManager().putFragment(outState, "mContent", mContent);
    }

    @AfterViews
    void initM() {
        topButton.setImageResource(R.drawable.ic_top_bar_category);
        topButton.setAdjustViewBounds(true);
        txtDoub.setVisibility(View.VISIBLE);
        topTextView.setText(getResources().getString(R.string.book_case));
    }

    /**
     * 初始化侧边栏
     */
    private void initSlidingMenu(Bundle savedInstanceState) {
        // 如果保存的状态不为空则得到之前保存的Fragment，否则实例化MyFragment
        if (savedInstanceState != null) {
            mContent = getSupportFragmentManager().getFragment(
                    savedInstanceState, "mContent");
        }

        if (mContent == null) {
            mContent = TodayFragment_.builder()
                    .build();
        }

        switchConent(mContent, "6");
        // 设置左侧滑动菜单
        setBehindContentView(R.layout.menu_frame_left);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.menu_frame, new LeftFragment_()).commit();

        // 实例化滑动菜单对象
        SlidingMenu sm = getSlidingMenu();
        // 设置可以左右滑动的菜单
        sm.setMode(SlidingMenu.LEFT);
        // 设置滑动阴影的宽度
        sm.setShadowWidthRes(R.dimen.shadow_width);
        // 设置滑动菜单阴影的图像资源
        sm.setShadowDrawable(null);
        // 设置滑动菜单视图的宽度
        sm.setBehindOffsetRes(R.dimen.slidingmenu_offset);
        // 设置渐入渐出效果的值
        sm.setFadeDegree(0.35f);
        // 设置触摸屏幕的模式,这里设置为全屏
        sm.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        // 设置下方视图的在滚动时的缩放比例
        sm.setBehindScrollScale(0.0f);

    }

    /**
     * 切换Fragment
     *
     * @param fragment
     */
    public void switchConent(Fragment fragment, String title) {
        mContent = fragment;
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.content_frame, fragment).commit();
        getSlidingMenu().showContent();
//        topTextView.setText(title);
    }

    @Click(R.id.img_left)
    void btnnClick() {
        toggle();
    }

    @Click(R.id.txt_doub)
    void toDouban() {
        Intent intent = new Intent(this, DouBanStore_.class);
        startActivity(intent);
        overridePendingTransition(R.anim.anim_enter, R.anim.anim_exit);

    }

}
