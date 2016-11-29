package com.douban.douban.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.douban.douban.R;
import com.douban.douban.adapter.BookItem;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * 作者：Andayoung on 2016/10/24 17:01
 * 邮箱：zhoup324@163.com
 */
@EViewGroup(R.layout.item)
public class ItemView extends RelativeLayout {
    @ViewById(R.id.txt_name)
    TextView txtName;

    @ViewById(R.id.img_bookface)
    ImageView imgFace;

    public ItemView(Context context) {
        super(context);
    }

    public void bind(BookItem bookItem) {
        txtName.setText(bookItem.getName());
        ImageLoader.getInstance().displayImage(bookItem.getImgFace(), imgFace);
    }


}

