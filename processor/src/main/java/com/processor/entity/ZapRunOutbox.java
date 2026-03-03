package com.processor.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ZapRunOutbox {
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


    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String Id;

    @Column(unique = true)
    private String zapRunId;

}
