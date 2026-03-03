package com.zapier.worker.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor @AllArgsConstructor
public class ZapRunOutbox {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String Id;

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getZapRunId() {
        return zapRunId;
    }

    public void setZapRunId(String zapRunId) {
        this.zapRunId = zapRunId;
    }


    @Column(name="zapRunId", nullable = false, unique = true)
    private String zapRunId;
}
