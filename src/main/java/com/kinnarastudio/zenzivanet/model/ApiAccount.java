package com.kinnarastudio.zenzivanet.model;

public class ApiAccount {
    private final String userKey;
    private final String apiKey;

    public ApiAccount(String userKey, String apiKey) {
        this.userKey = userKey;
        this.apiKey = apiKey;
    }

    public String getUserKey() {
        return userKey;
    }

    public String getApiKey() {
        return apiKey;
    }
}
