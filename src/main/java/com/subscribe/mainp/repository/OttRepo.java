package com.subscribe.mainp.repository;

import com.subscribe.mainp.entity.Ott;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OttRepo extends JpaRepository<Ott, Integer> {

    public List<Ott> findByGenre(int Genre);

    public List<Ott> findByMoviename(String title);

    public List<Ott> findByNetflix(int netflix);

    public List<Ott> findByPrime(int prime);

    public List<Ott> findByHulu(int hulu);

    public List<Ott> findByDisney(int disney);

    @Query("from Ott where genre = :genre1 or genre = :genre2 order by rating desc")
    public List<Ott> findShowByGenre(int genre1, int genre2);

}
