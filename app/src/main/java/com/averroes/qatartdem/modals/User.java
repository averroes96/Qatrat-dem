package com.averroes.qatartdem.modals;

public class User {

    private String uid,
            fullname,
            phone,
            email,
            dayra,
            wilaya,
            password,
            type,
            birth_date,
            timestamp,
            blood,
            gender,
            weight,
            height,
            diseases,
            donations,
            picture;

    public User(){}

    public User(String uid, String fullname, String phone, String email, String dayra, String wilaya, String password, String type, String birth_date, String timestamp, String blood, String gender, String weight, String height, String diseases, String donations, String picture) {
        this.uid = uid;
        this.fullname = fullname;
        this.phone = phone;
        this.email = email;
        this.dayra = dayra;
        this.wilaya = wilaya;
        this.password = password;
        this.type = type;
        this.birth_date = birth_date;
        this.timestamp = timestamp;
        this.blood = blood;
        this.gender = gender;
        this.weight = weight;
        this.height = height;
        this.diseases = diseases;
        this.donations = donations;
        this.picture = picture;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDayra() {
        return dayra;
    }

    public void setDayra(String dayra) {
        this.dayra = dayra;
    }

    public String getWilaya() {
        return wilaya;
    }

    public void setWilaya(String wilaya) {
        this.wilaya = wilaya;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getBirth_date() {
        return birth_date;
    }

    public void setBirth_date(String birth_date) {
        this.birth_date = birth_date;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getBlood() {
        return blood;
    }

    public void setBlood(String blood) {
        this.blood = blood;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getDiseases() {
        return diseases;
    }

    public void setDiseases(String diseases) {
        this.diseases = diseases;
    }

    public String getDonations() {
        return donations;
    }

    public void setDonations(String donations) {
        this.donations = donations;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }
}
