package com.douban.douban.activity;



import android.content.Context;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import com.douban.douban.interfac.NetCallBack;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Administrator on 2016/12/6 0006.
 */
public class ReadHelp {
    private BufferedReader in;
    private BufferedReader inzp;
    public boolean isEnd = false;//是否读取到文件末尾
    private int pageLines = 20;//一页数据20行
    private String path;
    int currentPage;
    int totalLines;
    int pageCount;
    NetCallBack netCallBack;

    Handler handler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message message) {
            super.handleMessage(message);
            BufferedReader in = (BufferedReader) message.obj;
            Log.e("999","in="+in);
            netCallBack.success();

        }
    };

    public ReadHelp(String path,NetCallBack netCallBack) {
        this.netCallBack=netCallBack;
        this.path = path;
        initData();
    }

    private void initData() {
        currentPage = 1;
        setTotalLines();

    }

    //获取总页数
    public int getPageCount() {
        return pageCount;
    }

    //获取总行数;
    public int getTotalLines() {
        return totalLines;
    }

    private void setTotalLines() {
        int total = 0;
        try {
            if (path.startsWith("http")) {
                new Thread(netWorkTask).start();
            } else {
                in = new BufferedReader(new FileReader(path));
                while (in.readLine() != null) {
                    total++;
                }
                in.close();
                totalLines=total;
                if (totalLines % 20 == 0) {
                    pageCount = totalLines / 20;
                } else {
                    pageCount = totalLines / 20 + 1;
                }
                netCallBack.success();
            }

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    //获取第page 页的数据
    public String seek(int page) {
        String str = null;
        if (page > pageCount) {
            return null;
        } else {
            str = read(page);
        }
        return str;
    }

    private String read(int page) {
        String sr=null;
        StringBuffer sb = new StringBuffer();
        try {
            if (path.startsWith("http")) {
                in=new BufferedReader(new InputStreamReader(new FileInputStream(
                        Environment.getExternalStorageDirectory().getPath()+"/bfWriter.txt")));
            } else {
                in = new BufferedReader(new FileReader(path));
            }
            for (int i = 0; i < 20 * (page - 1); i++) {
                //走过前n-1数据
                in.readLine();
            }
            for (int k = 0; k < 20; k++) {
                sb.append(in.readLine() + "\n");
            }
            in.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


        try {
            sr=new String(sb.toString().getBytes("GBK"),"GBK");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return sr;
    }

    Runnable netWorkTask = new Runnable() {
        @Override
        public void run() {
            URL url = null;
            HttpURLConnection httpConn = null;
            try {
                url = new URL(path);
                httpConn = (HttpURLConnection) url.openConnection();
//                HttpURLConnection.setFollowRedirects(true);
                httpConn.setRequestMethod("GET");
                httpConn.setRequestProperty("contentType", "GBK");
                httpConn.setRequestProperty("Accept-Encoding", "identity");
                FileWriter fWriter = new FileWriter(Environment.getExternalStorageDirectory().getPath()+"/bfWriter.txt");
                BufferedWriter bw=new BufferedWriter(fWriter);
                in = new BufferedReader(new InputStreamReader(httpConn.getInputStream(),"GBK"));
                StringBuffer sb = new StringBuffer();
                int total = 0;
                String input=null;
                try {
                    while ((input=in.readLine()) != null) {
                        sb.append(input + "\n");
                        bw.write(input);
                        bw.newLine();
                        total++;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                in.close();
                bw.close();
                Log.e("888","sb="+sb.toString());
                totalLines=total;
                if (totalLines % 20 == 0) {
                    pageCount = totalLines / 20;
                } else {
                    pageCount = totalLines / 20 + 1;
                }
                Message message = Message.obtain();
                message.obj = in;
                handler.sendMessage(message);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    };

}
