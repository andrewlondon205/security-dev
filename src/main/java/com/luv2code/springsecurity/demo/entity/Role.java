package com.luv2code.springsecurity.demo.entity;

import lombok.*;

import javax.persistence.*;

@Getter  @Setter  @NoArgsConstructor  @AllArgsConstructor @ToString
@Entity
@Table(name="role")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="id")
    private Long id;

    @Column(name = "name")
    private String name;

    public Role(String name) {
        this.name = name;
    }

}
