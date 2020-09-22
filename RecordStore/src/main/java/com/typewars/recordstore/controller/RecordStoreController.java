package com.typewars.recordstore.controller;

import com.typewars.recordstore.dao.SurvivalRecordsDao;
import com.typewars.recordstore.model.SurvivalRecord;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class RecordStoreController {
  private final SurvivalRecordsDao survivalRecordsDao;

  public RecordStoreController(SurvivalRecordsDao survivalRecordsDao) {
    this.survivalRecordsDao = survivalRecordsDao;
  }

  @GetMapping("/survival")
  public List<SurvivalRecord> getTopFiveSurvivalRecords() {
    return survivalRecordsDao.getTopFiveLast24Hours();
  }

  @PostMapping("/survival")
  public void saveSurvivalRecord(@RequestBody SurvivalRecord survivalRecord) {
    survivalRecordsDao.save(survivalRecord);
  }
}