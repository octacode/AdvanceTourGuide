/**
 * Filename: FoursquareVenue.java
 * Author: Matthew Huie
 *
 * FoursquareVenue describes a venue object from the Foursquare API.
 */

package life.afor.code.advancetourguide.app.model;

public class FoursquareVenue {

    // The ID of the venue.
    String id;

    // The name of the venue.
    String name;

    // The rating of the venue, if available.
    double rating;

    // A location object within the venue.
    FoursquareLocation location;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public FoursquareLocation getLocation() {
        return location;
    }

    public void setLocation(FoursquareLocation location) {
        this.location = location;
    }
}