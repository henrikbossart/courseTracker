package gruppe087.coursetracker;

import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * Created by henrikbossart on 25.03.2017.
 */

public class Service extends android.app.Service {

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //TODO: enter work to be done by service
        
        return super.onStartCommand(intent, flags, startId);
    }
}
