package com.resume.maker.models;


public class LanguageModel {
    String language;

    public LanguageModel() {
    }

    public LanguageModel(String str) {
        this.language = str;
    }

    public String getLanguage() {
        return this.language;
    }

    public void setLanguage(String str) {
        this.language = str;
    }
}
