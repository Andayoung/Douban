package com.douban.douban.adapter;

/**
 * 作者：Andayoung on 2016/10/28 15:19
 * 邮箱：zhoup324@163.com
 */
public class BookInfo {
    private String name;
    private String txtPath;
    private String imgPath;
    private int pageNum;

    public  BookInfo(String name,String txtPath,String imgPath,int pageNum){
        this.name=name;
        this.txtPath=txtPath;
        this.imgPath=imgPath;
        this.pageNum=pageNum;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public String getTxtPath() {
        return txtPath;
    }

    public void setTxtPath(String txtPath) {
        this.txtPath = txtPath;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
