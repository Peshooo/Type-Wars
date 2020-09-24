package com.typewars.recordstore.controller;

import com.typewars.recordstore.dao.StandardRecordsDao;
import com.typewars.recordstore.model.GameRecord;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/standard")
public class StandardRecordStoreController implements RecordStoreController {
  private final StandardRecordsDao standardRecordsDao;

  public StandardRecordStoreController(StandardRecordsDao standardRecordsDao) {
    this.standardRecordsDao = standardRecordsDao;
  }

  @Override
  @GetMapping
  public List<GameRecord> getTopFiveRecords() {
    return standardRecordsDao.getTopFiveLast24Hours();
  }

  @Override
  @PostMapping
  public void saveRecord(@RequestBody GameRecord gameRecord) {
    standardRecordsDao.save(gameRecord);
  }
}