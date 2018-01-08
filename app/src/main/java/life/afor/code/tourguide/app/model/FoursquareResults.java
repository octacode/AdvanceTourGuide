/**
 * Filename: FoursquareResults.java
 * Author: Matthew Huie
 *
 * FoursquareResults describes a results object from the Foursquare API.
 */

package life.afor.code.tourguide.app.model;

import java.io.Serializable;

public class FoursquareResults implements Serializable{

    // A venue object within the results.
    FoursquareVenue venue;

    public FoursquareVenue getVenue() {
        return venue;
    }
}
