package com.subscribe.mainp.repository;

import com.subscribe.mainp.entity.History;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.List;

public interface HistoryRepository extends JpaRepository<History, Integer> {
    public List<History> findByUserId(int userid);
    @Transactional
    void deleteById(int id);
}
