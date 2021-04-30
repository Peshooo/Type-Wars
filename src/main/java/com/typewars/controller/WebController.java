package com.typewars.controller;

import com.google.common.collect.ImmutableMap;
import com.typewars.aspect.DailyCounter;
import com.typewars.dao.StandardRecordsDao;
import com.typewars.dao.SurvivalRecordsDao;
import com.typewars.model.GameRecord;
import com.typewars.service.notifications.NotificationsService;
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
    private final NotificationsService notificationsService;

    public WebController(StandardRecordsDao standardRecordsDao, SurvivalRecordsDao survivalRecordsDao, NotificationsService notificationsService) {
        this.standardRecordsDao = standardRecordsDao;
        this.survivalRecordsDao = survivalRecordsDao;
        this.notificationsService = notificationsService;
    }

    @RequestMapping("/")
    @DailyCounter(key = "INDEX_OPENED")
    public ModelAndView index(HttpServletRequest request, HttpServletResponse response) {
        List<GameRecord> standardTopFive = standardRecordsDao.getTopFiveLast24Hours();
        List<GameRecord> survivalTopFive = survivalRecordsDao.getTopFiveLast24Hours();

        Map<String, Object> context =
                ImmutableMap.<String, Object>builder()
                        .put("standardRecords", standardTopFive)
                        .put("survivalRecords", survivalTopFive)
                        .put("notifications", notificationsService.getNotifications().getMessages())
                        .put("notificationsAll", notificationsService.getNotifications().getAll())
                        .build();

        return new ModelAndView("index", context);
    }

    @RequestMapping("/standard")
    @DailyCounter(key = "STANDARD_OPENED")
    public ModelAndView standard(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> context =
                ImmutableMap.<String, Object>builder()
                        .put("gameMode", "standard")
                        .put("notifications", notificationsService.getNotifications().getMessages())
                        .put("notificationsAll", notificationsService.getNotifications().getAll())
                        .build();

        return new ModelAndView("game", context);
    }

    @RequestMapping("/survival")
    @DailyCounter(key = "SURVIVAL_OPENED")
    public ModelAndView survival(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> context =
                ImmutableMap.<String, Object>builder()
                        .put("gameMode", "survival")
                        .put("notifications", notificationsService.getNotifications().getMessages())
                        .put("notificationsAll", notificationsService.getNotifications().getAll())
                        .build();

        return new ModelAndView("game", context);
    }

    @RequestMapping("/nickname")
    @DailyCounter(key = "NICKNAME_OPENED")
    public ModelAndView nickname(HttpServletRequest request, HttpServletResponse response) {
        return new ModelAndView("nickname");
    }
}
