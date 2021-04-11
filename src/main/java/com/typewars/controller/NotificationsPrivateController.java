package com.typewars.controller;

import com.typewars.model.CreateNotificationRequest;
import com.typewars.model.NotificationEntity;
import com.typewars.service.notifications.NotificationsService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/v1/private/notifications")
public class NotificationsPrivateController {
    private final NotificationsService notificationsService;

    public NotificationsPrivateController(NotificationsService notificationsService) {
        this.notificationsService = notificationsService;
    }

    @GetMapping
    @ResponseBody
    public List<NotificationEntity> getNotificationsPrivate() {
        return notificationsService.getNotificationsPrivate();
    }

    @DeleteMapping("/{ids}")
    @ResponseBody
    public void deletePrivate(@PathVariable List<Long> ids) {
        notificationsService.deleteNotificationsPrivate(ids);
    }

    @PostMapping
    @ResponseBody
    public void createNotificationPrivate(@RequestBody CreateNotificationRequest request) {
        notificationsService.createNotificationPrivate(request);
    }
}
