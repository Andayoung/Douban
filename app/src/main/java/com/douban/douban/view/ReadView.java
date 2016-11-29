package com.douban.douban.view;

import android.content.Context;
import android.text.Layout;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.TextView;

import com.douban.douban.interfac.PreOrNextCallBack;


public class ReadView extends TextView implements GestureDetector.OnGestureListener {
    private GestureDetector gestureDetector;

    private PreOrNextCallBack preOrNextCallBack;

    public ReadView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        gestureDetector = new GestureDetector(context, this);
    }

    public ReadView(Context context, AttributeSet attrs) {
        super(context, attrs);
        gestureDetector = new GestureDetector(context, this);

    }

    public ReadView(Context context) {
        super(context);
        gestureDetector = new GestureDetector(context, this);

    }

    public void setPreOrNextCallBack(PreOrNextCallBack preOrNextCallBack){
        this.preOrNextCallBack=preOrNextCallBack;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return gestureDetector.onTouchEvent(event);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        resize();
    }

    /**
     * 取出当前页无法显示的字
     *
     * @return 去掉的字数
     */
    public int resize() {
        CharSequence oldContent = getText();
        CharSequence newContent = oldContent.subSequence(0, getCharNum());
        setText(newContent);
        return oldContent.length() - newContent.length();
    }

    /**
     * 获取当前页总字数
     */
    public int getCharNum() {
        return getLayout().getLineEnd(getLineNum());
    }

    /**
     * 获取当前页总行数
     */
    public int getLineNum() {
        Layout layout = getLayout();
        int topOfLastLine = getHeight() - getPaddingTop() - getPaddingBottom() - getLineHeight();
        return layout.getLineForVertical(topOfLastLine);
    }


    @Override
    public boolean onDown(MotionEvent e) {
        return true;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        Log.e("8888", "getX1=" + e1.getX() + ",getX2=" + e2.getX());
        if (e1.getX() - e2.getX() > 50) {
            Log.e("8888", "向左滑");
            preOrNextCallBack.nextview();
        } else if (e1.getX() - e2.getX() < -50) {
            Log.e("8888", "向右滑");
            preOrNextCallBack.preview();
        }
        return true;
    }
}
