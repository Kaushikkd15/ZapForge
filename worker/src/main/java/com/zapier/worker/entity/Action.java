package com.zapier.worker.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.Map;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Action extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String Id;

    @Column(nullable = false)
    private String zapId;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "json")
    private Map<String, Object> metadata;

    @Column(nullable = false)
    private Integer sortingOrder = 0;

    @Column(nullable = false)
    private String actionId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "zapId", updatable = false, insertable = false)
    private Zap zap;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "actionId", updatable = false, insertable = false)
    private AvailableAction type;

    @PrePersist
    public void prePersist() {
        if (metadata == null) {
            metadata = Map.of();
        }
        if (sortingOrder == null) {
            sortingOrder = 0;
        }
    }
}
