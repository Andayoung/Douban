package com.douban.douban.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.FindCallback;
import com.douban.douban.R;
import com.douban.douban.adapter.BookStoreAdapter;
import com.douban.douban.adapter.BookItem;
import com.douban.douban.fragment.TodayFragment;
import com.douban.douban.util.GetFile;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.ItemClick;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 作者：Andayoung on 2016/10/26 15:11
 * 邮箱：zhoup324@163.com
 */
@EActivity(R.layout.douban_store)
public class DouBanStore extends Activity {
    public static final String NETBOOKPATH = "netBookPath";
    public static final String BOOKNAME = "bookName";
    @ViewById(R.id.gdv_bookstore)
    GridView gdvbookstore;
    @Bean
    BookStoreAdapter bsadapter;
    List<BookItem> bookstore;
    @ViewById(R.id.img_left)
    ImageView topButton;
    @ViewById(R.id.txt_title)
    TextView topTextView;
    @ViewById(R.id.txt_doub)
    TextView txtDoub;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bookstore = new ArrayList<BookItem>();
        AVQuery<AVObject> query = new AVQuery<>("DoubanList");
        query.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> list, AVException e) {
                List<AVObject> priorityEqualsZeroTodos = list;
                for (AVObject avObject : list) {
                    AVFile imgFile = (AVFile) avObject.get("imgName");
                    String name = imgFile.getOriginalName().toString();
                    AVFile txtFile=(AVFile)avObject.get("txtName");
                    bookstore.add(new BookItem(name.substring(0, name.length() - 4)
                            , imgFile.getThumbnailUrl(true, 130, 176)
                            , txtFile.getUrl()));
                }

                bsadapter.notifyDataSetChanged();
            }
        });
    }

    @AfterViews
    void initView() {
        topButton.setImageResource(R.drawable.icon_left);
        topButton.setAdjustViewBounds(true);
        topTextView.setText(getResources().getString(R.string.douban_list));
        txtDoub.setVisibility(View.GONE);
        gdvbookstore.setAdapter(bsadapter);
        bsadapter.getData(bookstore);
    }

    @Override
    protected void onPause() {
        super.onPause();
        overridePendingTransition(R.anim.back_enter, R.anim.back_exit);
    }

    @Click(R.id.img_left)
    void back() {
        finish();
    }

    @ItemClick(R.id.gdv_bookstore)
    void itemClick(BookItem item) {
        openSystemBook(item.getTxtPath(), item.getName());
    }

    private void openSystemBook(String path, String name) {
        Intent intent = new Intent(this, ReadPageActivity_.class);
        intent.putExtra(NETBOOKPATH, path);
        intent.putExtra(TodayFragment.BOOKNAME, name);
        startActivity(intent);
    }
}
