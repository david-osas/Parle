package com.example.parle.models;

public class Student {
    //nmodel for student

    private String username;
    private String email;
    private String fullName;
    private String userId;
    private String country;
    private String state;
    private String gender;


    private String dateOfBirth;
    private String concentrate;
    private String pin;
    private String phoneNumber;
    private String religion;

    private boolean similarReligionCounselor,
                    spiritualCounselling,
                    isOnline,
                    isAnonymous;



    public Student() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getConcentrate() {
        return concentrate;
    }

    public void setConcentrate(String concentrate) {
        this.concentrate = concentrate;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getReligion() {
        return religion;
    }

    public void setReligion(String religion) {
        this.religion = religion;
    }

    public boolean isSimilarReligionCounselor() {
        return similarReligionCounselor;
    }

    public void setSimilarReligionCounselor(boolean similarReligionCounselor) {
        this.similarReligionCounselor = similarReligionCounselor;
    }

    public boolean isSpiritualCounselling() {
        return spiritualCounselling;
    }

    public void setSpiritualCounselling(boolean spiritualCounselling) {
        this.spiritualCounselling = spiritualCounselling;
    }

    public boolean isOnline() {
        return isOnline;
    }

    public void setOnline(boolean online) {
        isOnline = online;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isAnonymous() {
        return isAnonymous;
    }

    public void setAnonymous(boolean anonymous) {
        isAnonymous = anonymous;
    }
}
