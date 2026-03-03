package com.zapier.worker.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
public class User extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    private String name;
    private String email;
    private String password;

}
