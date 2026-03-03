package com.zapier.webhook.controller;


import com.zapier.webhook.DTO.WebHookResponse;
import com.zapier.webhook.service.WebHookService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping(value = "/hooks")
public class CatchHookController {

    private final WebHookService webHookService;

    public CatchHookController(WebHookService webHookService){
        this.webHookService = webHookService;
    }

    @PostMapping("/catch/{userId}/{zapId}")
    public String catchWebHook(@PathVariable String userId,
                                                        @PathVariable String zapId,
                                                        @RequestBody Map<String, Object> body){
        webHookService.processWebHook(userId,zapId, body);


        return "WebHook successful";
    }
}
