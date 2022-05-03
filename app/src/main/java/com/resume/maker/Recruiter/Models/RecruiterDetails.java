package com.resume.maker.Recruiter.Models;

public class RecruiterDetails {

    public String companyName;
    public String companyMail;
    public String companyDescription;

    public RecruiterDetails( String companyName, String companyMail, String companyDescription){
        this.companyName = companyName;
        this.companyMail = companyMail;
        this.companyDescription = companyDescription;
    }
    public RecruiterDetails(){}
}
