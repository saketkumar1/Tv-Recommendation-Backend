package com.subscribe.mainp.service;

import com.subscribe.mainp.entity.History;
import com.subscribe.mainp.entity.Ott;
import com.subscribe.mainp.exceptions.ResourceNotFoundException;
import com.subscribe.mainp.repository.OttRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class OttService implements OttImpl{

    @Autowired
    OttRepo repo;

    @Autowired
    HistoryService historyService;
    @Override
    public Ott getMovieByMovieId(int id) {
        Ott show = this.repo.findById(id).orElseThrow(()->new ResourceNotFoundException("Movie", "id", id));
        return show;
    }

    @Override
    public List<Ott> getMovieByGenre(int genre) {
        List<Ott> shows = repo.findByGenre(genre);
        return shows;
    }

    @Override
    public List<Ott> getMovieByOTT(String ottName) {

        List<Ott> shows = null;

        if(ottName.equalsIgnoreCase("netflix"))
             shows = repo.findByNetflix(1);

        else if(ottName.equalsIgnoreCase("prime"))
            shows = repo.findByPrime(1);

        else if(ottName.equalsIgnoreCase("hulu"))
            shows = repo.findByHulu(1);

        else if(ottName.equalsIgnoreCase("disney"))
            shows = repo.findByDisney(1);

        return shows;
    }

    @Override
    public List<Ott> getMovieByTitle(String Title) {
        List<Ott> shows = repo.findByMoviename(Title);
        return shows;
    }




    @Override
    public List<Ott> getRecommendations(int id)
    {
        List<History> res = historyService.getHistoryByUserId(id);

        Map<Integer,Integer> map = new HashMap<>();

        for(History h : res)
        {
            Integer count = map.get(h.getGenre());

            if(count == null)
                map.put(h.getGenre(),h.getRating());

            else {
                if (h.getRating() != null)
                    map.put(h.getGenre(), count + h.getRating());
                else{
                    map.put(h.getGenre(),h.getRating());
                }
            }
        }

        Map<Integer, Integer> map2 = sortByValue(map);

        List<Ott> listOtt = new ArrayList<>();

        int [] genre = new int[2];
        int i=0;
        for(Map.Entry<Integer,Integer> itr : map2.entrySet())
        {
            genre[i++] = itr.getKey();
            System.out.println(i);
        }

        for(Ott o:repo.findShowByGenre(genre[0],genre[1])){
            listOtt.add(o);
            if(listOtt.size()>15){
                return listOtt;
            }
        }
        return listOtt;
    }

    public Map<Integer, Integer>
    sortByValue(Map<Integer, Integer> hm)
    {
        // Create a list from elements of HashMa
        // Create a list from elements of HashMap
        List<Map.Entry<Integer, Integer> > list
                = new LinkedList<>(
                hm.entrySet());

        Collections.sort(
                list,
                (i1,
                 i2) -> i2.getValue().compareTo(i1.getValue()));

        // put data from sorted list to hashmap
        Map<Integer, Integer> temp
                = new LinkedHashMap<Integer, Integer>();

        int count = 0;

        for (Map.Entry<Integer, Integer> aa : list) {

            if(count > 1)
                break;

            temp.put(aa.getKey(), aa.getValue());
            count += 1;
        }
        return temp;
    }

}
