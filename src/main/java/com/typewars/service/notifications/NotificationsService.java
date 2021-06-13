package com.typewars.service.notifications;

import com.typewars.dao.NotificationsDao;
import com.typewars.model.CreateNotificationRequest;
import com.typewars.model.NotificationEntity;
import com.typewars.model.NotificationsResponse;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class NotificationsService {
    private final NotificationsDao notificationsDao;

    public NotificationsService(NotificationsDao notificationsDao) {
        this.notificationsDao = notificationsDao;
    }

    public NotificationsResponse getNotifications() {
        List<NotificationEntity> notificationEntities = notificationsDao.getAll();
        List<String> messages = notificationEntities.stream()
                .sorted(Comparator.comparing(NotificationEntity::getCreatedAt).reversed())
                .map(NotificationEntity::getMessage)
                .collect(Collectors.toList());

        return new NotificationsResponse(messages);
    }

    private String buildNotificationWithIndex(int index, NotificationEntity entity) {
        return String.format("%s. %s", index, entity.getMessage());
    }

    public List<NotificationEntity> getNotificationsPrivate() {
        return notificationsDao.getAll();
    }

    public void deleteNotificationsPrivate(List<Long> ids) {
        notificationsDao.delete(ids);
    }

    public void createNotificationPrivate(CreateNotificationRequest request) {
        OffsetDateTime createdAt = OffsetDateTime.now();
        NotificationEntity notificationEntity = new NotificationEntity(request.getMessage(), createdAt);
        notificationsDao.save(notificationEntity);
    }
}
