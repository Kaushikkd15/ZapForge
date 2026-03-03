package com.zapier.worker.service;

import com.zapier.worker.entity.Action;
import com.zapier.worker.entity.ZapRun;
import com.zapier.worker.repository.ZapRunRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class workerService {

    private final ZapRunRepository zapRunRepository;


    @Value("${kafka.topic.zap-events:zap-events}")
    private String topicName;

    @Transactional(readOnly=true)
    public void executeZapAction(String zapRunId, Integer stage){
        ZapRun zapRunDetails = zapRunRepository.findByIdWithDetails(zapRunId).orElseThrow(() -> new RuntimeException("Zap Not Found: {}" + zapRunId));

        List<Action> actions = zapRunDetails.getZap().getActions();
        Action currentAction = zapRunDetails.getZap().getActions()
                .stream().filter(action -> action.getSortingOrder().equals(stage))
                .findFirst().orElse(null);

        if(currentAction ==  null){
           log.error("Current action not found for stage: {} ", stage);
           return;
        }

        Map<String, Object> zapRunMetadata = zapRunDetails.getMetadata();
        String actionTypeId = currentAction.getType().getId();
        Map<String,Object> actionMetadata = currentAction.getMetadata();

        log.info("Action details - Type: {}, Stage: {}/{}", actionTypeId,stage,actions.size() -1);

    }

    private void executeEmailAction(Map<String, Object> actionMetadata, Map<String,Object> zapRunMetadata){
        String bodyTemplate = (String) actionMetadata.get("body");
        String emailTemplate = (String) actionMetadata.get("email");
    }
}
