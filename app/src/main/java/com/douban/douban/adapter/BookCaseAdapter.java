package com.douban.douban.adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.douban.douban.view.BookCaseItemView;
import com.douban.douban.view.BookCaseItemView_;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：Andayoung on 2016/10/24 16:49
 * 邮箱：zhoup324@163.com
 */
@EBean
public class BookCaseAdapter extends BaseAdapter {
    @RootContext
    Context context;

    private List<BookInfo> bookTitle;

    public void getData(List<BookInfo> data) {
        bookTitle = data;
    }

    @AfterInject
    void init() {
        bookTitle = new ArrayList<>();
    }

    @Override
    public int getCount() {
        return bookTitle == null ? 1 : bookTitle.size()+1;
    }

    @Override
    public BookInfo getItem(int position) {
        return position==getCount()-1?null:bookTitle.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        BookCaseItemView view = null;
        if (convertView == null) {
            view = BookCaseItemView_.build(context);
        } else {
            view = (BookCaseItemView) convertView;
        }

        if(getItem(position)==null){
            view.bind();
        }else {
            view.bind(getItem(position));
        }
        return view;
    }
}
