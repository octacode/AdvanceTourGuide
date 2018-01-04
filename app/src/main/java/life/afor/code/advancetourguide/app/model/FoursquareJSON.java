/**
 * Filename: FoursquareJSON.java
 * Author: Matthew Huie
 *
 * FoursquareJSON describes a JSON response from the Foursquare API.
 */

package life.afor.code.advancetourguide.app.model;

public class FoursquareJSON {

    // A response object within the JSON.
    FoursquareResponse response;

    public FoursquareResponse getResponse() {
        return response;
    }

    public void setResponse(FoursquareResponse response) {
        this.response = response;
    }
}
