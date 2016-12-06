package com.douban.douban.activity;

import android.app.Activity;
import android.support.annotation.UiThread;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.douban.douban.R;
import com.douban.douban.fragment.TodayFragment;
import com.douban.douban.interfac.NetCallBack;
import com.douban.douban.interfac.PreOrNextCallBack;
import com.douban.douban.util.CharsetDetector;
import com.douban.douban.view.ReadView;


import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.ViewById;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.nio.CharBuffer;
import java.nio.charset.Charset;

/**
 * 作者：Andayoung on 2016/10/28 20:23
 * 邮箱：zhoup324@163.com
 */
@EActivity(R.layout.read_page)
public class ReadPageActivity extends Activity implements NetCallBack {
    private int position = 0;
    @ViewById(R.id.read_view_page)
    ReadView readView;
    @ViewById(R.id.book_title)
    TextView txtTitle;
    @Extra(TodayFragment.BOOKNAME)
    String bookNameExtra;
    @Extra(TodayFragment.BOOKPATH)
    String bookPathExtra;
    @Extra(DouBanStore.NETBOOKPATH)
    String netBookPathExtra;
    //    @Extra(TodayFragment.PAGENUM)
//    String pageNumExtra;
    ReadHelp r;

    @AfterViews
    void initView() {
        txtTitle.setText(bookNameExtra);
        if (bookPathExtra != null) {
            loadBook(this);
        } else if (netBookPathExtra != null) {
            loadNetBook(this);
        }
        readView.setPreOrNextCallBack(new PreOrNextCallBack() {
            @Override
            public void preview() {
                previewPageBtn();
            }

            @Override
            public void nextview() {
                nextPageBtn();
            }
        });
    }

    @Background(id = "load_sdcard_book")
    void loadBook(NetCallBack netCallBack) {
        r = new ReadHelp(bookPathExtra,netCallBack);
//        netCallBack.success();
    }

    @Background(id = "load_net_book")
    void loadNetBook(NetCallBack netCallBack) {
        r = new ReadHelp(netBookPathExtra,netCallBack);
//        netCallBack.success();
    }



    /**
     * 从指定位置开始载入一页
     */
    private void loadPage(int position) {
//        buffer.position(position);
        String context = r.seek(position);
        Log.e("999", "context=" + context);
        readView.setText(context);
    }

    /**
     * 上一页按钮
     */
    public void previewPageBtn() {
        position -= 1;
        loadPage(position);
        readView.resize();
//        int hang = position - readView.getCharNum();
//        if (hang < 0) {
//            Toast.makeText(this, "已经是第一页哦", Toast.LENGTH_SHORT).show();
//            return;
//        }
//        position -= readView.getCharNum();
//        loadPage(position);
    }

    /**
     * 下一页按钮
     */
    public void nextPageBtn() {
        position += 1;
        loadPage(position);
        readView.resize();
//        position += readView.getCharNum();
//        loadPage(position);
//        readView.resize();
//        if (position == 8000) {
//        }
    }

    @Override
    public void success() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                loadPage(0);
            }
        });

    }
}
