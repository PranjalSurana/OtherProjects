package com.fidelity.birthday;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Driver {

    public static void main(String[] args) {
        Map<String, Birthday> birthdayMap = new HashMap<>();
        birthdayMap.put("Alexander Hamilton", new Birthday(LocalDate.of(1757, 1, 11), "Charlestown"));
        birthdayMap.put("Winston Churchill", new Birthday(LocalDate.of(1874, 11, 30), "Blenheim Palace"));
        birthdayMap.put("Jawaharlal Nehru", new Birthday(LocalDate.of(1889, 11, 14), "Allahabad"));
        birthdayMap.put("Nelson Mandela", new Birthday(LocalDate.of(1918, 7, 18), "Mvezo"));
        birthdayMap.put("Charlemagne", new Birthday(LocalDate.of(742, 4, 2), "Aachen"));

        for(Map.Entry<String, Birthday> birthday: birthdayMap.entrySet()) {
            System.out.println(birthday.getKey() + " was born on " + birthday.getValue().getDateOfBirth() + " in " + birthday.getValue().getPlaceOfBirth());
        }
    }

}