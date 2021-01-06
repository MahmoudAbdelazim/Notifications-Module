package com.notifications.Web;

import com.notifications.Core.NotificationTemplate;
import com.notifications.Infrastructure.NotificationTemplateRepository;
import com.notifications.Web.Exceptions.TemplateErrorResponse;
import com.notifications.Web.Exceptions.TemplateNotFoundException;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/template")
public class NotificationTemplateController {
    NotificationTemplateRepository repo;

    @Autowired
    public NotificationTemplateController(NotificationTemplateRepository repo) {
        this.repo = repo;
    }

    @GetMapping("/get/{subject}")
    public NotificationTemplate getTemplate(@PathVariable String subject) {
        Optional<NotificationTemplate> template = repo.findById(subject);
        return template.orElse(null);
    }

    @GetMapping("/getAll")
    public Iterable<NotificationTemplate> getAllTemplates() {
        return repo.findAll();
    }

    @PostMapping("/add")
    public boolean addTemplate(@RequestBody NotificationTemplate template) {
        if (template.editContent(template.getContent())) {
            if (!repo.findById(template.getSubject()).isPresent()) {
                if (template.getChannel() != null && template.getLanguage() != null) {
                    repo.save(template);
                    return true;
                }
            }
        }
        return false;
    }

    @PutMapping("/update/{subject}")
    public boolean updateTemplate(@PathVariable String subject, @RequestBody NotificationTemplate template) {
        if (!repo.findById(subject).isPresent())
            return false;

        if (template.editContent(template.getContent())) {
            if (template.getChannel() != null && template.getLanguage() != null) {
                repo.save(template);
                return true;
            }
        }
        return false;
    }

    @DeleteMapping("/delete/{subject}")
    public boolean deleteTemplate(@PathVariable String subject) throws CloneNotSupportedException {
        Optional<NotificationTemplate> template = repo.findById(subject);
        if (!template.isPresent())
            return false;
        repo.deleteById(subject);
        return true;
    }
}
