package com.cixtor.jhobber.model;

import android.util.Log;

import com.cixtor.jhobber.R;

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

        posts.add(new JobPost(
                "Google Inc.",
                "Senior Software Engineer",
                "C++, Java, Go, Python, Node.js",
                R.mipmap.job_google_logo
        ));
        posts.add(new JobPost(
                "Adobe Systems Inc.",
                "Senior Software Engineer",
                "C++, Java, C#",
                R.mipmap.job_adobe_logo
        ));
        posts.add(new JobPost(
                "Slack Technologies",
                "Senior Software Engineer",
                "Node.js, JavaScript, Electron",
                R.mipmap.job_slack_logo
        ));

        for (JobPost post : posts) {
            Log.d("JOB_POST", post.toString());
        }

        return posts;
    }

    @Override
    public String toString() {
        return "JobPost{company='" + company + "', title='" + title + "'}";
    }
}
