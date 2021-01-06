package com.notifications.Core;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;

@Data
@RequiredArgsConstructor
@Entity
public class Notification implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String subject;

    private String content;

    private Language language;

    private Channel channel;

    private String destination;

    public boolean fillTemplate(NotificationTemplate template, String placeholdersStr) {
        String[] placeholders = placeholdersStr.split("/");
        if (template.getPlaceholdersStartingIndexes().size() != placeholders.length) return false;
        StringBuilder contentBuilder = new StringBuilder();
        int j = 0;
        for (int i = 0; i < template.getContent().length(); i++) {
            if (template.getContent().charAt(i) == '{') {
                contentBuilder.append(placeholders[j]);
                ++j;
                for (int k = i + 1; k < template.getContent().length(); k++) {
                    if (template.getContent().charAt(k) == '}') {
                        i = k;
                        break;
                    }
                }
            } else {
                contentBuilder.append(template.getContent().charAt(i));
            }
        }
        content = contentBuilder.toString();
        return true;
    }
}
