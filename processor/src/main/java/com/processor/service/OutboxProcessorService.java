package com.processor.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.processor.dto.ZapEventMessage;
import com.processor.entity.ZapRunOutbox;
import com.processor.repository.ZapRunOutboxRepository;
import jakarta.transaction.Transactional;
import lombok.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Data
public class OutboxProcessorService {

    private static final Logger log = LoggerFactory.getLogger(OutboxProcessorService.class);

    @Value("${kafka.topic.zap-events:zap-events}")
    private String topicName;

    private final ZapRunOutboxRepository zapRunOutboxRepository;
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    public OutboxProcessorService(ZapRunOutboxRepository zapRunOutboxRepository, KafkaTemplate<String, String> kafkaTemplate, ObjectMapper objectMapper) {

        this.zapRunOutboxRepository = zapRunOutboxRepository;
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = objectMapper;
    }

    private static final int BATCH_SIZE = 10;


    @Transactional
    @Scheduled(fixedDelay = 3000)
    public void processOutbox() {
        try {
            List<ZapRunOutbox> pendingRows = zapRunOutboxRepository
                    .findAll(PageRequest.of(0, BATCH_SIZE))
                    .getContent();
            if (pendingRows.isEmpty()) {
                log.debug("No pending outbox entries to process");
                return;
            }
            log.info("Processing {} outbox entries", pendingRows.size());

            for (ZapRunOutbox outbox : pendingRows) {
                ZapEventMessage message = new ZapEventMessage(outbox.getZapRunId(), 0);

                String messageJson = objectMapper.writeValueAsString(message);

                kafkaTemplate.send(topicName, messageJson).get();

            }

            List<String> processedIds = pendingRows.stream()
                    .map(ZapRunOutbox::getId)
                    .collect(Collectors.toList());

            zapRunOutboxRepository.deleteAllById(processedIds);
            zapRunOutboxRepository.flush();

            log.info("Successfully processed and deleted {} outbox entries", processedIds.size());
        } catch (Exception e) {
            log.error("Error processing zapRun", e);

        }
    }
}

