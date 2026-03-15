package com.ioc.coupling;

public class WebServiceDataProvider implements UserDataProvider {
    @Override
    public String getUserDetails() {
        return "ioc Fetching Data From Web Service";
    }
}
