package com.company;

import java.io.*;
import java.util.*;

public class FileTemplateManager implements TemplateManager{

    ArrayList<NotificationTemplate> templates = new ArrayList<>();
    String filename;

    /**
     *
     * @param filename
     * A constructor to deserialize the object inside the file
     */
    public FileTemplateManager(String filename) {
        this.filename = filename;
        File file = new File(filename);
        if (file.exists()) {
            FileInputStream inputFile = null;
            try {
                inputFile = new FileInputStream(filename);
                ObjectInputStream in = new ObjectInputStream(inputFile);
                templates = (ArrayList<NotificationTemplate>)in.readObject();
            } catch (ClassNotFoundException | IOException e) {
            }
        }
    }

    /**
     *
     * @param template
     * @return the template is already in there or not
     */
    public boolean addTemplate(NotificationTemplate template) {
        if (templates.contains(template)) {
            return false;
        }
        templates.add(template);
        updateFile();
        return true;
    }

    /**
     *
     * @param subject
     * @return whether the template is deleted or not
     */
    public boolean deleteTemplate(String subject) {
        NotificationTemplate template = null;
        for (int i = 0; i < templates.size(); i++) {
            if (templates.get(i).getSubject().equalsIgnoreCase(subject)) {
                templates.remove(i);
                updateFile();
                return true;
            }
        }
        return false;
    }

    /**
     *
     * @param subject
     * @param updatedTemplate
     * @return whether the template is updated or not
     */
    public boolean updateTemplate(String subject, NotificationTemplate updatedTemplate) {
        NotificationTemplate template = null;
        for (int i = 0; i < templates.size(); i++) {
            if (templates.get(i).getSubject().equalsIgnoreCase(subject)) {
                templates.set(i, updatedTemplate);
                updateFile();
                return true;
            }
        }
        return false;
    }

    /**
     *
     * @param subject
     * @return whether it could find the template we want to return or not
     */
    public NotificationTemplate getTemplate(String subject) {
        NotificationTemplate template = null;
        for (int i = 0; i < templates.size(); i++) {
            if (templates.get(i).getSubject().equalsIgnoreCase(subject)) {
                return templates.get(i);
            }
        }
        return null;
    }

    /**
     *
     * @return the list of templates
     */
    public ArrayList<NotificationTemplate> getAllTemplates() {
        return templates;
    }

    /**
     * a function to serialize the object in the file
     */
    private void updateFile() {
        FileOutputStream file = null;
        try {
            file = new FileOutputStream(filename);
            ObjectOutputStream out = new ObjectOutputStream(file);
            out.writeObject(templates);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
