package com.typewars.model;

import org.apache.logging.log4j.util.Strings;

import java.util.List;
import java.util.stream.Collectors;

public class NotificationsResponse {
    private List<String> messages;
    private String all;

    public NotificationsResponse() {
    }

    public NotificationsResponse(List<String> messages) {
        this.messages = messages;
        List<String> quotedMessages = messages.stream()
                .map(message -> "\"" + message + "\"")
                .collect(Collectors.toList());
        all = Strings.join(quotedMessages, ',');
    }

    public List<String> getMessages() {
        return messages;
    }

    public void setMessages(List<String> messages) {
        this.messages = messages;
    }

    public String getAll() {
        return all;
    }

    public void setAll(String all) {
        this.all = all;
    }
}
