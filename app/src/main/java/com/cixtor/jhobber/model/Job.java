package com.cixtor.jhobber.model;

public class Job {
    private int mID;
    private String mCompany;
    private String mImage;
    private String mOccupation;
    private String mDescription;
    private String mSkills;

    public Job() {
    }

    public int getID() {
        return mID;
    }

    public void setID(int ID) {
        mID = ID;
    }

    public String getCompany() {
        return mCompany;
    }

    public void setCompany(String company) {
        mCompany = company;
    }

    public String getImage() {
        return mImage;
    }

    public void setImage(String image) {
        mImage = image;
    }

    public String getOccupation() {
        return mOccupation;
    }

    public void setOccupation(String occupation) {
        mOccupation = occupation;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public String getSkills() {
        return mSkills;
    }

    public void setSkills(String skills) {
        mSkills = skills;
    }

    @Override
    public String toString() {
        return "Job{company='" + mCompany + "', jobtitle='" + mOccupation + "'}";
    }
}
