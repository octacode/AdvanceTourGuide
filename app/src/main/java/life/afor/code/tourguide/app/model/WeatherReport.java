package life.afor.code.tourguide.app.model;

/**
 * Created by shasha on 8/1/18.
 */

public class WeatherReport {
    private String description, maxTemp, minTemp, speed;

    public WeatherReport(String description, String maxTemp, String minTemp, String speed) {
        this.description = description;
        this.maxTemp = maxTemp;
        this.minTemp = minTemp;
        this.speed = speed;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMaxTemp() {
        return maxTemp;
    }

    public void setMaxTemp(String maxTemp) {
        this.maxTemp = maxTemp;
    }

    public String getMinTemp() {
        return minTemp;
    }

    public void setMinTemp(String minTemp) {
        this.minTemp = minTemp;
    }

    public String getSpeed() {
        return speed;
    }

    public void setSpeed(String speed) {
        this.speed = speed;
    }
}