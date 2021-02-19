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
    Scanner in = new Scanner(System.in);

    @Autowired
    public NotificationDequeueApp(EmailQueueRepository emailQueue, SMSQueueRepository SMSQueue) {
        this.emailQueue = emailQueue;
        this.SMSQueue = SMSQueue;
    }

    @Override
    public void run(String... args) throws Exception {
        while (true) {
            System.out.println("==================================================");
            System.out.println("1- View Emails" +
                    "\n2- View SMSs");
            int option = in.nextInt();
            if (option == 1) {
                viewEmails();
            } else if (option == 2) {
                viewSMSs();
            } else {
                System.out.println("Invalid Option!");
            }
        }
    }

    private void viewEmails() {
        Iterable<EmailNotification> emails = emailQueue.findAll();
        System.out.print("Enter your Email: ");
        String emailInput = in.next();
        System.out.println("Your Emails:");
        for (EmailNotification email : emails) {
            if (email.getDestination().equalsIgnoreCase(emailInput) && email.getStatus().equals("success")) {
                System.out.println("\tFrom: " + email.getSource() +
                        "\n\tTo: " + email.getDestination() +
                        "\n\tSubject: " + email.getSubject() +
                        "\n\tContent: " + email.getContent());
                System.out.println();
            }
        }
    }

    private void viewSMSs() {
        Iterable<SMSNotification> SMSs = SMSQueue.findAll();
        System.out.print("Enter your Phone Number: ");
        String phoneInput = in.next();
        System.out.println("Your SMSs:");
        for (SMSNotification SMS : SMSs) {
            if (SMS.getDestination().equalsIgnoreCase(phoneInput) && SMS.getStatus().equals("success")) {
                System.out.println("\tFrom: " + SMS.getSource() +
                        "\n\tTo: " + SMS.getDestination() +
                        "\n\tSubject: " + SMS.getSubject() +
                        "\n\tContent: " + SMS.getContent());
                System.out.println();
            }
        }
    }
}
