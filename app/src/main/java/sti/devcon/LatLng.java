package sti.devcon;

/**
 * Created by Jace Christian on 1/27/2017.
 */

public class LatLng {
    public final double	latitude;
    public final double	longitude;

    LatLng(double latitude, double longitude){
        this.latitude=latitude;
        this.longitude=longitude;
    }

    double getLongitude(){
        return longitude;
    }
    double getLatitude(){
        return latitude;
    }
}
