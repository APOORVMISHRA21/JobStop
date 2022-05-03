package com.resume.maker.models;


public class SkillModel {
    String skill;

    public SkillModel() {
    }

    public SkillModel(String str) {
        this.skill = str;
    }

    public String getSkill() {
        return this.skill;
    }

    public void setSkill(String str) {
        this.skill = str;
    }
}
