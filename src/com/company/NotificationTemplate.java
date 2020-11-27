package com.company;

import java.io.*;
import java.util.*;

public class NotificationTemplate implements Serializable {
    private String subject;
    private String content;
    private Language language;
    private Channel channel;
    private ArrayList<Integer> placeholdersStartingIndexes;
    private ArrayList<Integer> placeholdersEndingIndexes;

    /**
     *
     * @param subject of the notification template
     * @param language of the notification template
     * @param channel of the notification template
     */
    public NotificationTemplate(String subject, Language language, Channel channel) {
        this.subject = subject;
        this.language = language;
        this.channel = channel;
        placeholdersStartingIndexes = new ArrayList<>();
        placeholdersEndingIndexes = new ArrayList<>();
    }

    /**
     *
     * @param content
     * @return a function to add the place holder in the content string to the both arrays.
     */
    public boolean editContent(String content) {
        this.content = content;
        placeholdersStartingIndexes.clear();
        placeholdersEndingIndexes.clear();
        for (int i = 0; i < content.length(); i++) {
            if (content.charAt(i) == '{') {
                placeholdersStartingIndexes.add(i);
                for (int j = i + 1; j < content.length(); j++) {
                    if (content.charAt(j) == '}') {
                        placeholdersEndingIndexes.add(j);
                        i = j;
                        break;
                    } else if (content.charAt(j) == '{') {
                        break;
                    }
                }
            }
        }
        if (placeholdersStartingIndexes.size() != placeholdersEndingIndexes.size()) {
            placeholdersStartingIndexes.clear();
            placeholdersEndingIndexes.clear();
            this.content = "";
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Notification Template {" +
                "subject='" + subject + '\'' +
                ", content='" + content + '\'' +
                ", Language=" + language +
                ", placeholdersStartingIndexes=" + placeholdersStartingIndexes +
                ", placeholdersEndingIndexes=" + placeholdersEndingIndexes +
                ", Channel=" + channel +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        NotificationTemplate template = (NotificationTemplate) o;

        return subject != null ? subject.equalsIgnoreCase(template.subject) : template.subject == null;
    }

    /**
     * getter for the subject
     * @return the subject
     */
    public String getSubject() {
        return subject;
    }

    /**
     * getter for the content
     * @return the content
     */
    public String getContent() {
        return content;
    }

    /**
     * getter for the language
     * @return the language
     */
    public Language getLanguage() {
        return language;
    }

    /**
     * getter for the channel
     * @return the channel
     */
    public Channel getChannel() {
        return channel;
    }

    /**
     * getter for the placeholderstartingIndexes
     * @return
     */
    public ArrayList<Integer> getPlaceholdersStartingIndexes() {
        return placeholdersStartingIndexes;
    }

    /**
     * getter for the placeHoldersEndingIndexes
     * @return
     */
    public ArrayList<Integer> getPlaceholdersEndingIndexes() {
        return placeholdersEndingIndexes;
    }
}



