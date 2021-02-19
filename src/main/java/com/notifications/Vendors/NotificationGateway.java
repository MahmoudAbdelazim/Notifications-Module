package com.notifications.Vendors;

import com.notifications.Core.Notification;

import java.util.Random;

public class NotificationGateway implements INotificationGateway{
    @Override
    public boolean send(Notification notification)  {
        Random rd = new Random();
        int num = rd.nextInt() % (9) + 1;
        return num > 2;
    }
}
