package com.zapier.webhook.service;

import com.zapier.webhook.entity.ZapRun;
import com.zapier.webhook.entity.ZapRunOutbox;
import com.zapier.webhook.repository.ZapRunOutBoxRepository;
import com.zapier.webhook.repository.ZapRunRepository;
import jakarta.transaction.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class WebHookService {

    private final ZapRunRepository zapRunRepository;

    private final ZapRunOutBoxRepository zapRunOutBoxRepository;

    public WebHookService(ZapRunRepository zapRunRepository, ZapRunOutBoxRepository zapRunOutBoxRepository){
        this.zapRunRepository = zapRunRepository;
        this.zapRunOutBoxRepository = zapRunOutBoxRepository;
    }

    @Transactional
    public void processWebHook(String userId, String zapId, Map<String,Object> body){

        ZapRun zapRun = new ZapRun();
        zapRun.setZapId(zapId);
        zapRun.setMetadata(body);

        ZapRun savedZapRun = zapRunRepository.save(zapRun);
        zapRunRepository.flush();
        ZapRunOutbox zapRunOutbox = new ZapRunOutbox();
        zapRunOutbox.setZapRunId(savedZapRun.getZapId());

        zapRunOutBoxRepository.save(zapRunOutbox);
    }
}
