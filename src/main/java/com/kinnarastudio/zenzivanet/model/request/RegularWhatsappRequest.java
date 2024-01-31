package com.kinnarastudio.zenzivanet.model.request;

import com.kinnarastudio.zenzivanet.model.ApiAccount;
import org.apache.http.HttpEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;

import java.net.URLEncoder;

import static org.apache.http.entity.mime.HttpMultipartMode.BROWSER_COMPATIBLE;

public class RegularWhatsappRequest {
    private final String baseUrl;
    private final String path = "/wareguler/api/sendWA";

    private final ApiAccount apiAccount;

    private final String to;
    private final String message;

    public RegularWhatsappRequest(String baseUrl, ApiAccount apiAccount, String to, String message) {
        this.baseUrl = baseUrl;
        this.apiAccount = apiAccount;
        this.to = to;
        this.message = message;
    }

    public String getUrl() {
        return String.join("/", baseUrl, path) + String.format("?userkey=%s&passkey=%s&to=%s&message=%s", apiAccount.getUserKey(), apiAccount.getApiKey(), to, URLEncoder.encode(message));
    }

    public HttpEntity getEntity() {
        final MultipartEntityBuilder entityBuilder = MultipartEntityBuilder.create()
                .setMode(BROWSER_COMPATIBLE)
                .addTextBody("userkey", apiAccount.getUserKey())
                .addTextBody("passkey", apiAccount.getApiKey())
                .addTextBody("to", to)
                .addTextBody("message", message);

        final HttpEntity httpEntity = entityBuilder.build();
        return httpEntity;
    }
}
