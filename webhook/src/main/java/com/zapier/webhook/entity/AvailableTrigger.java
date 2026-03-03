package com.zapier.webhook.entity;

import jakarta.persistence.*;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor @AllArgsConstructor
@Table(name = "Available_Triggers")
public class AvailableTrigger {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String Id;

    private String name;

    private String image;

    @OneToMany(mappedBy = "type", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Trigger> triggers;
}
