package com.cixtor.jhobber.model;

public class User {
    private String mUUID;
    private String mFirstName;
    private String mLastName;
    private String mOccupation;
    private String mAvatar;

    public User() {
    }

    public String getUUID() {
        return mUUID;
    }

    public void setUUID(String uuid) {
        mUUID = uuid;
    }

    public String getFirstName() {
        return mFirstName;
    }

    public void setFirstName(String firstName) {
        mFirstName = firstName;
    }

    public String getLastName() {
        return mLastName;
    }

    public void setLastName(String lastName) {
        mLastName = lastName;
    }

    public String getOccupation() {
        return mOccupation;
    }

    public void setOccupation(String occupation) {
        mOccupation = occupation;
    }

    public String getAvatar() {
        return mAvatar;
    }

    public void setAvatar(String avatar) {
        mAvatar = avatar;
    }

    @Override
    public String toString() {
        return "User{"
                + "uuid='" + mUUID + "', "
                + "firstname='" + mFirstName + "', "
                + "mLastName='" + mLastName + "', "
                + "mOccupation='" + mOccupation + "', "
                + "mAvatar='" + mAvatar
                + "'}";
    }
}
