/**
 * Filename: FoursquareLocation.java
 * Author: Matthew Huie
 *
 * FoursquareLocation describes a location object from the Foursquare API.
 */

package life.afor.code.tourguide.app.model;

public class FoursquareLocation {

    // The address of the location.
    public String address;

    // The latitude of the location.
    public double lat;

    // The longitude of the location.
    public double lng;

    // The distance of the location, calculated from the specified location.
    int distance;

}