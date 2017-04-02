package locationService;

import java.util.HashMap;
import java.util.Map;


public class LocationComparer {

    private double latitude, longitude;
    private static final double radius = 0.0001; // Approx 12-13 meter

    // Hashmap of all rooms with latitude and longitude
    private static final Map<String,Double> rooms = new HashMap<String, Double>();
    static {
        rooms.put("B1-LA", 10.406011);
        rooms.put("B1-LO", 63.417341);
        rooms.put("EL2-LA", 10.401789);
        rooms.put("EL2-LO", 63.418549);
        rooms.put("EL3-LA", 10.401403);
        rooms.put("EL3-LO", 63.418598); 
        rooms.put("F1-LA", 10.403184);
        rooms.put("F1-LO", 63.416552);
        rooms.put("F6-LA", 10.403194);
        rooms.put("F6-LO", 63.417125);
        rooms.put("H3-LA", 10.402953);
        rooms.put("H3-LO", 63.419439);
        rooms.put("H3 Rom 511-LA", 10.402953);
        rooms.put("H3 Rom 511-LO", 63.419439);
        rooms.put("K5-LA", 10.406832);
        rooms.put("K5-LO", 63.41628);
        rooms.put("MA24-LA", 10.409927);
        rooms.put("MA24-LO", 63.4155);
        rooms.put("R1-LA", 10.406488);
        rooms.put("R1-LO", 63.415937);
        rooms.put("R2-LA", 10.407132);
        rooms.put("R2-LO", 63.415966);
        rooms.put("R3-LA", 10.406515);
        rooms.put("R3-LO", 63.415543);
        rooms.put("R5-LA", 10.405292);
        rooms.put("R5-LO", 63.415661);
        rooms.put("R7-LA", 10.4047079);
        rooms.put("R7-LO", 63.415601);
        rooms.put("R9-LA", 10.404305);
        rooms.put("R9-LO", 63.415519);
        rooms.put("R93-LA", 10.40556);
        rooms.put("R93-LO", 63.415454);
        rooms.put("S1-LA", 10.404562);
        rooms.put("S1-LO", 63.417317);
        rooms.put("S3-LA", 10.404517);
        rooms.put("S3-LO", 63.417407);
        rooms.put("S4-LA", 10.404404);
        rooms.put("S4-LO", 63.417476);
        rooms.put("S7-LA", 10.40387);
        rooms.put("S7-LO", 63.417989);
    }

    
    public LocationComparer(){
        this.latitude = 0;
        this.longitude = 0;
    }


    public LocationComparer(double latitude, double longitude){
        this.latitude = latitude;
        this.longitude = longitude;
    }

    // Returns latitude to the position
    private double getLatitude(){
        return this.latitude;
    }

    // Returns longitude to the position
    private double getLongitudeRoom(){
        return this.longitude;
    }

    // Returns latitude to the room as an argument
    private double getLatitudeRoom(String room){
        return rooms.get(room + "-LA");
    }

    // Returns longitude to the room as an argument
    private double getLongitudeRoom(String room){
        return rooms.get(room + "-LO");
    }

    // Returns true if position is inside the square of the lectureroom
    private boolean insideLectureRoom(double latitude, double longitude, String room, double radius){
        if(latitude < (getLatitudeRoom(room) + radius) && getLongitudeRoom(room) < (room + radius){
            if(latitude > (getLatitudeRoom(room) - radius) && getLongitudeRoom(room) > (room - radius)){
                return true;
            }
        }
        return false;
    }

    // Checks if student is attending lecture
    public boolean comparator(String room){
        if(getLatitude() != 0 && getLongitude() != 0){
            return insideLectureRoom(getLatitude(), getLongitude(), room, this.radius);     
        }
        return false;
    }



/*
GPS coordinates to check:

B1: Latitude: 63.417341 | Longitude: 10.406011

EL2: Latitude: 63.418549 | Longitude: 10.401789
EL3: Latitude: 63.418598 | Longitude: 10.401403

F1: Latitude: 63.416552 | Longitude: 10.403184
F6: Latitude: 63.417125 | Longitude: 10.403194

H3: Latitude: 63.419439 | Longitude: 10.402953
H3 Rom 511: SAMME SOM H3

K5: Latitude: 63.41628 | Longitude: 10.406832

MA24: Latitude: 63.4155 | Longitude: 10.409927

R1: Latitude: 63.415937 | Longitude: 10.406488
R2: Latitude: 63.415966 | Longitude: 10.407132
R3: Latitude: 63.415543 | Longitude: 10.406515
R5: Latitude: 63.415661 | Longitude: 10.405292
R7: Latitude: 63.415601 | Longitude: 10.404707
R9: Latitude: 63.415519 | Longitude: 10.404305
R93: Latitude: 63.415454 | Longitude: 10.40556

S1: Latitude: 63.417317 | Longitude: 10.404562
S3: Latitude: 63.417407 | Longitude: 10.404517
S4: Latitude: 63.417476 | Longitude: 10.404404
S7: Latitude: 63.417989 | Longitude: 10.40387
*/