package com.typewars.recordstore.controller;

import com.typewars.recordstore.dao.SurvivalRecordsDao;
import com.typewars.recordstore.model.GameRecord;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/survival")
public class SurvivalRecordStoreController implements RecordStoreController {
  private final SurvivalRecordsDao survivalRecordsDao;

  public SurvivalRecordStoreController(SurvivalRecordsDao survivalRecordsDao) {
    this.survivalRecordsDao = survivalRecordsDao;
  }

  @Override
  @GetMapping
  public List<GameRecord> getTopFiveRecords() {
    return survivalRecordsDao.getTopFiveLast24Hours();
  }

  @Override
  @PostMapping
  public void saveRecord(@RequestBody GameRecord gameRecord) {
    survivalRecordsDao.save(gameRecord);
  }
}
