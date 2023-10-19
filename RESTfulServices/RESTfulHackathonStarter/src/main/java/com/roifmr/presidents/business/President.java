package com.roifmr.presidents.business;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class President {

    int id;
    String firstName;
    String lastName;
    int startYear;
    int endYear;
    String imagePath;
    String bio;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof President president)) return false;
        return getId() == president.getId() && getStartYear() == president.getStartYear() && getEndYear() == president.getEndYear() && Objects.equals(getFirstName(), president.getFirstName()) && Objects.equals(getLastName(), president.getLastName()) && Objects.equals(getImagePath(), president.getImagePath()) && Objects.equals(getBio(), president.getBio());
    }

    @Override
    public String toString() {
        return "President{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", startYear=" + startYear +
                ", endYear=" + endYear +
                ", imagePath='" + imagePath + '\'' +
                ", bio='" + bio + '\'' +
                '}';
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getFirstName(), getLastName(), getStartYear(), getEndYear(), getImagePath(), getBio());
    }
}