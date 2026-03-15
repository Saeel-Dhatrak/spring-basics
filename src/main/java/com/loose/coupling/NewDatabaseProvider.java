package com.loose.coupling;

public class NewDatabaseProvider implements UserDataProvider{
    @Override
    public String getUserDetails() {
        return "Data from the new database";
    }
}
