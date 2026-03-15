package com.loose.coupling;

public class LooseCouplingExample {
    public static void main(String[] args) {
        UserDataProvider userDataProvider = new UserDatabaseProvider();
        // The USerManager constructor expects a parameter and we need to pass in the
        // object of UserDatabasePrvider and we have the `userDataProvider` reference
        // variable as the interface so we will pass userDataProvider
        UserManager userManagerwithDB = new UserManager(userDataProvider);
        System.out.println(userManagerwithDB.getUserInfo());


        UserDataProvider webServiceProvider = new WebServiceDataProvider();
        UserManager userManagerWitWS = new UserManager(webServiceProvider);
        System.out.println(userManagerWitWS.getUserInfo());

        UserDataProvider userDataProviderNewDB = new NewDatabaseProvider();
        UserManager userManagerNewDB = new UserManager(userDataProviderNewDB);
        System.out.println(userManagerNewDB.getUserInfo());
    }
}
