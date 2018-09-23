package com.example.packageindexer.model;

public class MessageHandleResult {
    private MessageResultEnum result;
    private String reason;

    public MessageHandleResult(MessageResultEnum result, String reason) {
        this.result = result;
        this.reason = reason;
    }

    public MessageHandleResult(MessageResultEnum result) {
        this.result = result;
    }

    public MessageResultEnum getResult() {
        return result;
    }

    public String getReason() {
        return reason;
    }
}
