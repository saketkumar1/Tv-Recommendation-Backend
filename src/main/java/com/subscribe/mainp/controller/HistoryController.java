package com.subscribe.mainp.controller;

import com.subscribe.mainp.dto.HistoryDto;
import com.subscribe.mainp.entity.History;
import com.subscribe.mainp.repository.HistoryRepository;
import com.subscribe.mainp.service.HistoryImpl;
import com.subscribe.mainp.service.HistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/history")
public class HistoryController {

    @Autowired
    HistoryImpl history;

    @Autowired
    HistoryService historyService;
    
    @Autowired
    HistoryRepository historyRepository;

    @PostMapping("/")
    public ResponseEntity<History> createHistory(@RequestBody HistoryDto hist)
    {
        History h = null;
        try {
            h = history.saveHistory(hist);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }
        catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping(value = "/{userid}")
    public ResponseEntity<List<History>> getHistoryByUserId(@PathVariable int userid){
        List<History> h = history.getHistoryByUserId(userid);
        if (h.size() == 0)
        {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        return new ResponseEntity<>(h,HttpStatus.OK);

    }

  @DeleteMapping("/{historyid}")
    public ResponseEntity<String> deleteHistory(@PathVariable int historyid)
    {
        if (historyRepository.existsById(historyid)) {
            historyRepository.deleteById(historyid);
            return new ResponseEntity<>("Deleted Successfully",HttpStatus.OK);
        }

        else
            return new ResponseEntity<>("Failed to delete", HttpStatus.INTERNAL_SERVER_ERROR);

    }
}
