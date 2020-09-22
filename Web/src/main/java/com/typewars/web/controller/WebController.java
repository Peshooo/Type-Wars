package com.typewars.web.controller;

import com.google.common.collect.ImmutableMap;
import com.typewars.web.recordstore.RecordStoreClient;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Controller
public class WebController {
  private final RecordStoreClient recordStoreClient;

  public WebController(RecordStoreClient recordStoreClient) {
    this.recordStoreClient = recordStoreClient;
  }

  @RequestMapping("/")
  public ModelAndView index(HttpServletRequest request, HttpServletResponse response) {
    Map<String, Object> context =
        ImmutableMap.<String, Object>builder()
            .put("recordStoreClient", recordStoreClient)
            .build();

    return new ModelAndView("index", context);
  }

  @RequestMapping("/survival")
  public ModelAndView survival(HttpServletRequest request, HttpServletResponse response) {
    return new ModelAndView("survival");
  }

  @RequestMapping("/nickname")
  public ModelAndView nickname(HttpServletRequest request, HttpServletResponse response) {
    return new ModelAndView("nickname");
  }
}
