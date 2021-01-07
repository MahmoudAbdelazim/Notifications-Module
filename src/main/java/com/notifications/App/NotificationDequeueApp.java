package com.notifications.App;

import com.notifications.Core.EmailNotification;
import com.notifications.Core.SMSNotification;
import com.notifications.Infrastructure.EmailQueueRepository;
import com.notifications.Infrastructure.SMSQueueRepository;
import lombok.extern.slf4j.Slf4j;

import java.util.Scanner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class NotificationDequeueApp implements CommandLineRunner {

    EmailQueueRepository emailQueue;
    SMSQueueRepository SMSQueue;

    @Autowired
    public NotificationDequeueApp(EmailQueueRepository emailQueue, SMSQueueRepository SMSQueue) {
        this.emailQueue = emailQueue;
        this.SMSQueue = SMSQueue;
    }

    @Override
    public void run(String... args) throws Exception {
        NotificationSendingHandler sendingHandler = new NotificationSendingHandler(emailQueue, SMSQueue);
        Scanner in = new Scanner(System.in);
        while (true) {
            System.out.println("==================================================");
            System.out.println("1- View The Email Queue" +
                    "\n2- View The SMS Queue" +
                    "\n3- Send Emails in the Email Queue" +
                    "\n4- Send SMSs in the SMS Queue" +
                    "\n5- Clear Email Queue" +
                    "\n6- Clear SMS Queue");
            int option = in.nextInt();
            if (option == 1) {
                Iterable<EmailNotification> emails = sendingHandler.getEmailQueue();
                System.out.println("Email Queue:");
                for (EmailNotification email : emails) {
                    System.out.println("\tTo: " + email.getDestination() +
                            "\n\tSubject: " + email.getSubject() +
                            "\n\tContent: " + email.getContent() +
                            "\n\tStatus: " + email.getStatus());
                    System.out.println();
                }
            } else if (option == 2) {
                Iterable<SMSNotification> SMSs = sendingHandler.getSMSQueue();
                System.out.println("SMS Queue:");
                for (SMSNotification SMS : SMSs) {
                    System.out.println("\tTo: " + SMS.getDestination() +
                            "\n\tSubject: " + SMS.getSubject() +
                            "\n\tContent: " + SMS.getContent() +
                            "\n\tStatus: " + SMS.getStatus());
                    System.out.println();
                }
            } else if (option == 3) {
                sendingHandler.sendAllEmails();
                System.out.println("All Emails in the email queue have been sent");
            } else if (option == 4) {
                sendingHandler.sendAllSMS();
                System.out.println("All SMSs in the SMS queue have been sent");
            } else if (option == 5) {
                sendingHandler.clearEmailQueue();
                System.out.println("Email Queue is cleared");
            } else if (option == 6) {
                sendingHandler.clearSMSQueue();
                System.out.println("SMS Queue is cleared");
            } else {
                System.out.println("Invalid Option!");
            }
        }
    }
}
