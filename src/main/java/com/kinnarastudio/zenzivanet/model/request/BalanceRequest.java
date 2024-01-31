package com.kinnarastudio.zenzivanet.model.request;

import com.kinnarastudio.zenzivanet.model.ApiAccount;

public class BalanceRequest {
    private final String baseUrl;
    private final String path = "/api/balance";

    private final ApiAccount apiAccount;


    public BalanceRequest(String baseUrl, ApiAccount apiAccount) {
        this.baseUrl = baseUrl;
        this.apiAccount = apiAccount;
    }

    public String getPath() {
        return path;
    }

    public String getUrl() {
        return String.join("/", baseUrl, path) + String.format("?userkey=%s&passkey=%s", apiAccount.getUserKey(), apiAccount.getApiKey());
    }
}
