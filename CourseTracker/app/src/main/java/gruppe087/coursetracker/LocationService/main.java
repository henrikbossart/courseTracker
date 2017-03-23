package locationService;

// To run the class in poll mode, just call start(). To run it in update mode, call start(Listener).

public static void main(String[] args) {
    ProviderLocationTracker ls = new ProviderLocationTracker();
    ls.start();
    Location location = ls.getLocation();
    
