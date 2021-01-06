package com.notifications.Web;

import com.notifications.Core.Channel;
import com.notifications.Core.Notification;
import com.notifications.Core.NotificationTemplate;
import com.notifications.Infrastructure.NotificationQueueRepository;
import com.notifications.Infrastructure.NotificationTemplateRepository;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/notification")
public class NotificationQueueingController {
    NotificationQueueRepository queue;
    NotificationTemplateRepository templateRepo;

    @Autowired
    public NotificationQueueingController(NotificationQueueRepository queue, NotificationTemplateRepository templateRepo) {
        this.queue = queue;
        this.templateRepo = templateRepo;
    }

    @GetMapping("/chooseTemplate")
    public String viewChooseTemplate(Model model) {
        model.addAttribute("templateSubject", "");
        model.addAttribute("message", "");
        return "chooseTemplate";
    }

    @PostMapping("/message")
    public String enterMessage(String templateSubject, Model model) {
        Optional<NotificationTemplate> template = templateRepo.findById(templateSubject);
        if (template.isPresent()) {
            NotificationTemplate temp = template.get();
            model.addAttribute("template", temp);
            model.addAttribute("placeholders", "");
            if (temp.getChannel() == Channel.Email) {
                model.addAttribute("type", "email");
            } else {
                model.addAttribute("type", "sms");
            }
            model.addAttribute("destination", "");
            return "send";
        } else {
            model.addAttribute("message", "Template Not Found");
            return "chooseTemplate";
        }
    }

    @PostMapping("/send")
    public String sendNotification(NotificationTemplate template, String placeholders, String destination, Model model) {
        Optional<NotificationTemplate> notificationTemplate = templateRepo.findById(template.getSubject());
        template = notificationTemplate.get();
        Notification notification = new Notification();
        notification.setLanguage(template.getLanguage());
        notification.setChannel(template.getChannel());
        notification.setSubject(template.getSubject());
        notification.setDestination(destination);

        boolean valid = true;
        if (template.getChannel() == Channel.SMS) {
            for (int i = 0; i < destination.length(); i++) {
                if (destination.charAt(i) < '0' || destination.charAt(i) > '9') {
                    valid = false;
                    model.addAttribute("message", "Invalid Phone Number (must use digits only)");
                    break;
                }
            }
        } else if (template.getChannel() == Channel.Email) {
            if (!checkEmail(destination)) {
                valid = false;
                model.addAttribute("message", "Invalid Email!");
            }
        }
        if (!notification.fillTemplate(template, placeholders)) {
            valid = false;
            model.addAttribute("message", "Placeholders don't match");

        }
        if (valid) {
            queue.save(notification);
            model.addAttribute("message", "Notification Sent Successfully");
        }
        return "chooseTemplate";
    }

    private boolean checkEmail(String email) {
        return email.matches("(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"" +
                "(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])" +
                "*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9]" +
                "(?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.)" +
                "{3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:" +
                "(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])");
    }
}
