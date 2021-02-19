package com.notifications.Web;

import com.notifications.Core.EmailNotification;
import com.notifications.Core.Notification;
import com.notifications.Core.NotificationTemplate;
import com.notifications.Core.SMSNotification;
import com.notifications.Infrastructure.EmailQueueRepository;
import com.notifications.Infrastructure.NotificationTemplateRepository;
import com.notifications.Infrastructure.SMSQueueRepository;
import com.notifications.Vendors.INotificationGateway;
import com.notifications.Vendors.NotificationGateway;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;
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
    INotificationGateway notificationGateway = new NotificationGateway();

    @Autowired
    public NotificationQueueingController(EmailQueueRepository EmailQueue,
                                          SMSQueueRepository SMSQueue,
                                          NotificationTemplateRepository templateRepo) {
        this.EmailQueue = EmailQueue;
        this.SMSQueue = SMSQueue;
        this.templateRepo = templateRepo;
    }

    @PostMapping("/sendEmail")
    public boolean sendEmail(@RequestBody EmailNotification notification) {
        if (notification.getSubject() == null
                || notification.getDestination() == null || notification.getSource() == null)
            return false;
        Optional<NotificationTemplate> template = templateRepo.findById(notification.getSubject());
        if (!template.isPresent())
            return false;
        if (!checkEmail(notification.getDestination()) || !checkEmail(notification.getSource()))
            return false;
        if (!fillTemplate(template.get(), notification))
            return false;
        if (notificationGateway.send(notification))
            notification.setStatus("success");
        EmailQueue.save(notification);
        return true;
    }

    @PostMapping("/sendSMS")
    public boolean sendSMS(@RequestBody SMSNotification notification) {
        if (notification.getSubject() == null
                || notification.getDestination() == null || notification.getSource() == null)
            return false;
        Optional<NotificationTemplate> template = templateRepo.findById(notification.getSubject());
        if (!template.isPresent())
            return false;
        if (!checkPhoneNumber(notification.getDestination()) || !checkPhoneNumber(notification.getSource()))
            return false;
        if (!fillTemplate(template.get(), notification))
            return false;
        if (notificationGateway.send(notification))
            notification.setStatus("success");
        SMSQueue.save(notification);
        return true;
    }

    private boolean checkPhoneNumber(String phoneNumber) {
        if (phoneNumber.isEmpty()) return false;
        for (int i = 0; i < phoneNumber.length(); i++) {
            if (phoneNumber.charAt(i) < '0' || phoneNumber.charAt(i) > '9') return false;
        }
        return true;
    }

    private boolean checkEmail(String email) {
        return email.matches("(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"" +
                "(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])" +
                "*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9]" +
                "(?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.)" +
                "{3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:" +
                "(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])");
    }

    private boolean fillTemplate(NotificationTemplate template, Notification notification) {
        String[] placeholders = notification.getPlaceholders().split("/");
        if (template.getPlaceholdersStartingIndexes().size() != placeholders.length) return false;
        StringBuilder contentBuilder = new StringBuilder();
        int j = 0;
        for (int i = 0; i < template.getContent().length(); i++) {
            if (template.getContent().charAt(i) == '{') {
                contentBuilder.append(placeholders[j]);
                ++j;
                for (int k = i + 1; k < template.getContent().length(); k++) {
                    if (template.getContent().charAt(k) == '}') {
                        i = k;
                        break;
                    }
                }
            } else {
                contentBuilder.append(template.getContent().charAt(i));
            }
        }
        notification.setContent(contentBuilder.toString());
        return true;
    }
}
