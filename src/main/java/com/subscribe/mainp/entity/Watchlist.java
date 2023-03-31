package com.subscribe.mainp.entity;

import javax.persistence.*;

import org.hibernate.annotations.CreationTimestamp;

import lombok.Data;

import java.util.Date;

@Entity
@Data
public class Watchlist {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private int id;

    @Column
    private String movie_name;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "watchlist_uid")
    private User user;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "watchlist_ottid")
    private Ott ott;
    private Date date;



}