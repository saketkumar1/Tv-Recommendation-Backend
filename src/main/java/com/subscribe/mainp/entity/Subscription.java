package com.subscribe.mainp.entity;

import java.util.Date;
import javax.persistence.*;

import lombok.Data;

@Entity
@Data
public class Subscription {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	private Date startDate;
	private Date endDate;
	private Integer totalPrice;

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "subs_uid")
	private User user;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "subs_ottid")
	private Ott ott;

	private String planName;
	
}
