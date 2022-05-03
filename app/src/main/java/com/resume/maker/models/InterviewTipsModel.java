package com.resume.maker.models;


public class InterviewTipsModel {
    String desc;
    String title;

    public InterviewTipsModel() {
    }

    public InterviewTipsModel(String str, String str2) {
        this.title = str;
        this.desc = str2;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String str) {
        this.title = this.title;
    }

    public String getDesc() {
        return this.desc;
    }

    public void setDesc(String str) {
        this.desc = str;
    }
}
