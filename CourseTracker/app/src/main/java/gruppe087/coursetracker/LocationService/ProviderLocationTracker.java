package locationService;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;


public class ProviderLocationTracker implements LocationListener, LocationTracker {


    // The minimum distance to change Updates in meters
    private static final long MIN_UPDATE_DISTANCE = 10; 

    // The minimum time between updates in milliseconds
    private static final long MIN_UPDATE_TIME = 1000 * 60; 

    public enum ProviderType{
        NETWORK,
        GPS
    };    

    private LocationManager lm;
    private String provider;
    private Location lastLocation;
    private long lastTime;
    private boolean isRunning;
    private LocationUpdateListener listener;


    public ProviderLocationTracker(Context context, ProviderType type) {
        this.lm = (LocationManager)context.getSystemService(Context.LOCATION_SERVICE);
        if(type == ProviderType.NETWORK){
            this.provider = LocationManager.NETWORK_PROVIDER;
        }
        else{
            this.provider = LocationManager.GPS_PROVIDER;
        }
    }

    // Starts getting updates on location
    public void start(){
        if(this.isRunning){
            // Already running, do nothing
            return;
        }

        // The provider is on, so start getting updates.  Update current location
        this.isRunning = true;
        this.lm.requestLocationUpdates(provider, MIN_UPDATE_TIME, MIN_UPDATE_DISTANCE, this);
        this.lastLocation = null;
        this.lastTime = 0;
        return;
    }

    // Starts getting updates after we have set a listener to get callbacks on
    public void start(LocationUpdateListener update) {
        start();
        this.listener = update;

    }

    // Stops the process if it's running
    public void stop(){
        if(this.isRunning){
            this.lm.removeUpdates(this);
            this.isRunning = false;
            this.listener = null;
        }
    }

    // Checks if we have a valid location or not
    public boolean hasLocation(){
        if(this.lastLocation == null){
            return false;
        }
        if(System.currentTimeMillis() - this.lastTime > 5 * MIN_UPDATE_TIME){
            return false; //stale
        }
        return true;
    }

    // Returns location if we has a location, stale otherwise
    public boolean hasPossiblyStaleLocation(){
        if(this.lastLocation != null){
            return true;
        }
        return this.lm.getLastKnownLocation(provider)!= null;
    }

    // Retuns location and if unable to track current position
    public Location getLocation(){
        if(this.lastLocation == null){
            return null;
        }
        if(System.currentTimeMillis() - this.lastTime > 5 * MIN_UPDATE_TIME){
            return null;
        }
        return this.lastLocation;
    }

    // Returns last location, possibly stale, who knows...
    public Location getPossiblyStaleLocation(){
        if(this.lastLocation != null){
            return this.lastLocation;
        }
        return this.lm.getLastKnownLocation(provider);
    }

    // Called when the location has changed.
    public void onLocationChanged(Location newLoc) {
        long now = System.currentTimeMillis();
        if(this.listener != null){
            this.listener.onUpdate(lastLocation, lastTime, newLoc, now);
        }
        this.lastLocation = newLoc;
        this.lastTime = now;
    }

    // Called when the provider is disabled by the user.
    public void onProviderDisabled(String arg0) {
    
    }

    // Called when the provider is enabled by the user.
    public void onProviderEnabled(String arg0) {

    }

    // Called when the provider status changes.
    public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
    }
}