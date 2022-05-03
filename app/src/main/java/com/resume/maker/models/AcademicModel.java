package com.resume.maker.models;


public class AcademicModel {
    String degree;
    String institute;
    String percgpa;
    String perradio;
    String year;
    String yearradio;

    public AcademicModel() {
    }

    public AcademicModel(String str, String str2, String str3, String str4, String str5, String str6) {
        this.degree = str;
        this.institute = str2;
        this.percgpa = str3;
        this.year = str4;
        this.perradio = str5;
        this.yearradio = str6;
    }

    public String getDegree() {
        return this.degree;
    }

    public void setDegree(String str) {
        this.degree = str;
    }

    public String getInstitute() {
        return this.institute;
    }

    public void setInstitute(String str) {
        this.institute = str;
    }

    public String getPercgpa() {
        return this.percgpa;
    }

    public void setPercgpa(String str) {
        this.percgpa = str;
    }

    public String getYear() {
        return this.year;
    }

    public void setYear(String str) {
        this.year = str;
    }

    public String getPerradio() {
        return this.perradio;
    }

    public void setPerradio(String str) {
        this.perradio = str;
    }

    public String getYearradio() {
        return this.yearradio;
    }

    public void setYearradio(String str) {
        this.yearradio = str;
    }
}
