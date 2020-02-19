package com.lee.line.data;

import android.content.Context;
import android.net.Uri;
import android.widget.Toast;

import java.util.List;

public class Memo {

    String title;
    String content;
    List<Uri> imglist;

    public Memo(String title, String content){
        this.title=title;
        this.content=content;
    }



    public void setContent(String content) {
        this.content = content;
    }


    public void setTitle(String title) {
        this.title = title;
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
