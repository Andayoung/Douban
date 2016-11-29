package com.douban.douban.view;

import android.content.Context;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.douban.douban.R;
import com.douban.douban.adapter.BookInfo;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

/**
 * 作者：Andayoung on 2016/10/24 17:01
 * 邮箱：zhoup324@163.com
 */
@EViewGroup(R.layout.bookcase_item)
public class BookCaseItemView extends RelativeLayout {
    @ViewById(R.id.txt_case_name)
    TextView txtName;

    @ViewById(R.id.img_case_bookface)
    ImageView imgCover;
    public BookCaseItemView(Context context) {
        super(context);
    }

    public void bind(BookInfo item) {
        txtName.setText(item.getName());
    }
    public void bind(){
        txtName.setVisibility(INVISIBLE);
        imgCover.setImageResource(R.drawable.book_add);
        imgCover.setAdjustViewBounds(true);
    }
}
