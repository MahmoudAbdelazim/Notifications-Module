package com.notifications.Core;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@Data
@RequiredArgsConstructor
@MappedSuperclass
public class Notification {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String subject;

    private String destination;

    private String placeholders;
}
