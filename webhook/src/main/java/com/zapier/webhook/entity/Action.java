package com.zapier.webhook.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Action extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String Id;

    @Column(nullable = false)
    private String zapId;

    @Column(nullable = false)
    private  String actionId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "zapId", insertable = false, updatable = false)
    private Zap zap;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "actionId", insertable = false, updatable = false)
    private AvailableAction type;


}
