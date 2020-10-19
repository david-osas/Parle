package com.example.parle.Models;

public class Student {
    private String Username;
    private String FullName;
    private String userId;
    private String Country;
    private String State;
    private String Sex;


    private String DateOfBirth;
    private String Concentrate;
    private String PIN;
    private String PhoneNumber;
    private String Religion;

    private boolean SimilarReligionCounselor,
                    SpiritualCounselling,
                    IsOnline;



    public Student() {
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getFullName() {
        return FullName;
    }

    public void setFullName(String fullName) {
        FullName = fullName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCountry() {
        return Country;
    }

    public void setCountry(String country) {
        Country = country;
    }

    public String getState() {
        return State;
    }

    public void setState(String state) {
        State = state;
    }

    public String getSex() {
        return Sex;
    }

    public void setSex(String sex) {
        Sex = sex;
    }

    public String getDateOfBirth() {
        return DateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        DateOfBirth = dateOfBirth;
    }

    public String getConcentrate() {
        return Concentrate;
    }

    public void setConcentrate(String concentrate) {
        Concentrate = concentrate;
    }

    public String getPIN() {
        return PIN;
    }

    public void setPIN(String PIN) {
        this.PIN = PIN;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        PhoneNumber = phoneNumber;
    }

    public String getReligion() {
        return Religion;
    }

    public void setReligion(String religion) {
        Religion = religion;
    }

    public boolean isSimilarReligionCounselor() {
        return SimilarReligionCounselor;
    }

    public void setSimilarReligionCounselor(boolean similarReligionCounselor) {
        SimilarReligionCounselor = similarReligionCounselor;
    }

    public boolean isSpiritualCounselling() {
        return SpiritualCounselling;
    }

    public void setSpiritualCounselling(boolean spiritualCounselling) {
        SpiritualCounselling = spiritualCounselling;
    }

    public boolean isOnline() {
        return IsOnline;
    }

    public void setOnline(boolean online) {
        IsOnline = online;
    }
}
