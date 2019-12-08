package com.example.yourcheck;

import java.util.ArrayList;
import java.util.List;

public class Check {

    public String userID;
    public String firstName;
    public String lastName;
    public String phoneNumber;
    public String DOB;
    public String SSN;
    public String checkPackage;
    public String nationalStatus;
    public String countyStatus;
    public String watchListStatus;
    public String extensiveStatus;
    public List<String> sharedWith;

    public Check(){
    }

    public Check(String userID, String firstName, String lastName, String phoneNumber, String DOB, String SSN, String checkPackage, String nationalStatus, String countyStatus, String watchListStatus, String extensiveStatus, List<String> sharedWith){
        this.userID = userID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.DOB = DOB;
        this.SSN = SSN;
        this.checkPackage = checkPackage;
        this.nationalStatus = nationalStatus;
        this.countyStatus = countyStatus;
        this.watchListStatus = watchListStatus;
        this.extensiveStatus = extensiveStatus;
        this.sharedWith = sharedWith;
    }

}
