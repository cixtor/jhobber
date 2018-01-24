package com.cixtor.jhobber;

import java.util.ArrayList;

public class JobPost {
    private String company;
    private String title;
    private String skills;
    private int logoId;

    public JobPost(String company, String title, String skills, int logoId) {
        this.company = company;
        this.title = title;
        this.skills = skills;
        this.logoId = logoId;
    }

    public String getCompany() {
        return company;
    }

    public String getTitle() {
        return title;
    }

    public String getSkills() {
        return skills;
    }

    public int getLogoId() {
        return logoId;
    }

    public static ArrayList<JobPost> getJobPosts() {
        ArrayList<JobPost> posts = new ArrayList<JobPost>();

        return posts;
    }
}
