package com.application.entity;

import java.util.Objects;

public class User extends Entity{
    private String email;
    private String password;
    private Role role;
    private UserInfo userInfo;
    private TourOrgInfo tourOrgInfo;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public void setRole (int role){
        this.role = Role.values()[role];
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    public void setTourOrgInfo(TourOrgInfo tourOrgInfo) {
        this.tourOrgInfo = tourOrgInfo;
    }

    public TourOrgInfo getTourOrgInfo() { return tourOrgInfo; }

    @Override
    public String toString() {
        return "User{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", role=" + role +
                ", userInfo=" + userInfo +
                ", tourOrgInfo=" + tourOrgInfo +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return email.equals(user.email) && Objects.equals(password, user.password) && role == user.role && Objects.equals(userInfo, user.userInfo) && Objects.equals(tourOrgInfo, user.tourOrgInfo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email, password, role, userInfo, tourOrgInfo);
    }
}
