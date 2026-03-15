package com.loose.coupling;

public class UserManager {
    private UserDataProvider userDataProvider;

    public UserManager(UserDataProvider userDataProvider){
        this.userDataProvider = userDataProvider;
    }

    // When instantiating the USerManager we are going to provide the implementation
    // as the parameter to the constructor

    public String getUserInfo(){
        return userDataProvider.getUserDetails();
    }
}
