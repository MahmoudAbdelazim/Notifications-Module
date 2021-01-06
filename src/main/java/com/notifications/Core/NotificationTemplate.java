package com.notifications.Core;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


@Data
@RequiredArgsConstructor
@Entity
public class NotificationTemplate implements Serializable {

    @Id
    private String subject;

    private String content;

    private Language language;

    @ElementCollection
    private List<Integer> placeholdersStartingIndexes = new ArrayList<>();

    @ElementCollection
    private List<Integer> placeholdersEndingIndexes = new ArrayList<>();

    public boolean adjustContent(String content) {
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
            return false;
        }
        return true;
    }
}



