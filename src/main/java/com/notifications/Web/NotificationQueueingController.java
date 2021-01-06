package com.notifications.Web;

import com.notifications.Core.EmailNotification;
import com.notifications.Core.Notification;
import com.notifications.Core.NotificationTemplate;
import com.notifications.Core.SMSNotification;
import com.notifications.Infrastructure.EmailQueueRepository;
import com.notifications.Infrastructure.NotificationTemplateRepository;
import com.notifications.Infrastructure.SMSQueueRepository;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/notification")
public class NotificationQueueingController {
    EmailQueueRepository EmailQueue;
    SMSQueueRepository SMSQueue;
    NotificationTemplateRepository templateRepo;

    @Autowired
    public NotificationQueueingController(EmailQueueRepository EmailQueue,
                                          SMSQueueRepository SMSQueue,
                                          NotificationTemplateRepository templateRepo) {
        this.EmailQueue = EmailQueue;
        this.SMSQueue = SMSQueue;
        this.templateRepo = templateRepo;
    }

    @PostMapping("/sendEmail")
    public void sendEmail(@RequestBody EmailNotification notification) {
        EmailQueue.save(notification);
    }

    @PostMapping("/sendSMS")
    public void sendSMS(@RequestBody SMSNotification notification) {
        SMSQueue.save(notification);
    }
}
