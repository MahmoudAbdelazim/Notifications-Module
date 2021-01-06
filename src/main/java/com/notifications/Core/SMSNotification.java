package com.notifications.Core;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import javax.persistence.Entity;

@Data
@RequiredArgsConstructor
@Entity(name = "SMS_Queue")
public class SMSNotification extends Notification {

}
