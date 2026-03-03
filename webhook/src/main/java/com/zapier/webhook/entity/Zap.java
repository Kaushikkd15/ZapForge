package com.zapier.webhook.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.*;


import java.util.List;

@Entity

@Getter @Setter @ToString @AllArgsConstructor
@Data
public class Zap extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String Id;

    @Column(nullable = false)
    private String triggerId;

    @Column(nullable = false)
    private Long userId;

    @OneToOne(mappedBy = "zap", cascade = CascadeType.ALL, orphanRemoval = true)
    private Trigger trigger;

    @OneToMany(mappedBy = "zap", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Action> actions;

    @OneToMany(mappedBy = "zap", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ZapRun> zapRuns;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name= "userId", updatable = false, insertable = false)
    private User user;
}
