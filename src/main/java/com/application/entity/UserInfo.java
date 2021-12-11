package com.application.entity;

import java.sql.Date;
import java.util.Arrays;
import java.util.Objects;

public class UserInfo extends Entity{
    private String name;
    private String surname;
    private String middleName = "";
    private String phone;
    private String passport;
    private Date dateBirthDay;
    private char [] codeCountry = new char[3];
    private String country ;
    private boolean sex;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setPhone(Long phone) {
        this.phone = "+"+phone;
    }

    public String getPassport() {
        return passport;
    }

    public void setPassport(String passport) {
        this.passport = passport;
    }

    public Date getDateBirthDay() {
        return dateBirthDay;
    }

    public void setDateBirthDay(String dateBirthDay) {
            this.dateBirthDay = Date.valueOf(dateBirthDay);
    }

    public void setDateBirthDay(Date dateBirthDay) {
        this.dateBirthDay = dateBirthDay;
    }

    public boolean getSex() {
        return sex;
    }

    public void setSex(boolean sex) {
        this.sex = sex;
    }

    public void setSex(String sex) {
        if (sex.equals("man")){
            this.sex = false;
        } else if (sex.equals("woman")) {
            this.sex = true;
        }
    }

    public char[] getCodeCountry() {
        return codeCountry;
    }

    public void setCodeCountry(String codeCountry) {
        this.codeCountry = codeCountry.toCharArray();
    }

    public void setCodeCountry(char[] codeCountry) {
        this.codeCountry = codeCountry;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        UserInfo userInfo = (UserInfo) o;
        return getId()== userInfo.getId() && sex == userInfo.sex && name.equals(userInfo.name) && surname.equals(userInfo.surname) && Objects.equals(middleName, userInfo.middleName) && phone.equals(userInfo.phone) && passport.equals(userInfo.passport) && dateBirthDay.equals(userInfo.dateBirthDay) && Arrays.equals(codeCountry, userInfo.codeCountry);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(super.hashCode(), name, surname, middleName, phone, passport, dateBirthDay, sex);
        result = 31 * result + Arrays.hashCode(codeCountry);
        return result;
    }

    @Override
    public String toString() {
        return "UserInfo{" +
                "name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", middleName='" + middleName + '\'' +
                ", phone='" + phone + '\'' +
                ", passport='" + passport + '\'' +
                ", dateBirthDay=" + dateBirthDay +
                ", codeCountry=" + Arrays.toString(codeCountry) +
                ", sex=" + sex +
                "} " + super.toString();
    }

    private Long processPhone (String phoneNumber){
        StringBuilder phoneNumberProc = new StringBuilder();
        for (int i = 0; i < phoneNumber.length(); i++){
            if (phoneNumber.charAt(i) >= 48 && phoneNumber.charAt(i) <= 57){
                phoneNumberProc.append(phoneNumber.charAt(i));
            }
        }
        return Long.valueOf(phoneNumberProc.toString());
    }
}
