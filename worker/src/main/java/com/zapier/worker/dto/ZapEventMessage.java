package com.zapier.worker.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ZapEventMessage {

    @JsonProperty("zapRunId")
    private String zapRunId;

    @JsonProperty("stage")
    private Integer stage;
}
