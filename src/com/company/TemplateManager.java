package com.company;

import java.util.*;

/**
 * an interface for the templateManager
 */
public interface TemplateManager {
    // un-implemented functions for the templateManager
    public boolean addTemplate(NotificationTemplate template);

    public boolean deleteTemplate(String subject);

    public boolean updateTemplate(String subject, NotificationTemplate updatedTemplate);

    public NotificationTemplate getTemplate(String subject);

    public ArrayList<NotificationTemplate> getAllTemplates();
}
