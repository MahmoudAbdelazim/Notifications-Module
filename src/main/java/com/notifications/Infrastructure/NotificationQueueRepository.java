package com.notifications.Infrastructure;

import com.notifications.Core.Notification;

import org.springframework.data.repository.CrudRepository;

public interface NotificationQueueRepository extends CrudRepository<Notification, Long> {
}
