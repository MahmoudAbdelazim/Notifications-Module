package com.notifications.Infrastructure;

import com.notifications.Core.SMSNotification;

import org.springframework.data.repository.CrudRepository;

public interface SMSQueueRepository extends CrudRepository<SMSNotification, Long> {
}
