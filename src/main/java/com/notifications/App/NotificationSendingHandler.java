package com.notifications.App;

import com.notifications.Core.EmailNotification;
import com.notifications.Core.SMSNotification;
import com.notifications.Infrastructure.EmailQueueRepository;
import com.notifications.Infrastructure.SMSQueueRepository;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class NotificationSendingHandler {

    EmailQueueRepository emailQueue;
    SMSQueueRepository SMSQueue;

    @Autowired
    public NotificationSendingHandler(EmailQueueRepository emailQueue, SMSQueueRepository SMSQueue) {
        this.emailQueue = emailQueue;
        this.SMSQueue = SMSQueue;
    }

    public Iterable<EmailNotification> getEmailQueue() {
        return emailQueue.findAll();
    }

    public Iterable<SMSNotification> getSMSQueue() {
        return SMSQueue.findAll();
    }

    public void sendAllEmails() {
        Iterable<EmailNotification> emails = emailQueue.findAll();
        for (EmailNotification email : emails) {
            System.out.println("Sending Email:");
            System.out.println("\tTo: " + email.getDestination() +
                    "\n\tSubject: " + email.getSubject() +
                    "\n\tContent: " + email.getContent());
            System.out.println();
        }
        emailQueue.deleteAll();
    }

    public void sendAllSMS() {
        Iterable<SMSNotification> SMSs = SMSQueue.findAll();
        for (SMSNotification SMS : SMSs) {
            System.out.println("Sending SMS:");
            System.out.println("\tTo: " + SMS.getDestination() +
                    "\n\tSubject: " + SMS.getSubject() +
                    "\n\tContent: " + SMS.getContent());
            System.out.println();
        }
        SMSQueue.deleteAll();
    }
}
