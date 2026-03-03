package com.zapier.worker.domain.action;

import lombok.Value;

@Value
public class SendEmailAction implements DomainAction{
    String recipient;
    String subject;
    String body;
    String from;

    @Override
    public String getActionType(){
        return "SEND_EMAIL";
    }
}
