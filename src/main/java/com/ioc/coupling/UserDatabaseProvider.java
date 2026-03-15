package com.ioc.coupling;

public class UserDatabaseProvider implements UserDataProvider {
    public String getUserDetails(){
        return "ioc User Details From Database";
    }
}
