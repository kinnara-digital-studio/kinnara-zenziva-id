package com.kinnarastudio.zenzivanet.model;

import com.kinnarastudio.zenzivanet.model.exception.RequestException;
import com.kinnarastudio.zenzivanet.model.exception.ResponseException;
import com.kinnarastudio.zenzivanet.model.request.BalanceRequest;
import com.kinnarastudio.zenzivanet.model.request.RegularSmsRequest;
import com.kinnarastudio.zenzivanet.model.response.BalanceResponse;
import com.kinnarastudio.zenzivanet.model.response.RegularSmsResponse;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.json.JSONException;
import org.json.JSONObject;

import javax.annotation.Nonnull;
import javax.net.ssl.SSLContext;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;
import java.util.stream.Collectors;

public class ZenzivaClient {
    private final String baseUrl;
    private final ApiAccount apiAccount;

    private final boolean ignoreSslCertificateError;

    private final String webHookUrl;

    public ZenzivaClient(String baseUrl, ApiAccount apiAccount, boolean ignoreSslCertificateError, String webHookUrl) {
        this.baseUrl = baseUrl;
        this.apiAccount = apiAccount;
        this.ignoreSslCertificateError = ignoreSslCertificateError;
        this.webHookUrl = webHookUrl;
    }

    public BalanceResponse executeGetBalanceCheck() throws RequestException, ResponseException {
        final BalanceRequest balanceRequest = new BalanceRequest(baseUrl, apiAccount);

        final HttpGet request = new HttpGet(balanceRequest.getUrl());

        final HttpClient client = getHttpClient(ignoreSslCertificateError);

        final HttpResponse response;
        try {
            response = client.execute(request);
        } catch (IOException e) {
            throw new RequestException(e);
        }

        final int statusCode = getResponseStatus(response);
        if (statusCode != 200) {
            try (BufferedReader br = new BufferedReader(new InputStreamReader(response.getEntity().getContent()))) {
                String responsePayload = br.lines().collect(Collectors.joining());
                throw new ResponseException("Response code [" + statusCode + "] is not 200 (Success) :" + responsePayload);
            } catch (IOException e) {
                throw new ResponseException(e);
            }
        }

        try (BufferedReader br = new BufferedReader(new InputStreamReader(response.getEntity().getContent()))) {
            final JSONObject jsonResponseBody = new JSONObject(br.lines().collect(Collectors.joining()));
            return new BalanceResponse(jsonResponseBody);
        } catch (IOException | JSONException e) {
            throw new ResponseException(e);
        }
    }

    public RegularSmsResponse executePostRegularSms(String to, String message) throws RequestException, ResponseException {
        final RegularSmsRequest regularSmsRequest = new RegularSmsRequest(baseUrl, apiAccount, to, message);

        final HttpPost request = new HttpPost(regularSmsRequest.getUrl());

        final HttpClient client = getHttpClient(ignoreSslCertificateError);

        final HttpResponse response;
        try {
            response = client.execute(request);
        } catch (IOException e) {
            throw new RequestException(e);
        }

        final int statusCode = getResponseStatus(response);
        if (statusCode != 200) {
            try (BufferedReader br = new BufferedReader(new InputStreamReader(response.getEntity().getContent()))) {
                String responsePayload = br.lines().collect(Collectors.joining());
                throw new ResponseException("Response code [" + statusCode + "] is not 200 (Success) :" + responsePayload);
            } catch (IOException e) {
                throw new ResponseException(e);
            }
        }

        try (BufferedReader br = new BufferedReader(new InputStreamReader(response.getEntity().getContent()))) {
            final JSONObject jsonResponseBody = new JSONObject(br.lines().collect(Collectors.joining()));
            return new RegularSmsResponse(jsonResponseBody);
        } catch (IOException | JSONException e) {
            throw new ResponseException(e);
        }
    }

    protected int getResponseStatus(@Nonnull HttpResponse response) throws ResponseException {
        return Optional.of(response)
                .map(HttpResponse::getStatusLine)
                .map(StatusLine::getStatusCode)
                .orElseThrow(() -> new ResponseException("Error getting status code"));
    }

    protected HttpClient getHttpClient(boolean ignoreCertificate) throws RequestException {
        try {
            if (ignoreCertificate) {
                SSLContext sslContext = new SSLContextBuilder()
                        .loadTrustMaterial(null, (certificate, authType) -> true).build();
                return HttpClients.custom().setSSLContext(sslContext)
                        .setSSLHostnameVerifier(new NoopHostnameVerifier())
                        .build();
            } else {
                return HttpClientBuilder.create().build();
            }
        } catch (NoSuchAlgorithmException | KeyStoreException | KeyManagementException e) {
            throw new RequestException(e);
        }
    }

    public static class Builder {
        private ApiAccount apiAccount;

        private boolean ignoreSslCertificateError = false;

        private String baseUrl = "https://console.zenziva.net";

        private String webhookUrl = "";

        public Builder setBaseUrl(String baseUrl) {
            this.baseUrl = baseUrl;
            return this;
        }

        public Builder setIgnoreSslCertificateError(boolean ignoreSslCertificateError) {
            this.ignoreSslCertificateError = ignoreSslCertificateError;
            return this;
        }

        public Builder setAccount(ApiAccount apiAccount) {
            this.apiAccount = apiAccount;
            return this;
        }

        public Builder setWebhookUrl(String webhookUrl) {
            this.webhookUrl = webhookUrl;
            return this;
        }

        public ZenzivaClient build() {
            return new ZenzivaClient(baseUrl, apiAccount, ignoreSslCertificateError, webhookUrl);
        }
    }
}
