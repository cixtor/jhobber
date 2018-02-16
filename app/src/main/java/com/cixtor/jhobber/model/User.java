package com.cixtor.jhobber.model;

public class User {
    private final int MAX_FULLNAME = 17;
    private final int MAX_OCCUPATION = 38;

    private String mUUID;
    private String mFirstName;
    private String mLastName;
    private String mOccupation;
    private String mAvatar;
    private boolean mValid;

    public User() {
        mValid = false;
    }

    public String getUUID() {
        return mUUID;
    }

    public void setUUID(String uuid) {
        if (uuid.matches("[0-9a-zA-Z]{8}(\\-[0-9a-zA-Z]{4}){3}\\-[0-9a-zA-Z]{12}")) {
            mValid = true;
            mUUID = uuid;
        }
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

    public String getFullName() {
        String full = mFirstName + "\u0020" + mLastName;

        if (full.length() > MAX_FULLNAME) {
            return full.substring(0, MAX_FULLNAME) + "…";
        }

        return full;
    }

    public String getOccupation() {
        if (mOccupation.length() > MAX_OCCUPATION) {
            return mOccupation.substring(0, MAX_OCCUPATION) + "…";
        }

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

    public boolean isValid() {
        return mValid;
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
