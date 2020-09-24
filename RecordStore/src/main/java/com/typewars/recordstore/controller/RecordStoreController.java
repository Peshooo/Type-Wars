package com.typewars.recordstore.controller;

import com.typewars.recordstore.model.GameRecord;

import java.util.List;

public interface RecordStoreController {
  List<GameRecord> getTopFiveRecords();
  void saveRecord(GameRecord gameRecord);
}