/**
 * Filename: FoursquareResults.java
 * Author: Matthew Huie
 *
 * FoursquareResults describes a results object from the Foursquare API.
 */

package life.afor.code.tourguide.app.model;

public class FoursquareResults {

    // A venue object within the results.
    FoursquareVenue venue;

    public FoursquareVenue getVenue() {
        return venue;
    }
}
