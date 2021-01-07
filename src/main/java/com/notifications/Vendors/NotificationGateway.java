package com.notifications.Vendors;

import com.notifications.Core.Notification;

import java.util.Random;

public class NotificationGateway implements INotificationGateway{
    @Override
    public boolean send(Notification notification)  {
        Random rd = new Random();
        return rd.nextBoolean();
    }
}
