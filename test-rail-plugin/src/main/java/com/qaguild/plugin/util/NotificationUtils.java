package com.qaguild.plugin.util;

import com.intellij.notification.Notification;
import com.intellij.notification.NotificationType;
import com.intellij.notification.Notifications;

public class NotificationUtils {

    public static void notify(String text) {
        Notifications.Bus.notify(new Notification("TestRail.Action",
                "Export to TestTail",
                "Finished exporting [" + text + "]",
                NotificationType.INFORMATION));
    }
}
