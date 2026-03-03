package com.zapier.worker.consumer;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.zapier.worker.dto.ZapEventMessage;
import com.zapier.worker.entity.ZapRun;
import com.zapier.worker.service.workerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class ZapWorkerConsumer {

       private final workerService workerService;
       private final ObjectMapper objectMapper;

       @KafkaListener(
               topics = "${kafka.topic.zap-events:zap-events}",
               groupId = "${spring.kafka.consumer.group-id:main-worker-2}",
               concurrency = "${kafka.consumer.concurrency:3}"
       )
       public void consume(@Payload String message, @Header(KafkaHeaders.RECEIVED_PARTITION) int partition,
                           @Header(KafkaHeaders.OFFSET) long offset, Acknowledgment acknowledgment){

              try{
                     log.info("Received message - Partition: {}, Offset: {}, Value: {}", partition, offset, message);

                     if(message == null || message.isEmpty()){
                            log.warn("Empty message received");
                            acknowledgment.acknowledge();
                            return;
                     }
                     ZapEventMessage parsedValue = objectMapper.readValue(message, ZapEventMessage.class);
                     String zapRunId = parsedValue.getZapRunId();
                     Integer stage = parsedValue.getStage();

                     log.info("Processing zapRunId: {}, stage: {}", zapRunId, stage);

                     workerService.executeZapAction(zapRunId, stage);

              }catch(Exception e){

              }

       }
}
