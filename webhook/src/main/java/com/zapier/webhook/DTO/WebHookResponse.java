package com.zapier.webhook.DTO;

import lombok.*;

@Data
@NoArgsConstructor
public class WebHookResponse{
    private String message;

    public WebHookResponse(String message){
        this.message = message;
    }
}
