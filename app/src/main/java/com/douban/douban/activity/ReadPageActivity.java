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
    BufferedReader reader;
    CharBuffer buffer = CharBuffer.allocate(8 * 1024);
    InputStream inputStream;

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


    private void loadBook(NetCallBack netCallBack) {
        File file = new File(bookPathExtra);
        if (!file.exists()) {
            Toast.makeText(this, "file is not exits", Toast.LENGTH_SHORT).show();
        } else {
            try {
                inputStream = new FileInputStream(file);
                readBook(netCallBack);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    void readBook(NetCallBack netCallBack) {
        try {
            Charset charset = CharsetDetector.detect(inputStream);
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, charset);
            reader = new BufferedReader(inputStreamReader);
            reader.read(buffer);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            netCallBack.success();
        }
    }

    @Background(id = "load_net_book")
    void loadNetBook(NetCallBack netCallBack) {
        HttpURLConnection httpConn = null;
        StringBuffer sb = new StringBuffer();
        URL url = null;
        try {
            url = new URL(netBookPathExtra);
            Log.e("999","url="+netBookPathExtra);
            httpConn = (HttpURLConnection) url.openConnection();
            HttpURLConnection.setFollowRedirects(true);
            httpConn.setRequestMethod("GET");
            inputStream = httpConn.getInputStream();
            readBook(netCallBack);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * 从指定位置开始载入一页
     */
    private void loadPage(int position) {
        buffer.position(position);
        Log.e("999", "buff=" + buffer);
        readView.setText(buffer);
    }

    /**
     * 上一页按钮
     */
    public void previewPageBtn() {
        int hang = position - readView.getCharNum();
        if (hang < 0) {
            Toast.makeText(this, "已经是第一页哦", Toast.LENGTH_SHORT).show();
            return;
        }
        position -= readView.getCharNum();
        loadPage(position);
//        readView.resize();
    }

    /**
     * 下一页按钮
     */
    public void nextPageBtn() {
        position += readView.getCharNum();
        loadPage(position);
//        readView.resize();
        if (position == 8000) {
        }
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
