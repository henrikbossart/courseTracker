package locationService;

import android.content.Context;
import android.location.Location;
import android.location.LocationManager;


// The Fallback tracker will use GPS or network, so if its inside a building and the GPS is not available it will use network.
// If the GPS just stops updating and becomes stale, it switches to network after a few minutes.

public class FallbackLocationTracker  implements LocationTracker, LocationTracker.LocationUpdateListener {


    private boolean isRunning;
    private ProviderLocationTracker gps;
    private ProviderLocationTracker net;
    private LocationUpdateListener listener;
    private Location lastLocation;
    private long lastTime;


    public FallbackLocationTracker(Context context) {

        this.gps = new ProviderLocationTracker(context, ProviderLocationTracker.ProviderType.GPS);
        this.net = new ProviderLocationTracker(context, ProviderLocationTracker.ProviderType.NETWORK);
    }

    // Starts both gps and network tracking if not already running
    public void start(){

        if(this.isRunning){
            return;
        }
        this.gps.start(this);
        this.net.start(this);
        this.isRunning = true;
    }

    // Starts getting updates after we have set a listener to get callbacks on
    public void start(LocationUpdateListener update) {

        start();
        this.listener = update;
    }

    // Stops the process if it's running
    public void stop(){

        if(this.isRunning){
            this.gps.stop();
            this.net.stop();
            this.isRunning = false;
            this.listener = null;
        }
    }

    // Checks if we have a valid location or not
    public boolean hasLocation(){

        // If either has a location, use it
        return this.gps.hasLocation() || this.net.hasLocation();
    }

    // Returns location if we has a location, stale otherwise
    public boolean hasPossiblyStaleLocation(){

        // If either has a location, use it
        return this.gps.hasPossiblyStaleLocation() || this.net.hasPossiblyStaleLocation();
    }

    // Retuns location and stale if unable to track current position
    public Location getLocation(){

        Location ret = this.gps.getLocation();
        if(ret == null){
            ret = this.net.getLocation();
        }
        return ret;
    }

    // Returns last location, possibly stale, who knows...
    public Location getPossiblyStaleLocation(){

        Location ret = this.gps.getPossiblyStaleLocation();
        if(ret == null){
            ret = this.net.getPossiblyStaleLocation();
        }
        return ret;
    }

    // Updates location if location has changed
    public void onUpdate(Location oldLoc, long oldTime, Location newLoc, long newTime) {

        boolean update = false;

        // Updates only if there is no last location, the provider is the same, the provider is more accurate or the old location is stale
        if(lastLocation == null){
            update = true;
        }
        else if(lastLocation != null && lastLocation.getProvider().equals(newLoc.getProvider())){
            update = true;
        }
        else if(newLoc.getProvider().equals(LocationManager.GPS_PROVIDER)){
            update = true;
        }
        else if (newTime - lastTime > 5 * 60 * 1000){
            update = true;
        }

        if(update){
            if(this.listener != null){
                this.listener.onUpdate(lastLocation, lastTime, newLoc, newTime);                  
            }
            this.lastLocation = newLoc;
            this.lastTime = newTime;
        }
    }
}