package locationService;

// Regarding Location Service: To run the class in poll mode, just call start(). To run it in update mode, call start(Listener).

public static void main(String[] args) {

    /*
    MÅ HENTE UT ROM FRA DATABASEN LAGRET PÅ MOBILEN SOM ER ARGUMENT I COMPARATOR I LOCATIONCOMPARER-KLASSEN
    LocationTrackerInterface, ProviderLocationTracker og FallbackLocationTracker er lagd på bakgrunn av LS-API.

    Sider til hjelp:
    https://developer.android.com/guide/topics/location/index.html
    https://developer.android.com/guide/topics/location/strategies.html
    https://developer.android.com/training/location/index.html
    */
    
    String room = "THAT";

    // Starts location service
    ProviderLocationTracker ls = new ProviderLocationTracker();
    ls.start();

    // Get location and check if it's valid
    Location location = ls.getLocation();
    if(location != null){
        System.out.println("Yeah, we have a valid location!");
    }

    // Compare current location and if the position is inside a room where the lecture is
    LocationComparer lc = new LocationComparer(location.getLatitude(), location.getLongitude());
    
    // If comparator == true -> Student is attending lecture
    if(lc.comparator(room){
        System.out.println("Student is attending lecture");
    }