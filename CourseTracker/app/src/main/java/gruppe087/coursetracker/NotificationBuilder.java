package gruppe087.coursetracker;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import static android.content.Context.NOTIFICATION_SERVICE;

/**
 * Created by henrikbossart on 25.03.2017.
 */

public class NotificationBuilder {

    private Context context;

    public NotificationBuilder(Context context) {

        this.context = context;

    }

    public void Build(Context context, String title, String message) {

        Intent intent = new Intent(context, AlertBuilder.class);

        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        PendingIntent pendingIntent = PendingIntent.getActivity(context, (int)
                System.currentTimeMillis(), intent.putExtra("fromnotification", true), PendingIntent.FLAG_UPDATE_CURRENT);

        Notification notification = new Notification.Builder(context)
                .setContentTitle(title)
                .setContentText(message)
                .setSmallIcon(R.drawable.ic_checklist)
                .setContentIntent(pendingIntent)
                .build();

        sendNotification(notification);
    }

    public void sendNotification(Notification notification) {

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);

        notification.flags |= Notification.FLAG_AUTO_CANCEL;

        notificationManager.notify(0, notification);

    }
}
