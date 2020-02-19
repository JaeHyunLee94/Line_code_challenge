package com.lee.line.memo;

public class Memo {

    String title;
    String content;

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
}
