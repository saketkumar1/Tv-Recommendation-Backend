package com.subscribe.mainp.service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.time.LocalDate;  

import com.subscribe.mainp.dto.HistoryDto;
import com.subscribe.mainp.entity.*;
import com.subscribe.mainp.exceptions.ResourceNotFoundException;
import com.subscribe.mainp.repository.OttRepo;
import com.subscribe.mainp.repository.UserRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.subscribe.mainp.dto.subscriptionDto;
import com.subscribe.mainp.repository.subscriptionrepo;

@Service
public class subscriptionService implements subscriptionImpl {
	
	  private final Logger logger = LoggerFactory.getLogger(subscriptionService.class);

	@Autowired
	subscriptionrepo subsrepo;

	@Autowired
	UserRepo userrepo;

	@Autowired
	OttRepo ottRepo;

	@Autowired
	HistoryService historyService;

	@Autowired
	NotifyUser notifyUser;

	@Override
	public List<Subscription> getAllSubscription() {
		
		return subsrepo.findAll();
	}

	
	@Override
	@Scheduled(initialDelay = 10000,fixedDelay = 86400000)
	public Subscription removeSubscription() {
		// TODO Auto-generated method stub cronjobs
		
		List<Subscription> listSubs=subsrepo.findAll();
		
		for(Subscription subsVal:listSubs) {
			if(subsVal.getEndDate().compareTo(new Date())<0) {
				subsrepo.deleteById(subsVal.getId());
				addTohistory(subsVal);
			}
		}


		for(Subscription subsVal:listSubs) {
			Date today = new Date();
			today = new Date(today.getTime()+5*24*60*60*1000);
			System.out.println(today);
			Date endDate = new Date(subsVal.getEndDate().getTime());
			System.out.println(endDate);
			if(endDate.compareTo(today) <= 0) {
				String mail = subsVal.getUser().getEmail();
				String name = subsVal.getUser().getName();
				notifyUser.sendMail(mail,name);
			}
		}
		
		logger.info("calling all function"+new Date());

		
		return null;
	}

	@Override
	public Subscription addSubscription(subscriptionDto subs) {

		User user = this.userrepo.findById(subs.getUserId()).get();

		Ott ott = this.ottRepo.findById(subs.getMovieId()).get();

//		removeSubscription();
	


		Date today = new Date();
		Date endDate = new Date();

		int days=subs.getNoOfDays();
		while(days>0){
			long ltime = endDate.getTime()+5*24*60*60*1000;
			endDate=new Date(ltime);
			days=days-5;
		}



		Subscription sub=new Subscription();
		sub.setUser(user);
		sub.setOtt(ott);
		sub.setStartDate(today);
		sub.setEndDate(endDate);
		sub.setPlanName(subs.getPlanName());
		sub.setTotalPrice(subs.getPrice());
		subsrepo.save(sub);
		
		return sub;
	}


	@Override
	public List<Subscription> getSubscriptionByUserID(int userid) {
		List<Subscription> subs = subsrepo.findByUserId(userid);
		return subs;
	}

	public void addTohistory(Subscription subsValue) {
		HistoryDto his = new HistoryDto();
		his.setMovieId(subsValue.getOtt().getMovie_id());
		his.setUserId(subsValue.getUser().getId());
		historyService.saveHistory(his);
	}

	@Override
	public void deleteSubscription(long subscriptionId) {
		Subscription subscription = this.subsrepo.findById(subscriptionId).orElseThrow(()-> new ResourceNotFoundException("Subscription", "id", subscriptionId));
		this.subsrepo.delete(subscription);
	}

}
