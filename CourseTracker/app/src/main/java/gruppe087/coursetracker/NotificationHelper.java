package gruppe087.coursetracker;

import android.app.NotificationManager;
import android.content.Context;
import android.support.v7.app.NotificationCompat;
import android.view.View;

/**
 * Created by henrikbossart on 22.03.2017.
 */

public class NotificationHelper {

    private Context _CONTEXT;

    public NotificationHelper(Context context) {
        this._CONTEXT = context;
    }

    public void NotificationBuilder(){
        NotificationCompat.Builder notificationCompat = (NotificationCompat.Builder) new NotificationCompat.Builder(this._CONTEXT).setContentTitle("Test").setContentText("Test");

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(001, notificationCompat.build());
    }



}
