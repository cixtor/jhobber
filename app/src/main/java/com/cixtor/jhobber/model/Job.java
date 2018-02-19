package com.cixtor.jhobber.model;

public class Job {
    private String mID;
    private String mCompany;
    private String mTitle;
    private String mSummary;
    private String mSkills;
    private String mSource;
    private String mImage;
    private String mContact;
    private String mKind;
    private boolean mActive;
    private boolean mRemote;
    private boolean mVisa;
    private String mCountry;
    private String mCity;
    private double mLongitude;
    private double mLatitude;
    private String mDatetime;

    public Job() {
    }

    public String getID() {
        return mID;
    }

    public void setID(String ID) {
        mID = ID;
    }

    public String getCompany() {
        return mCompany;
    }

    public void setCompany(String company) {
        mCompany = company;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getSummary() {
        return mSummary;
    }

    public void setSummary(String summary) {
        mSummary = summary;
    }

    public String getSkills() {
        return mSkills;
    }

    public void setSkills(String skills) {
        mSkills = skills;
    }

    public String getSource() {
        return mSource;
    }

    public void setSource(String source) {
        mSource = source;
    }

    public String getImage() {
        return mImage;
    }

    public void setImage(String image) {
        mImage = image;
    }

    public String getContact() {
        return mContact;
    }

    public void setContact(String contact) {
        mContact = contact;
    }

    public String getKind() {
        return mKind;
    }

    public void setKind(String kind) {
        mKind = kind;
    }

    public boolean isActive() {
        return mActive;
    }

    public void setActive(boolean active) {
        mActive = active;
    }

    public boolean isRemote() {
        return mRemote;
    }

    public void setRemote(boolean remote) {
        mRemote = remote;
    }

    public boolean isVisa() {
        return mVisa;
    }

    public void setVisa(boolean visa) {
        mVisa = visa;
    }

    public String getCountry() {
        return mCountry;
    }

    public void setCountry(String country) {
        mCountry = country;
    }

    public String getCity() {
        return mCity;
    }

    public void setCity(String city) {
        mCity = city;
    }

    public double getLongitude() {
        return mLongitude;
    }

    public void setLongitude(double longitude) {
        mLongitude = longitude;
    }

    public double getLatitude() {
        return mLatitude;
    }

    public void setLatitude(double latitude) {
        mLatitude = latitude;
    }

    public String getDatetime() {
        return mDatetime;
    }

    public void setDatetime(String datetime) {
        mDatetime = datetime;
    }

    @Override
    public String toString() {
        return "Job{company='" + mCompany + "', jobtitle='" + mTitle + "'}";
    }
}
