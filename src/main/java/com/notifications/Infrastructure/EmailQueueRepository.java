package com.notifications.Infrastructure;

import com.notifications.Core.EmailNotification;

import org.springframework.data.repository.CrudRepository;

public interface EmailQueueRepository extends CrudRepository<EmailNotification, Long> {
}
