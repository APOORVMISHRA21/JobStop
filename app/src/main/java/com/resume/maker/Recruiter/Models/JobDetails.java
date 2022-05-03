package com.resume.maker.Recruiter.Models;

public class JobDetails {

    public String creatorId;
    public String jobTitle;
    public String jobDescription;
    public String jobSalary;
    public String jobType;
    public String jobMode;
    public String jobLocation;
    public String jobDeadline;
    public String createdAt;
    public String jobTag;

    public JobDetails(){}

    public JobDetails(String creatorId, String jobTitle, String jobDescription, String jobSalary, String jobType, String jobMode,String jobLocation, String jobDeadline, String createdAt, String jobTag){
        this.creatorId = creatorId;
        this.jobTitle = jobTitle;
        this.jobDescription = jobDescription;
        this.jobSalary = jobSalary;
        this.jobType = jobType;
        this.jobMode = jobMode;
        this.jobLocation = jobLocation;
        this.jobDeadline = jobDeadline;
        this.createdAt = createdAt;
        this.jobTag = jobTag;

    }

}
