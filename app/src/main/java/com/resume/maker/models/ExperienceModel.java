package com.resume.maker.models;


public class ExperienceModel {
    String designation;
    String empradio;
    String fromtime;
    String organization;
    String role;
    String totime;

    public ExperienceModel() {
    }

    public ExperienceModel(String str, String str2, String str3, String str4, String str5, String str6) {
        this.organization = str;
        this.designation = str2;
        this.fromtime = str3;
        this.totime = str4;
        this.empradio = str5;
        this.role = str6;
    }

    public String getOrganization() {
        return this.organization;
    }

    public void setOrganization(String str) {
        this.organization = str;
    }

    public String getDesignation() {
        return this.designation;
    }

    public void setDesignation(String str) {
        this.designation = str;
    }

    public String getFromtime() {
        return this.fromtime;
    }

    public void setFromtime(String str) {
        this.fromtime = str;
    }

    public String getTotime() {
        return this.totime;
    }

    public void setTotime(String str) {
        this.totime = str;
    }

    public String getEmpradio() {
        return this.empradio;
    }

    public void setEmpradio(String str) {
        this.empradio = str;
    }

    public String getRole() {
        return this.role;
    }

    public void setRole(String str) {
        this.role = str;
    }
}
