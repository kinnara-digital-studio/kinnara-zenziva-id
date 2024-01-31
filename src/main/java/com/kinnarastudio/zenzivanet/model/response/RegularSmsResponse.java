package com.kinnarastudio.zenzivanet.model.response;

import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;

public class RegularSmsResponse {
    final private long messageId;
    final private String to;
    final private int status;
    final private String text;

    public RegularSmsResponse(JSONObject json) throws JSONException {
        this(json.getLong("messageId"), json.getString("to"), json.getInt("status"), json.getString("text"));
    }

    public RegularSmsResponse(long messageId, String to, int status, String text) {
        this.messageId = messageId;
        this.to = to;
        this.status = status;
        this.text = text;
    }

    public int getStatus() {
        return status;
    }

    public String getText() {
        return text;
    }

    public long getMessageId() {
        return messageId;
    }

    public String getTo() {
        return to;
    }
}
