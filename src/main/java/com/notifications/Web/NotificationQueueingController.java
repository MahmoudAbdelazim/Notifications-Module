package com.notifications.Web;

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
            return "send";
        } else {
            model.addAttribute("message", "Template Not Found");
            return "chooseTemplate";
        }
    }

    @PostMapping("/send")
    public String sendNotification(NotificationTemplate template, String placeholders, Model model) {
        Optional<NotificationTemplate> notificationTemplate = templateRepo.findById(template.getSubject());
        template = notificationTemplate.get();
        Notification notification = new Notification();
        notification.setLanguage(template.getLanguage());
        notification.setChannel(template.getChannel());
        notification.setSubject(template.getSubject());
        if (notification.fillTemplate(template, placeholders)) {
            System.out.println(notification);
            queue.save(notification);
            model.addAttribute("message", "Notification Sent Successfully");
            return "chooseTemplate";
        } else {
            model.addAttribute("message", "Placeholders don't match");
            return "chooseTemplate";
        }
    }
}
