package locationService;

import android.location.Location;


public interface LocationTracker {

    public interface LocationUpdateListener{

        public void onUpdate(Location oldLoc, long oldTime, Location newLoc, long newTime);
    }

    // Two start methods, and one we can set a listener to get callbacks on
    public void start();
    public void start(LocationUpdateListener update);

    // Stops the process if it's running
    public void stop();

    // Checks if we have a valid location or not
    public boolean hasLocation();

    // Returns location if we has a location, stale otherwise
    public boolean hasPossiblyStaleLocation();

    // Retuns location and stale if unable to track current position
    public Location getLocation();

    // Returns last location, possibly stale, who knows...
    public Location getPossiblyStaleLocation();
}