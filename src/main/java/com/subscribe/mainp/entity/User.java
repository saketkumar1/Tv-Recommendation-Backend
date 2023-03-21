package com.subscribe.mainp.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name="users")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private int age;
    private String name;
    private String username;
    private String email;
    private String password;

//    @OneToOne(cascade = CascadeType.ALL, mappedBy = "users")
//    private History history;
//
//    public User(int id, int age, String name, String username, String email, String password) {
//        this.id = id;
//        this.age = age;
//        this.name = name;
//        this.username = username;
//        this.email = email;
//        this.password = password;
//    }
}

