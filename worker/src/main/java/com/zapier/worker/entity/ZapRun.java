package com.zapier.worker.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.Map;

@Entity
@Getter
@Setter
@NoArgsConstructor @AllArgsConstructor
public class ZapRun {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String Id;

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getZapId() {
        return zapId;
    }

    public void setZapId(String zapId) {
        this.zapId = zapId;
    }

    public Map<String, Object> getMetadata() {
        return metadata;
    }

    public void setMetadata(Map<String, Object> metadata) {
        this.metadata = metadata;
    }

    public Zap getZap() {
        return zap;
    }

    public void setZap(Zap zap) {
        this.zap = zap;
    }

    @Column(nullable = false)
    private String zapId;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "json")
    private Map<String,Object> metadata;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "zapId", insertable = false, updatable = false)
    private Zap zap;

}
