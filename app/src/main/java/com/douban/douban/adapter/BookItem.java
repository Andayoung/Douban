package com.douban.douban.adapter;

/**
 * 作者：Andayoung on 2016/10/24 17:05
 * 邮箱：zhoup324@163.com
 */
public class BookItem {
    private String name;
    private String imgFace;
    private String txtPath;
    public BookItem(String name,String imgFace,String txtPath){
        setName(name);
        setImgFace(imgFace);
        setTxtPath(txtPath);
    }

    public String getTxtPath() {
        return txtPath;
    }

    public void setTxtPath(String txtPath) {
        this.txtPath = txtPath;
    }

    public String getImgFace() {
        return imgFace;
    }

    public void setImgFace(String imgFace) {
        this.imgFace = imgFace;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
