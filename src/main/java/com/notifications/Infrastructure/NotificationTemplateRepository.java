package com.notifications.Infrastructure;

import com.notifications.Core.NotificationTemplate;

import org.springframework.data.repository.CrudRepository;

public interface NotificationTemplateRepository extends CrudRepository<NotificationTemplate, String> {

}
