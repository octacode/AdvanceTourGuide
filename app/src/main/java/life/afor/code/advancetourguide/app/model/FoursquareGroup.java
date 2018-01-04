/**
 * Filename: FoursquareGroup.java
 * Author: Matthew Huie
 *
 * FoursquareGroup describes a group object from the Foursquare API.
 */

package life.afor.code.advancetourguide.app.model;

import java.util.ArrayList;
import java.util.List;

public class FoursquareGroup {

    // A results list within the group.
    List<FoursquareResults> results = new ArrayList<FoursquareResults>();

    public List<FoursquareResults> getResults() {
        return results;
    }
}
