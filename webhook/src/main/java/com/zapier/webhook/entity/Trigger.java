package com.zapier.webhook.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.Map;

@Entity
@Getter @Setter @AllArgsConstructor
@Table(name = "zap_trigger")
public class Trigger extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String Id;

    @Column(nullable = false, unique = true)
    private String zapId;

    @Column(name= "triggerId" ,nullable = false)
    private String triggerId;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "json")
    private Map<String, Object> metadata;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "triggerId", insertable = false, updatable = false)
    private AvailableTrigger type;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "zapId", updatable = false, insertable = false)
    private Zap zap;
}
