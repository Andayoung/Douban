package com.douban.douban.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.douban.douban.view.ItemView;
import com.douban.douban.view.ItemView_;

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
public class BookStoreAdapter extends BaseAdapter {
    @RootContext
    Context context;

    private List<BookItem> bookTitle;

    public void getData(List<BookItem> data) {
        bookTitle = data;
    }

    @AfterInject
    void init() {
        bookTitle = new ArrayList<>();
    }

    @Override
    public int getCount() {
        return bookTitle == null ? 0 : bookTitle.size();
    }

    @Override
    public BookItem getItem(int position) {
        return bookTitle.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ItemView view = null;

        if (convertView == null) {
            view = ItemView_.build(context);
        } else {
            view = (ItemView) convertView;
        }
        view.bind(getItem(position));

        return view;
    }
}
