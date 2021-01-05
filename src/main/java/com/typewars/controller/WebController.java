package com.typewars.controller;

import com.google.common.collect.ImmutableMap;
import com.typewars.dao.StandardRecordsDao;
import com.typewars.dao.SurvivalRecordsDao;
import com.typewars.model.GameRecord;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

@Controller
public class WebController {
    private final StandardRecordsDao standardRecordsDao;
    private final SurvivalRecordsDao survivalRecordsDao;

    public WebController(StandardRecordsDao standardRecordsDao, SurvivalRecordsDao survivalRecordsDao) {
        this.standardRecordsDao = standardRecordsDao;
        this.survivalRecordsDao = survivalRecordsDao;
    }

    @RequestMapping("/")
    public ModelAndView index(HttpServletRequest request, HttpServletResponse response) {
        List<GameRecord> standardTopFive = standardRecordsDao.getTopFiveLast24Hours();
        List<GameRecord> survivalTopFive = survivalRecordsDao.getTopFiveLast24Hours();

        Map<String, Object> context =
                ImmutableMap.<String, Object>builder()
                        .put("standardRecords", standardTopFive)
                        .put("survivalRecords", survivalTopFive)
                        .build();

        return new ModelAndView("index", context);
    }

    @RequestMapping("/standard")
    public ModelAndView standard(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> context =
                ImmutableMap.<String, Object>builder()
                        .put("gameMode", "standard")
                        .build();

        return new ModelAndView("game", context);
    }

    @RequestMapping("/survival")
    public ModelAndView survival(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> context =
                ImmutableMap.<String, Object>builder()
                        .put("gameMode", "survival")
                        .build();

        return new ModelAndView("game", context);
    }

    @RequestMapping("/nickname")
    public ModelAndView nickname(HttpServletRequest request, HttpServletResponse response) {
        return new ModelAndView("nickname");
    }
}
