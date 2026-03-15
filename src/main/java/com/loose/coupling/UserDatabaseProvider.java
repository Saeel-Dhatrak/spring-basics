package com.loose.coupling;

public class UserDatabaseProvider implements UserDataProvider{
    public String getUserDetails(){
        // Directly access Database here
        return "User Details From Database";
    }
}
