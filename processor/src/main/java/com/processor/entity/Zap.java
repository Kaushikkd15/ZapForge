package com.processor.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
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
    @JoinColumn(name = "userId", updatable = false, insertable = false)
    private User user;


}
