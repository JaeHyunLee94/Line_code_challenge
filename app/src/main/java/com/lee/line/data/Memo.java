package com.lee.line.data;

import java.util.ArrayList;

/*
메모 데이터 클래스
제목,내용,이미지 uri 리스트 를 가지고 있음
 */

public class Memo {

    String title;
    String content;
    ArrayList<String> imglist;

    public Memo(String title, String content){
        this.title=title;
        this.content=content;
        this.imglist= new ArrayList<>();
    }



    public void setContent(String content) {
        this.content = content;
    }

    public void setImglist(ArrayList<String> imglist) {
        this.imglist = imglist;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public boolean isImgEmpty(){
        return this.imglist.isEmpty();
    }

    public ArrayList<String> getImglist() {
        return imglist;
    }

    public String getImgThumbnail(){
        return isImgEmpty() ? "":imglist.get(0);
    }




    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }


}
