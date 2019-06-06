package com.pcp.unittest;

import java.util.Calendar;

public class SharedPreferenceEntry {
    // Name of the user
    private final String name;

    // Date of Birth of the user
    private final Calendar dateOfBirth;

    // Email address of the user
    private final String email;

    public SharedPreferenceEntry(String name, Calendar dateOfBirth, String email){
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.email = email;
    }

    public String getName(){
        return name;
    }
    public Calendar getDateOfBirth(){
        return dateOfBirth;
    }
    public String getEmail(){
        return email;
    }
}
