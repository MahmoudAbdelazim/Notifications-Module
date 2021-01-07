package com.notifications.Vendors;

import com.notifications.Core.Notification;

import java.util.Random;

public interface INotificationGateway {
    boolean send(Notification notification);
}
