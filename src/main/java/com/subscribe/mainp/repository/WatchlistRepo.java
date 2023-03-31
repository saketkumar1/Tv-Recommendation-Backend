package com.subscribe.mainp.repository;

import com.subscribe.mainp.entity.Watchlist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface WatchlistRepo extends JpaRepository<Watchlist, Integer> {

    List<Watchlist> findWatchlistByUserId(int userId);
}
