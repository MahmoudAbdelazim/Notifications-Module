package com.notifications.Core;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.Entity;

@Data
@RequiredArgsConstructor
@Entity(name = "EmailQueue")
public class EmailNotification extends Notification {

}
