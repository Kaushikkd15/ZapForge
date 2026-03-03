package com.zapier.worker.domain.action;

import lombok.*;

@Value
public class ActionResult {
    boolean success;
    String message;
    String externalId;
    Exception error;

    public static ActionResult success(String message, String externalId){
        return new ActionResult(true,message, externalId, null);
    }

    public static ActionResult failure(String message, Exception error){
        return new ActionResult(false,message, null, error);
    }
}
