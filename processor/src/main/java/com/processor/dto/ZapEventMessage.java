package com.processor.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ZapEventMessage {
    public ZapEventMessage(String zapRunId, Integer stage) {
        this.zapRunId = zapRunId;
        this.stage = stage;
    }

    @JsonProperty("zapRunId")
    private String zapRunId;

    public String getZapRunId() {
        return zapRunId;
    }

    public void setZapRunId(String zapRunId) {
        this.zapRunId = zapRunId;
    }

    public Integer getStage() {
        return stage;
    }

    public void setStage(Integer stage) {
        this.stage = stage;
    }

    @JsonProperty("stage")
    private Integer stage;
}