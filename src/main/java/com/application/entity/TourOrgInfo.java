package com.application.entity;

import java.util.Objects;

public class TourOrgInfo extends Entity {
    private String tourOrganization;
    private String license;

    public String getTourOrg() {
        return tourOrganization;
    }

    public void setTourOrg(String tourOrganization) {
        this.tourOrganization = tourOrganization;
    }

    public String getLicense() {
        return license;
    }

    public void setLicense(String license) {
        this.license = license;
    }

    @Override
    public String toString() {
        return "TourOrgInfo{" +
                "tourOrganization='" + tourOrganization + '\'' +
                ", license='" + license + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        TourOrgInfo that = (TourOrgInfo) o;
        return tourOrganization.equals(that.tourOrganization) && license.equals(that.license);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), tourOrganization, license);
    }
}
