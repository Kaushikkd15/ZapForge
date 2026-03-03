package com.processor.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.Map;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "zap_trigger")
public class Trigger extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(nullable = false, unique = true)
    private String zapId;

    @Column(name="triggerId" , nullable = false)
    private String triggerId;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "json")
    private Map<String,Object> metadata;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "triggerId", updatable = false, insertable = false)
    private AvailableTrigger type;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "zapId", updatable = false, insertable = false)
    private Zap zap;

    @PrePersist
    public void prePersist() {
        if (metadata == null) {
            metadata = Map.of();
        }
    }
}
