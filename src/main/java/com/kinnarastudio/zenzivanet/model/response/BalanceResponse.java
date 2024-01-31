package com.kinnarastudio.zenzivanet.model.response;

import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;

public class BalanceResponse {
    final private BigDecimal balance;
    final private int status;
    final private String text;

    public BalanceResponse(JSONObject json) throws JSONException {
        this(BigDecimal.valueOf(Double.parseDouble(json.getString("balance").replaceAll(",", ""))), json.getInt("status"), json.getString("text"));
    }

    public BalanceResponse(BigDecimal balance, int status, String text) {
        this.balance = balance;
        this.status = status;
        this.text = text;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public int getStatus() {
        return status;
    }

    public String getText() {
        return text;
    }
}
