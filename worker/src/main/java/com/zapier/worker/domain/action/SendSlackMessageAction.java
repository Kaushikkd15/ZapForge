package com.zapier.worker.domain.action;

import lombok.Value;

@Value
public class SendSlackMessageAction implements DomainAction{

    String message;
    String channel;
    String username;
    @Override
    public String getActionType() {
        return "SEND_SLACK_MESSAGE";
    }
}
