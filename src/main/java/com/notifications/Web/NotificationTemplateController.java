package com.notifications.Web;

import com.notifications.Core.Channel;
import com.notifications.Core.Language;
import com.notifications.Core.NotificationTemplate;
import com.notifications.Infrastructure.NotificationTemplateRepository;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/template")
public class NotificationTemplateController {
    NotificationTemplateRepository repo;

    @Autowired
    public NotificationTemplateController(NotificationTemplateRepository repo) {
        this.repo = repo;
    }

    public void addCommonAttributes(Model model, String message) {
        List<String> languages = new ArrayList<>();
        for (Language lang : Language.values()) {
            languages.add(lang.toString());
        }
        List<String> channels = new ArrayList<>();
        for (Channel chan : Channel.values()) {
            channels.add(chan.toString());
        }
        model.addAttribute("languages", languages);
        model.addAttribute("channels", channels);
        model.addAttribute("message", message);
    }

    @GetMapping("/add")
    public String addTemplate(Model model) {
        model.addAttribute("template", new NotificationTemplate());
        addCommonAttributes(model, "");
        return "addTemplate";
    }

    @PostMapping("/add")
    public String processAddTemplate(NotificationTemplate notificationTemplate, Model model) {
        if (notificationTemplate.editContent(notificationTemplate.getContent())) {
            Optional<NotificationTemplate> template = repo.findById(notificationTemplate.getSubject());
            if (template.isPresent()) {
                model.addAttribute("template", new NotificationTemplate());
                addCommonAttributes(model, "A Template with the same subject is present");
            } else {
                repo.save(notificationTemplate);
                model.addAttribute("template", new NotificationTemplate());
                addCommonAttributes(model, "Template Added Successfully");
            }
        } else {
            model.addAttribute("template", new NotificationTemplate());
            addCommonAttributes(model, "Placeholders don't match");
        }
        return "addTemplate";
    }

    @GetMapping("/search")
    public String getTemplate(Model model) {
        model.addAttribute("id", "");
        return "searchTemplate";
    }

    @PostMapping("/search")
    public String searchTemplate(String id, Model model) {
        Optional<NotificationTemplate> template = repo.findById(id);
        if (template.isPresent()) {
            NotificationTemplate temp = template.get();
            model.addAttribute("template", temp);
            addCommonAttributes(model, "");
            return "updateTemplate";
        } else {
            model.addAttribute("message", "Template Not Found");
            return "searchTemplate";
        }
    }

    @PostMapping("/update")
    public String updateTemplate(NotificationTemplate template, Model model) {
        if (template.editContent(template.getContent())) {
            template.setSubject(template.getSubject());
            repo.save(template);
            return "redirect:/template/search";
        } else {
            model.addAttribute("template", template);
            addCommonAttributes(model, "Placeholders don't match");
            return "updateTemplate";
        }
    }

    @PostMapping("/delete")
    public String deleteTemplate(NotificationTemplate template) {
        repo.deleteById(template.getSubject());
        return "redirect:/template/search";
    }

    @GetMapping("/getAll")
    public String getAllTemplates(Model model) {
        Iterable<NotificationTemplate> templates = repo.findAll();
        model.addAttribute("templates", templates);
        return "viewAllTemplates";
    }
}
