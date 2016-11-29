package com.douban.douban.fragment;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.widget.GridView;
import android.widget.Toast;

import com.douban.douban.R;
import com.douban.douban.activity.ReadPageActivity_;
import com.douban.douban.adapter.BookCaseAdapter;
import com.douban.douban.adapter.BookInfo;
import com.douban.douban.util.GetFile;
import com.douban.douban.db.BookDBOpenHelper;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentArg;
import org.androidannotations.annotations.ItemClick;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：Andayoung on 2016/10/24 16:49
 * 邮箱：zhoup324@163.com
 */
@EFragment(R.layout.frag_today)
public class TodayFragment extends Fragment {
    private static final int FILE_SELECT_CODE = 0X111;
    public static final String BOOKNAME = "bookName";
    public static final String BOOKPATH = "bookPath";
    public static final String PAGENUM = "pageNum";
    @ViewById(R.id.book_grid)
    GridView gridBookCase;
    @Bean
    BookCaseAdapter adapter;
    List<BookInfo> bookCaseInfo;

    BookDBOpenHelper bookDBOpenHelper;
    SQLiteDatabase db;

    @AfterViews
    void initT() {
        bookDBOpenHelper = new BookDBOpenHelper(getActivity());
        initData();
    }

    @ItemClick(R.id.book_grid)
    void ItemClick(BookInfo bookInfo) {
        if (bookInfo != null) {
            openSystemBook(bookInfo.getTxtPath(),bookInfo.getName());
        } else {
            openSystemFile();
        }
    }

    public void openSystemFile() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("text/plain");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        try {
            startActivityForResult(Intent.createChooser(intent, "请选择文件!"),
                    FILE_SELECT_CODE);
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(getActivity(), "请安装文件管理器", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        if (resultCode == Activity.RESULT_OK) {
            String path=GetFile.getPath(getActivity(), data.getData());
            openSystemBook(path,GetFile.getFileName(path));
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void openSystemBook(String path,String name) {
        Intent intent = new Intent(getActivity(), ReadPageActivity_.class);
        intent.putExtra(BOOKPATH, path);
        intent.putExtra(BOOKNAME,name);
        startActivity(intent);
        if(!queryBookExits(path)){
            addBookToDB(GetFile.getFileName(path), path, null, 0);
            initData();
        }

    }

    private void initData() {
        db = bookDBOpenHelper.getWritableDatabase();
        bookCaseInfo = new ArrayList<BookInfo>();
        Cursor cursor = db.query("bookInfo", null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                bookCaseInfo.add(new BookInfo(cursor.getString(cursor.getColumnIndex("bookName"))
                        , cursor.getString(cursor.getColumnIndex("txtPath"))
                        , cursor.getString(cursor.getColumnIndex("imgPath"))
                        , cursor.getInt(cursor.getColumnIndex("pageNum"))));
            } while (cursor.moveToNext());
        }
        gridBookCase.setAdapter(adapter);
        adapter.getData(bookCaseInfo);
        adapter.notifyDataSetChanged();
        cursor.close();
        db.close();
    }

    private void addBookToDB(String bookName, String txtPath, String imgPath, int pageNum) {
        db = bookDBOpenHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("bookName", bookName);
        values.put("txtPath", txtPath);
        values.put("imgPath", imgPath);
        values.put("pageNum", pageNum);
        db.insert("bookInfo", null, values);
        db.close();
    }

    private boolean queryBookExits(String txtPath) {
        db = bookDBOpenHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM bookInfo WHERE txtPath = ?",
                new String[]{txtPath});
        if (cursor.moveToFirst()) {
            return true;
        }
        cursor.close();
        return false;
    }
}
