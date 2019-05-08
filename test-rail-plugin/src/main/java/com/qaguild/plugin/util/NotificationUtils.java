package com.qaguild.plugin.util;

import com.intellij.notification.Notification;
import com.intellij.notification.NotificationType;
import com.intellij.notification.Notifications;

public class NotificationUtils {

    public static void notify(String text) {
        Notifications.Bus.notify(new Notification("TestRail.Action",
                "Export to TestTail",
                "Exporting finished",
                NotificationType.INFORMATION));
    }

    public static void error(String text) {
        Notifications.Bus.notify(new Notification("TestRail.Action",
                "Export to TestTail",
                "Exporting error "+ text,
                NotificationType.ERROR));
    }
}
