package com.lee.line.data;

import android.content.Context;
import android.net.Uri;
import android.widget.Toast;

import java.util.ArrayList;

public class Memo {

    String title;
    String content;
    ArrayList<String> imglist;

    public Memo(String title, String content){
        this.title=title;
        this.content=content;
        this.imglist= new ArrayList<>();
        imglist.add("https://t1.daumcdn.net/cfile/tistory/2542444858F57E0B33");
    }



    public void setContent(String content) {
        this.content = content;
    }


    public void setTitle(String title) {
        this.title = title;
    }


    public boolean isImgEmpty(){
        return this.imglist.isEmpty();
    }

    public String getImgThumbnail(){
        return isImgEmpty() ? "":imglist.get(0);
    }


    public void addImg(String url){
        imglist.add(url);
    }
    public void addImg(Uri uri){
        imglist.add(uri.toString());
    }



    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public void save(Context context) {
        Toast.makeText(context,"MEMO SAVED",Toast.LENGTH_SHORT).show();

    }
}
