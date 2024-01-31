package com.kinnarastudio.zenzivanet.model.request;

import com.kinnarastudio.zenzivanet.model.ApiAccount;

public class RegularSmsRequest {
    private final String baseUrl;
    private final String path = "/reguler/api/sendsms";

    private final ApiAccount apiAccount;

    private final String to;
    private final String message;

    public RegularSmsRequest(String baseUrl, ApiAccount apiAccount, String to, String message) {
        this.baseUrl = baseUrl;
        this.apiAccount = apiAccount;
        this.to = to;
        this.message = message;
    }

    public String getUrl() {
        return String.join("/", baseUrl, path) + String.format("?userkey=%s&passkey=%s&to=%s&message=%s", apiAccount.getUserKey(), apiAccount.getApiKey(), to, message);
    }
}
